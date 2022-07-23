package org.yjhking.pethome.pet.domain;

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
public class PetType extends BaseDomain {
    private String name;
    
    private String description;
    
    private String dirpath;
    
    private Long pid;
    private PetType parent;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PetType> children = new ArrayList<>();
}