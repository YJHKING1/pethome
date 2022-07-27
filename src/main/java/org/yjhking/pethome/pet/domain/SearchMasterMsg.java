package org.yjhking.pethome.pet.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.user.domain.User;

import java.math.BigDecimal;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchMasterMsg extends BaseDomain {
    private String title;
    private Integer state;
    private String name;
    private Integer age;
    private Boolean gender;
    private String coatColor;
    private String resources;
    private Long petType;
    private BigDecimal price;
    private String address;
    private Long userId;
    private Long shopId;
    private String note;
    private PetType type;
    private Shop shop;
    private User user;
}