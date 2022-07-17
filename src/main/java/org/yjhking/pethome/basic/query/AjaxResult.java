package org.yjhking.pethome.basic.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YJH
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResult {
    
    private Boolean success = true;
    private String msg = "操作成功";
    private Object resultObj;
    
    public AjaxResult(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
    
    /**
     * 静态创建对象，用于链式方法
     *
     * @return AjaxResult
     */
    public static AjaxResult me() {
        return new AjaxResult();
    }
}
