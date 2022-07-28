package org.yjhking.pethome.pet.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.constants.FastDfsImgConstants;
import org.yjhking.pethome.basic.dto.Point;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.basic.util.*;
import org.yjhking.pethome.order.domain.OrderPetAcquisition;
import org.yjhking.pethome.order.mapper.OrderPetAcquisitionMapper;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.org.mapper.EmployeeMapper;
import org.yjhking.pethome.org.mapper.ShopMapper;
import org.yjhking.pethome.pet.domain.Pet;
import org.yjhking.pethome.pet.domain.SearchMasterMsg;
import org.yjhking.pethome.pet.domain.SearchMasterMsgAuditLog;
import org.yjhking.pethome.pet.mapper.PetMapper;
import org.yjhking.pethome.pet.mapper.SearchMasterMsgAuditLogMapper;
import org.yjhking.pethome.pet.mapper.SearchMasterMsgMapper;
import org.yjhking.pethome.pet.query.SearchMasterMsgQuery;
import org.yjhking.pethome.pet.service.SearchMasterMsgService;
import org.yjhking.pethome.user.domain.Logininfo;
import org.yjhking.pethome.user.domain.User;
import org.yjhking.pethome.user.mapper.UserMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author YJH
 */
@Service
public class SearchMasterMsgServiceImpl extends BaseServiceImpl<SearchMasterMsg> implements SearchMasterMsgService {
    @Resource
    private SearchMasterMsgAuditLogMapper searchMasterMsgAuditLogMapper;
    @Resource
    private SearchMasterMsgMapper searchMasterMsgMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ShopMapper shopMapper;
    @Resource
    private EmployeeMapper employeeMapper;
    @Resource
    private OrderPetAcquisitionMapper orderPetAcquisitionMapper;
    @Resource
    private PetMapper petMapper;
    
    @Override
    @Transactional
    public AjaxResult publish(SearchMasterMsg searchMasterMsg, HttpServletRequest request) {
        // 获取发布寻主消息的用户
        Logininfo logininfo = (Logininfo) LoginContext.getLoginUser(request);
        assert logininfo != null;
        User user = userMapper.selectByLogininfoId(logininfo.getId());
        
        // 1.添加寻主消息
        searchMasterMsg.setUserId(user.getId());
        searchMasterMsgMapper.insertSelective(searchMasterMsg);
        
        // 2.智能审核
        Boolean textBoo = BaiduAiUtils.textCensor(searchMasterMsg.getTitle() + searchMasterMsg.getName() + searchMasterMsg.getAddress());
        Boolean imageBoo = null;
        if (searchMasterMsg.getResources() != null) {
            imageBoo = BaiduAiUtils.imgCensor(FastDfsImgConstants.IMG_SERVER_PREFIX_URL + searchMasterMsg.getResources());
        } else {
            imageBoo = true;
        }
        
        if (textBoo && imageBoo) {
            // 2.1.审核通过： 修改寻主消息状态1【审核通过待处理】 + 将当前寻主消息分配给最近的门店 + 发短信告诉它 + 添加审核日志
            // 设置状态和日志
            searchMasterMsg.setState(1);
            searchMasterMsg.setNote("审核成功");
            // 绑定店铺
            Point point = DistanceUtils.getPoint(searchMasterMsg.getAddress());
            // 50公里以内
            Shop nearestShop = DistanceUtils.getNearestShop(point, shopMapper.selectAll());
            if (nearestShop != null) {
                searchMasterMsg.setShopId(nearestShop.getId());
                // 模拟发短信或发邮件
                System.out.println("你有新的订单，请尽快处理：" + searchMasterMsg.getTitle());
            }
            searchMasterMsgMapper.updateByPrimaryKeySelective(searchMasterMsg);
            
            // 添加审核日志
            SearchMasterMsgAuditLog log = new SearchMasterMsgAuditLog();
            log.setMsgId(searchMasterMsg.getId());
            // audit_id - 自动审核，没有添加审核人
            log.setState((byte) 1);// 驳回
            log.setNote("审核成功，happy");
            searchMasterMsgAuditLogMapper.insertSelective(log);
            return new AjaxResult(true, "发布成功");
        } else {
            // 修改状态和日志
            searchMasterMsg.setState(0);
            searchMasterMsg.setNote("审核失败，内容非法");
            searchMasterMsgMapper.updateByPrimaryKeySelective(searchMasterMsg);
            // 2.2.审核失败：修改寻主消息状态0 + 添加审核日志
            SearchMasterMsgAuditLog log = new SearchMasterMsgAuditLog();
            log.setMsgId(searchMasterMsg.getId());
            // audit_id - 自动审核，没有添加审核人
            log.setState((byte) 0);// 驳回
            log.setNote("审核失败，内容非法");
            searchMasterMsgAuditLogMapper.insertSelective(log);
            return new AjaxResult(false, "审核失败，内容非法");
            /*
             其他思路：也可以将审核失败的寻主消息对象传递到前端【resultObj】，
             前端显示这个寻主消息数据做修改操作，直到这个寻主消息审核通过，这样就不会存在审核不通过的寻主消息每次点击都会加到数据库一次
            */
        }
    }
    
    @Override
    public PageList<SearchMasterMsg> userSearchMasterMsg(SearchMasterMsgQuery query, HttpServletRequest request) {
        Logininfo logininfo = (Logininfo) LoginContext.getLoginUser(request);
        assert logininfo != null;
        User user = userMapper.selectByLogininfoId(logininfo.getId());
        query.setUserId(user.getId());
        return super.queryData(query);
    }
    
    @Override
    public PageList<SearchMasterMsg> toHandle(SearchMasterMsgQuery query, HttpServletRequest request) {
        query.setState(1);
        Logininfo logininfo = (Logininfo) LoginContext.getLoginUser(request);
        assert logininfo != null;
        Employee employee = employeeMapper.selectByLogininfoId(logininfo.getId());
        if (employee.getShopId() != null) {
            query.setShopId(employee.getShopId());
        }
        return super.queryData(query);
    }
    
    @Override
    public void reject(Long id) {
        searchMasterMsgMapper.reject(id);
    }
    
    @Override
    @Transactional
    public void accept(Map<String, Object> params) {
        Long msgId = Long.valueOf(params.get("msgId").toString());
        //1.校验改寻主消息是否已经在处理，报错
        OrderPetAcquisition orderTmp = orderPetAcquisitionMapper.selectByMsgId(msgId);
        if (orderTmp != null) {
            throw new BusinessRuntimeException("已经被接单，正在处理中！");
        }
        //处理人id
        Long handlerId = Long.valueOf(params.get("handler").toString());
        String note = params.get("note").toString();
        Employee handler = employeeMapper.selectByPrimaryKey(handlerId);
        //1 创建寻主消息所对应宠物信息
        //1.1 获取寻主消息
        SearchMasterMsg searchMasterMsg = searchMasterMsgMapper.selectByPrimaryKey(msgId);
        //1.2 创建宠物信息
        Pet pet = searchMasterMsg2Pet(searchMasterMsg);
        petMapper.insertSelective(pet);
        //2 创建宠物收购订单
        OrderPetAcquisition order = buildPetAcquisitionOrder(searchMasterMsg, handler, pet);
        orderPetAcquisitionMapper.insertSelective(order);
        //3 发送消息给对应的工作人员
        System.out.println(handler.getEmail() + "-->邮件通知：您有新的" + order.getOrdersn() + "订单要处理！");
        //System.out.println(handler.getPhone()+"-->短信通知：您有新的"+order.getOrderSn()+"订单要处理！");
    }
    
    @Override
    public PageList<SearchMasterMsg> queryPagePool(SearchMasterMsgQuery query) {
        return super.queryData(query);
    }
    
    private OrderPetAcquisition buildPetAcquisitionOrder(SearchMasterMsg searchMasterMsg, Employee handler, Pet pet) {
        OrderPetAcquisition order = new OrderPetAcquisition();
        String orderSn = CodeGenerateUtils.generateOrderSn(handler.getId());
        order.setOrdersn(orderSn);
        order.setDigest(searchMasterMsg.getName() + "的收购订单");
        //最后收购是当前时间+2两天
        long lastcomfirmtimeLong = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2;
        order.setLastcomfirmtime(new Date(lastcomfirmtimeLong));
        order.setState(0);
        order.setPrice(searchMasterMsg.getPrice().longValue());
        //        order.setPaytype(); //现金支付
        order.setPetId(pet.getId());
        order.setUserId(searchMasterMsg.getUserId());
        order.setShopId(pet.getShopId());
        order.setAddress(searchMasterMsg.getAddress());
        order.setEmpId(handler.getId());
        order.setSearchMasterMsgId(searchMasterMsg.getId());
        return order;
    }
    
    private Pet searchMasterMsg2Pet(SearchMasterMsg searchMasterMsg) {
        Pet pet = new Pet();
        pet.setName(searchMasterMsg.getName());
        pet.setResources(searchMasterMsg.getResources());
        pet.setTypeId(searchMasterMsg.getPetType());
        //就要获取处理人的店铺id
        pet.setShopId(searchMasterMsg.getShopId());
        pet.setUserId(searchMasterMsg.getUserId());
        pet.setSearchMasterMsgId(searchMasterMsg.getId());
        //用户发布时需要的价格 收购回来就是成本价
        pet.setCostprice(searchMasterMsg.getPrice().longValue());
        return pet;
    }
}
