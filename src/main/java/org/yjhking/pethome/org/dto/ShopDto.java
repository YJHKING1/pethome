package org.yjhking.pethome.org.dto;

import lombok.Data;

/**
 * @author YJH
 */
@Data
public class ShopDto {
    /**
     * 店铺状态
     */
    private Integer state;
    /**
     * 每种状态的数量
     */
    private Integer countNum;
}
