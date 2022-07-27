package org.yjhking.pethome.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author YJH
 */
@Data
public class OrderDto {
    private Long Id;
    private Integer payType;
    private BigDecimal money;
}
