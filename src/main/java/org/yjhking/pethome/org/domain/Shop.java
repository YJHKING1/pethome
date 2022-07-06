package org.yjhking.pethome.org.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.yjhking.pethome.basic.domain.BaseDomain;

import java.util.Date;

/**
 * @author YJH
 */
@Data
public class Shop extends BaseDomain {
    private String name;
    
    private String tel;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registerTime;
    
    private Integer state;
    
    private String address;
    
    private String logo;
    
    private Long adminId;
    /**
     * 员工
     */
    private Employee employee;
}