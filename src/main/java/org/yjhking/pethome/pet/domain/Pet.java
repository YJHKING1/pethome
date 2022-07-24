package org.yjhking.pethome.pet.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.user.domain.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Pet extends BaseDomain {
    private String name;
    
    private Long costprice;
    
    private BigDecimal saleprice;
    
    private Long typeId;
    /**
     * 宠物类型
     */
    private PetType petType;
    
    private String resources;
    
    private Integer state;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date offsaletime;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date onsaletime;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime = new Date();
    
    private Long shopId;
    /**
     * 店铺
     */
    private Shop shop;
    
    private Long userId;
    /**
     * 用户
     */
    private User user;
    
    private Long searchMasterMsgId;
    /**
     * 寻主消息
     */
    private SearchMasterMsg searchMasterMsg;
    /**
     * 宠物明细
     */
    private PetDetail petDetail;
}