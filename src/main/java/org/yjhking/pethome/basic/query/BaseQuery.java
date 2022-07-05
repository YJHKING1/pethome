package org.yjhking.pethome.basic.query;

import lombok.Data;

/**
 * 通用查询工具类
 *
 * @author YJH
 */
@Data
public class BaseQuery {
    /**
     * 当前页
     */
    private Integer currentPage = 1;
    /**
     * 每页条数
     */
    private Integer pageSize = 5;
    
    /**
     * 关键字
     */
    private String keyword;
    
    /**
     * 获取当前页的起始下标
     *
     * @return 当前页的起始下标
     */
    public Integer getBegin() {
        return (this.currentPage - 1) * this.pageSize;
    }
}
