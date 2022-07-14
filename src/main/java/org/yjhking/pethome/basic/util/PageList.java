package org.yjhking.pethome.basic.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询结果工具类
 * 封装分页查询和高级查询
 *
 * @author YJH
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageList<T> {
    /**
     * 总条数
     */
    private Long totals = 0L;
    /**
     * 查询结果
     */
    private List<T> data = new ArrayList<>();
}
