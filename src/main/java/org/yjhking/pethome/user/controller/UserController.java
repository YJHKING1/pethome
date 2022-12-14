package org.yjhking.pethome.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.user.domain.User;
import org.yjhking.pethome.user.dto.UserDto;
import org.yjhking.pethome.user.query.UserQuery;
import org.yjhking.pethome.user.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (t_user)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<User> findAll() {
        return userService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.selectByPrimaryKey(id);
    }
    
    /**
     * 删除
     *
     * @param id 删除id
     * @return 返回信息
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            userService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("删除失败");
        }
        return ajaxResult;
    }
    
    /**
     * 增加及修改
     *
     * @param user 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody User user) {
        AjaxResult ajaxResult = new AjaxResult();
        if (user.getId() == null) {
            try {
                userService.insertSelective(user);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                userService.updateByPrimaryKeySelective(user);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("修改失败");
            }
        }
        
        return ajaxResult;
    }
    
    /**
     * 高级分页查询
     *
     * @param userQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<User> queryPage(@RequestBody UserQuery userQuery) {
        return userService.queryData(userQuery);
    }
    
    /**
     * 批量删除
     *
     * @param ids 要删除的id集合
     * @return 返回信息
     */
    @PatchMapping
    public AjaxResult patchDelete(@RequestBody List<Long> ids) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            userService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
    
    /**
     * 邮箱注册
     *
     * @param userDto 注册参数
     * @return 返回信息
     */
    @PostMapping("/register/email")
    public AjaxResult emailRegister(@RequestBody UserDto userDto) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            userService.emailRegister(userDto);
        } catch (BusinessRuntimeException e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("系统错误");
        }
        return ajaxResult;
    }
    
    /**
     * 电话注册
     *
     * @param userDto 注册参数
     * @return 返回信息
     */
    @PostMapping("/register/phone")
    public AjaxResult phoneRegister(@RequestBody UserDto userDto) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            userService.phoneRegister(userDto);
        } catch (BusinessRuntimeException e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("系统错误");
        }
        return ajaxResult;
    }
    
    /**
     * 用户邮件激活
     *
     * @param id 要激活的用户id
     * @return 返回信息
     */
    @GetMapping("/active/{id}")
    public AjaxResult active(@PathVariable Long id, HttpServletResponse response) {
        try {
            userService.active(id);
            response.sendRedirect("http://localhost/email_true.html");
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "激活失败");
        }
    }
}
