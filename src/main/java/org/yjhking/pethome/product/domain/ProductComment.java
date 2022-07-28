package org.yjhking.pethome.product.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
/**
    * 产品评价
    */
@Data
@EqualsAndHashCode(callSuper=true)
public class ProductComment extends BaseDomain {
    private Long createtime;

    private Long updatetime;

    private Long orderid;

    private Long productid;

    private Integer score;

    private String comment;

    private Byte level;
}