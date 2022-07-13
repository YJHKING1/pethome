package org.yjhking.pethome.org.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.yjhking.pethome.basic.domain.BaseDomain;

import java.util.Date;

/**
 * @author YJH
 */
@Data
public class Shop extends BaseDomain {
    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称", orderNum = "1", width = 30)
    private String name;
    /**
     * 店铺座机
     */
    @Excel(name = "店铺座机", orderNum = "2", width = 30)
    private String tel;
    /**
     * 店铺入驻时间默认值，新增店铺时设置
     */
    @Excel(name = "店铺入驻时间", orderNum = "3", width = 30, exportFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registerTime = new Date();
    /**
     * 店铺状态：1待审核，2审核通过待激活，3激活成功，4驳回
     */
    @Excel(name = "店铺状态", orderNum = "4", width = 30)
    private Integer state = 1;
    /**
     * 店铺地址
     */
    @Excel(name = "店铺地址", orderNum = "5", width = 30)
    private String address;
    /**
     * 店铺logo
     */
    @Excel(name = "店铺logo", orderNum = "6", width = 30)
    private String logo;
    /**
     * 店长id
     */
    private Long adminId;
    /**
     * 员工
     */
    private Employee employee;
}