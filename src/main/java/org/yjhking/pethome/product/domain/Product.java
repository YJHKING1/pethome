package org.yjhking.pethome.product.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseDomain {
    private String name;
    
    private Long costprice;
    
    private BigDecimal saleprice;
    
    private String resources;
    
    private Long salecount;
    
    private Long state;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date offsaletime;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date onsaletime;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime = new Date();
    private ProductDetail productDetail;
}