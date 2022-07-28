package org.yjhking.pethome.product.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ProductDetail extends BaseDomain {
    private Long productId;

    private String ordernotice;

    private String intro;
}