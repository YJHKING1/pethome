package org.yjhking.pethome.system.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseDomain {
    private String name;
    
    private String component;
    
    private String url;
    
    private String icon;
    
    private Integer index;
    
    private Long parentId;
    
    private String intro;
    
    private Boolean state;
    /**
     * 父级菜单
     */
    private Menu parent;
    /**
     * 子级菜单，最后一级无此属性
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Menu> children = new ArrayList<>();
}