package org.yjhking.pethome.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.basic.util.BaiduAiUtils;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.org.domain.ShopAuditLog;
import org.yjhking.pethome.org.dto.ShopDto;
import org.yjhking.pethome.org.mapper.EmployeeMapper;
import org.yjhking.pethome.org.mapper.ShopAuditLogMapper;
import org.yjhking.pethome.org.mapper.ShopMapper;
import org.yjhking.pethome.org.service.ShopService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.beans.Transient;
import java.util.List;

/**
 * @author YJH
 */
@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements ShopService {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private ShopAuditLogMapper shopAuditLogMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Transient
    @Override
    public void settlement(Shop shop) {
        // 每个参数的空值判断
        if (shop.getName() == null || shop.getName().trim().length() == 0
                || shop.getTel() == null || shop.getTel().trim().length() == 0
                || shop.getAddress() == null || shop.getAddress().trim().length() == 0
                || shop.getEmployee().getUsername() == null || shop.getEmployee().getUsername().trim().length() == 0
                || shop.getEmployee().getPhone() == null || shop.getEmployee().getPhone().trim().length() == 0
                || shop.getEmployee().getEmail() == null || shop.getEmployee().getEmail().trim().length() == 0
                || shop.getEmployee().getPassword() == null || shop.getEmployee().getPassword().trim().length() == 0
                || shop.getEmployee().getComfirmPassword() == null
                || shop.getEmployee().getComfirmPassword().trim().length() == 0) {
            throw new BusinessRuntimeException("信息不能为空");
        }
        // 两次密码不一致校验
        if (!shop.getEmployee().getPassword().equals(shop.getEmployee().getComfirmPassword())) {
            throw new BusinessRuntimeException("两次密码不一致");
        }
        // 该店铺是否已经被入驻过
        if (shopMapper.selectByNameAndAddress(shop.getName(), shop.getAddress()) != null) {
            throw new BusinessRuntimeException("该店铺已经被入驻过");
        }
        // 审核店铺名称是否合法
        if (!BaiduAiUtils.textCensor(shop.getName())) {
            throw new BusinessRuntimeException("店铺名称不合法");
        }
        // 审核店铺logo是否合法
        String imgUrl = "http://123.207.27.208" + shop.getLogo();
        if (!BaiduAiUtils.imgCensor(imgUrl)) {
            throw new BusinessRuntimeException("店铺logo不合法");
        }
        // 保存店铺管理员信息t_employee
        Employee employee = shop.getEmployee();
        employeeMapper.insertSelective(employee);
        // 将店铺管理员的id设置进店铺信息中
        shop.setAdminId(employee.getId());
        
        // 保存店铺信息t_shop
        shopMapper.insertSelective(shop);
        // 将店铺的id设置进员工信息中
        employee.setShopId(shop.getId());
        
        // 将店铺的id更新到t_employee中
        employeeMapper.updateByPrimaryKeySelective(employee);
    }
    
    @Transient
    @Override
    public void pass(ShopAuditLog log) throws MessagingException {
        // 修改状态
        Shop shop = shopMapper.selectByPrimaryKey(log.getShopId());
        shop.setState(2);
        shopMapper.updateByPrimaryKeySelective(shop);
        
        // 保存审核记录
        log.setState(2);
        log.setAuditId(666L);
        shopAuditLogMapper.insertSelective(log);
        
        // 发送激活邮件
        //创建复杂邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //发送复杂邮件的工具类
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setFrom("910480155@qq.com");
        helper.setSubject("店铺激活邮件");
        helper.setText("<h1>你的店铺已经注册!!!</h1><h2>点击链接激活</h2><a src='http://localhost:8080/shop/active/"
                + shop.getId() + "'>http://localhost:8080/shop/active/" + shop.getId() + "</a>", true);
        //收件人
        Long adminId = shop.getAdminId();
        String email = employeeMapper.selectByPrimaryKey(adminId).getEmail();
        helper.setTo(email);
        javaMailSender.send(mimeMessage);
    }
    
    @Transient
    @Override
    public void reject(ShopAuditLog log) throws MessagingException {
        // 修改状态
        Shop shop = shopMapper.selectByPrimaryKey(log.getShopId());
        shop.setState(4);
        shopMapper.updateByPrimaryKeySelective(shop);
        
        // 保存审核记录
        log.setState(4);
        log.setAuditId(666L);
        shopAuditLogMapper.insertSelective(log);
        
        // 发送激活邮件
        //创建复杂邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //发送复杂邮件的工具类
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setFrom("910480155@qq.com");
        helper.setSubject("入驻失败邮件");
        helper.setText("<h1>入驻失败!!!</h1><h2>点击链接重新入驻或登录</h2>" +
                "<a src='http://localhost:8081/#/login/'>http://localhost:8081/#/login/</a>", true);
        //收件人
        Long adminId = shop.getAdminId();
        Employee employee = employeeMapper.selectByPrimaryKey(adminId);
        String email = employee.getEmail();
        helper.setTo(email);
        javaMailSender.send(mimeMessage);
        // 删除员工信息
        employeeMapper.deleteByPrimaryKey(employee.getId());
        // 删除店铺信息
        shopMapper.deleteByPrimaryKey(shop.getId());
    }
    
    @Override
    public void active(Long id) {
        // 查询店铺
        Shop shop = shopMapper.selectByPrimaryKey(id);
        // 修改状态
        shop.setState(3);
        // 保存店铺信息
        shopMapper.updateByPrimaryKeySelective(shop);
    }
    
    @Override
    public List<ShopDto> getCountByState() {
        return shopMapper.getCountByState();
    }
    
    @Transient
    @Override
    public void patchInsert(List<Shop> shops) {
        shopMapper.patchInsert(shops);
    }
}
