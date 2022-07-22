package org.yjhking.pethome.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.constants.VerifyCodeConstants;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.basic.util.HttpUtil;
import org.yjhking.pethome.basic.util.Md5Utils;
import org.yjhking.pethome.basic.util.StrUtils;
import org.yjhking.pethome.system.domain.Menu;
import org.yjhking.pethome.system.mapper.PermissionMapper;
import org.yjhking.pethome.user.constants.WeiXinConstants;
import org.yjhking.pethome.user.domain.Logininfo;
import org.yjhking.pethome.user.domain.User;
import org.yjhking.pethome.user.domain.Wxuser;
import org.yjhking.pethome.user.dto.LoginDto;
import org.yjhking.pethome.user.jwt.JwtUtils;
import org.yjhking.pethome.user.jwt.LoginData;
import org.yjhking.pethome.user.jwt.RsaUtils;
import org.yjhking.pethome.user.mapper.LogininfoMapper;
import org.yjhking.pethome.user.mapper.UserMapper;
import org.yjhking.pethome.user.mapper.WxuserMapper;
import org.yjhking.pethome.user.service.LogininfoService;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录业务层
 *
 * @author YJH
 */
@Service
public class LogininfoServiceImpl extends BaseServiceImpl<Logininfo> implements LogininfoService {
    @Autowired
    private LogininfoMapper logininfoMapper;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private WxuserMapper wxuserMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    
    @Override
    public AjaxResult account(LoginDto loginDto) {
        // 提取所有参数
        String account = loginDto.getAccount();
        String checkPass = loginDto.getCheckPass();
        Integer loginType = loginDto.getLoginType();
        // 空值校验
        if (account == null || account.trim().length() == 0
                || checkPass == null || checkPass.trim().length() == 0
                || loginType == null) {
            throw new BusinessRuntimeException("参数不能为空");
        }
        // 校验账号是否存在
        Logininfo logininfo = logininfoMapper.selectByAccount(loginDto);
        if (logininfo == null) {
            throw new BusinessRuntimeException("账号不存在");
        }
        // 获取数据库中的密码
        String dbPassword = logininfo.getPassword();
        // 获取前端密码并加盐加密
        String password = Md5Utils.encrypByMd5(checkPass + logininfo.getSalt());
        // 校验密码是否正确
        if (!dbPassword.equals(password)) {
            throw new BusinessRuntimeException("密码错误");
        }
        // 校验账号是否被禁用
        if (!logininfo.getDisable()) {
            throw new BusinessRuntimeException("账号已被禁用");
        }
        /*// 生成tokens
        String token = UUID.randomUUID().toString();
        // 将盐值和密码设为空
        logininfo.setSalt(null);
        logininfo.setPassword(null);
        // 保存到redis
        // redis保存对象需先序列化，再保存，否则会报错。解决办法：在要保存的实体类上实现Serializable接口即可。
        redisTemplate.opsForValue().set(token, logininfo, 30, TimeUnit.MINUTES);
        // 创建map用于返回
        Map<String, Object> map = new HashMap<>();
        // 将token可登录信息对象装入map
        map.put("token", token);
        map.put("logininfo", logininfo);*/
        Map<String, Object> map = loginSuccessJwtHandler(logininfo);
        // 返回map
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setResultObj(map);
        return ajaxResult;
    }
    
    private Map<String, Object> loginSuccessJwtHandler(Logininfo logininfo) {
        //创建LoginData对象用于等会通过私钥加密
        LoginData loginData = new LoginData();
        loginData.setLogininfo(logininfo);
        Map<String, Object> map = new HashMap<>();
        try {
            //1.获取logininfo
            if (logininfo.getType() == 0) {//管理员
                //2.获取当前登陆人的所有权限 - sn
                List<String> permissions = logininfoMapper.selectPermissionsByLogininfoId(logininfo.getId());
                //3.获取当前登陆人的所有菜单 - 【难度】
                List<Menu> menus = logininfoMapper.selectMenusByLogininfoId(logininfo.getId());
                loginData.setPermissions(permissions);
                loginData.setMenus(menus);
                //将当前登陆人的权限和菜单添加到map - 响应给前端
                map.put("permissions", permissions);
                map.put("menus", menus);
            }
            //4.通过私钥对登录信息进行加密 - jwtToken串
            PrivateKey privateKey = RsaUtils.getPrivateKey(RsaUtils.class.getClassLoader()
                    .getResource("auth_rsa.pri").getFile());
            //将登陆人信息加密得到jwtToken串
            String jwtToken = JwtUtils.generateTokenExpireInMinutes(loginData, privateKey, 30);
            //5.装到map返回
            map.put("token", jwtToken);
            map.put("logininfo", logininfo);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public AjaxResult wechatLogin(String code) {
        // 准备发送第二个请求的参数
        String url = WeiXinConstants.GET_ACK_URL.replace("APPID", WeiXinConstants.APPID)
                .replace("SECRET", WeiXinConstants.SECRET).replace("CODE", code);
        // 发送请求，并返回json数据
        String jsonStr = HttpUtil.httpGet(url);
        // 转为json对象
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        // 获取token和openid
        String access_token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");
        // 通过openid查询数据库
        Wxuser wxuser = wxuserMapper.selectByOpenid(openid);
        // 如果查询到，则登录成功，否则，响应给前端
        if (wxuser != null && wxuser.getUserId() != null && userMapper.selectByPrimaryKey(wxuser.getUserId()) != null) {
            /*// 生成token
            String token = UUID.randomUUID().toString();
            // 通过微信用户id查询用户对象
            User user = userMapper.selectByPrimaryKey(wxuser.getUserId());
            // 通过用户对象中的登录信息id查询登录信息对象
            Logininfo logininfo = logininfoMapper.selectByPrimaryKey(user.getLogininfoId());
            // 保存到redis
            redisTemplate.opsForValue().set(token, logininfo, 30, TimeUnit.MINUTES);
            // 创建map用于返回
            Map<String, Object> map = new HashMap<>();
            // 将token对象装入map
            map.put("token", token);
            // 将logininfo装入map
            map.put("logininfo", logininfo);*/
            User user = userMapper.selectByPrimaryKey(wxuser.getUserId());
            Logininfo logininfo = logininfoMapper.selectByPrimaryKey(user.getLogininfoId());
            Map<String, Object> map = loginSuccessJwtHandler(logininfo);
            // 返回map
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.setResultObj(map);
            return ajaxResult;
        } else {
            // 创建参数地址
            String param = "?accessToken=" + access_token + "&openId=" + openid;
            return new AjaxResult(false, "登录失败", param);
        }
    }
    
    @Override
    public AjaxResult wechatBinder(Map<String, String> map) {
        // 获取参数
        String phone = map.get("phone");
        String verifyCode = map.get("verifyCode");
        String accessToken = map.get("accessToken");
        String openId = map.get("openId");
        // 空值校验
        if (phone == null || phone.trim().length() == 0
                || verifyCode == null || verifyCode.trim().length() == 0
                || accessToken == null || accessToken.trim().length() == 0
                || openId == null || openId.trim().length() == 0) {
            throw new BusinessRuntimeException("参数不能为空");
        }
        // 从数据库里获得验证码
        Object redisCode = redisTemplate.opsForValue().get(VerifyCodeConstants.BINDER_CODE_PREFIX + phone);
        // 判断验证码是否过期
        if (redisCode == null) {
            throw new BusinessRuntimeException("验证码已过期");
        }
        // 判断验证码是否正确
        if (!redisCode.toString().split(":")[0].equalsIgnoreCase(verifyCode.trim())) {
            throw new BusinessRuntimeException("验证码错误");
        }
        // 获得请求url
        String url = WeiXinConstants.GET_USER_URL
                .replace("ACCESS_TOKEN", accessToken)
                .replace("OPENID", openId);
        // 将url转换成json字符串
        String jsonStr = HttpUtil.httpGet(url);
        // 将json字符串转换成Wxuser对象
        Wxuser wxuser = jsonStr2Wxuser(jsonStr);
        // 通过电话查询User对象
        User user = userMapper.selectByPhone(phone);
        // 判断是否已经绑定过微信，没有就创建
        if (user == null) {
            // 将电话转换为User对象
            user = phone2User(phone);
            // 将User对象转换为登录信息对象
            Logininfo logininfo = user2Logininfo(user);
            // 将登录信息对象添加进数据库
            logininfoMapper.insertSelective(logininfo);
            // 将登录信息id保存到User对象中
            user.setLogininfoId(logininfo.getId());
            // 将User对象添加进数据库
            userMapper.insertSelective(user);
        }
        // 设置用户id进微信用户里
        wxuser.setUserId(user.getId());
        // 将微信用户保存进数据库
        wxuserMapper.insertSelective(wxuser);
        // 免密登录
        /*// 获得token
        String token = UUID.randomUUID().toString();
        // 通过用户的登录信息查询登录信息对象
        Logininfo logininfo = logininfoMapper.selectByPrimaryKey(user.getLogininfoId());
        // 设置盐值为空
        logininfo.setSalt(null);
        // 设置密码为空
        logininfo.setPassword(null);
        // 创建返回map集合
        Map<Object, Object> reMap = new HashMap<>();
        // 将登录信息和token保存到redis数据库中
        redisTemplate.opsForValue().set(token, logininfo, 30, TimeUnit.MINUTES);
        // 将token和登录信息保存到map集合中
        reMap.put("token", token);
        reMap.put("logininfo", logininfo);*/
        Logininfo logininfo = logininfoMapper.selectByPrimaryKey(user.getLogininfoId());
        Map<String, Object> reMap = loginSuccessJwtHandler(logininfo);
        // 返回map集合
        return new AjaxResult(true, "绑定成功", reMap);
    }
    
    /**
     * 将json字符串转为微信用户对象
     *
     * @param jsonStr json字符串
     * @return 微信用户对象
     */
    private Wxuser jsonStr2Wxuser(String jsonStr) {
        Wxuser wxuser = new Wxuser();
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        wxuser.setOpenid(jsonObject.getString("openid"));
        wxuser.setNickname(jsonObject.getString("nickname"));
        wxuser.setSex(Integer.valueOf(jsonObject.getString("sex")));
        wxuser.setHeadimgurl(jsonObject.getString("headimgurl"));
        wxuser.setUnionid(jsonObject.getString("unionid"));
        String country = jsonObject.getString("country");
        String province = jsonObject.getString("province");
        String city = jsonObject.getString("city");
        wxuser.setAddress(country + province + city);
        return wxuser;
    }
    
    /**
     * 将手机号转为用户对象
     *
     * @param phone 手机号
     * @return 用户对象
     */
    private User phone2User(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setUsername(phone);
        String salt = StrUtils.getComplexRandomString(32);
        String randomPassword = StrUtils.getComplexRandomString(8);
        String password = Md5Utils.encrypByMd5(randomPassword + salt);
        user.setSalt(salt);
        user.setPassword(password);
        user.setState(1);
        return user;
    }
    
    /**
     * 将用户对象转为登录信息对象
     *
     * @param user 用户对象
     * @return 登录信息对象
     */
    private Logininfo user2Logininfo(User user) {
        Logininfo logininfo = new Logininfo();
        BeanUtils.copyProperties(user, logininfo);
        logininfo.setType(1);
        return logininfo;
    }
}
