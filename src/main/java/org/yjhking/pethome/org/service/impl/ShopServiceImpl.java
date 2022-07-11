package org.yjhking.pethome.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.org.mapper.EmployeeMapper;
import org.yjhking.pethome.org.mapper.ShopMapper;
import org.yjhking.pethome.org.service.ShopService;

import java.beans.Transient;

/**
 * @author YJH
 */
@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements ShopService {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    
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
}
