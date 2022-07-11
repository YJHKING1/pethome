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
    /**
     * 店铺入驻时间默认值，新增店铺时设置
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registerTime = new Date();
    /**
     * 店铺状态：1待审核，2审核通过待激活，3激活成功，4驳回
     */
    private Integer state = 1;
    
    private String address;
    
    private String logo;
    
    private Long adminId;
    /**
     * 员工
     */
    private Employee employee;
}