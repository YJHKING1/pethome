package org.yjhking.pethome.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjhking.pethome.basic.query.BaseQuery;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.basic.util.LoginContext;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.order.domain.OrderPetAcquisition;
import org.yjhking.pethome.order.dto.OrderDto;
import org.yjhking.pethome.order.mapper.OrderPetAcquisitionMapper;
import org.yjhking.pethome.order.query.OrderPetAcquisitionQuery;
import org.yjhking.pethome.order.service.OrderPetAcquisitionService;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.org.mapper.EmployeeMapper;
import org.yjhking.pethome.org.mapper.ShopMapper;
import org.yjhking.pethome.pet.domain.SearchMasterMsg;
import org.yjhking.pethome.pet.mapper.PetMapper;
import org.yjhking.pethome.pet.mapper.SearchMasterMsgMapper;
import org.yjhking.pethome.user.domain.Logininfo;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author YJH
 */
@Service
public class OrderPetAcquisitionServiceImpl extends BaseServiceImpl<OrderPetAcquisition> implements OrderPetAcquisitionService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private OrderPetAcquisitionMapper orderPetAcquisitionMapper;
    @Autowired
    private SearchMasterMsgMapper searchMasterMsgMapper;
    @Autowired
    private PetMapper petMapper;
    
    @Override
    public PageList<OrderPetAcquisition> queryPage(OrderPetAcquisitionQuery query, HttpServletRequest request) {
        Logininfo logininfo = (Logininfo) LoginContext.getLoginUser(request);
        assert logininfo != null;
        Employee employee = employeeMapper.selectByLogininfoId(logininfo.getId());
        // 判断登录员工是那种角色
        //1.employee中shop_id为null，平台管理员
        Long shopId = employee.getShopId();
        //2.店铺人员
        if (shopId != null) {
            Shop shop = shopMapper.selectByPrimaryKey(shopId);
            if (!shop.getAdminId().equals(employee.getId())) {
                //3.否则就为店员
                query.setEmployeeId(employee.getId());
            }
            //4.employee中shop_id不为null，通过shop_id查询店铺得到admin_id如果为当前登录管理员的id，就为店铺管理员
            query.setShopId(shopId);
        }
        //5.平台管理员
        return queryData(query);
    }
    
    @Override
    public void confirm(OrderDto orderDto) {
        Long orderId = orderDto.getId();
        Integer payType = orderDto.getPayType();
        BigDecimal money = orderDto.getMoney();
        //1.修改订单：state = 1，money，payType
        OrderPetAcquisition order = orderPetAcquisitionMapper.selectByPrimaryKey(orderId);
        order.setState(1);
        order.setPrice(money.longValue());
        order.setPaytype(payType);
        orderPetAcquisitionMapper.updateByPrimaryKeySelective(order);
        //2.修改寻主消息：state=2，note
        SearchMasterMsg msg = searchMasterMsgMapper.selectByPrimaryKey(order.getSearchMasterMsgId());
        msg.setState(2);
        msg.setNote("处理完成，交易成功");
        searchMasterMsgMapper.updateByPrimaryKeySelective(msg);
        //宠物
        //修改一个成本价 = money
        // 支付模块
    }
    
    @Override
    public PageList<OrderPetAcquisition> queryData(BaseQuery baseQuery) {
        return super.queryData(baseQuery);
    }
    
    @Override
    public Integer deleteByPrimaryKey(Long id) {
        OrderPetAcquisition order = orderPetAcquisitionMapper.selectByPrimaryKey(id);
        //将寻主消息中的shop_id置空-  寻主池
        searchMasterMsgMapper.reject(order.getSearchMasterMsgId());
        //删除宠物 - 没有成功收购
        petMapper.deleteByPrimaryKey(order.getPetId());
        return super.deleteByPrimaryKey(id);
    }
}
