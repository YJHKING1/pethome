package org.yjhking.pethome.pet.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class PetDetail extends BaseDomain {
    private Long petId;

    private String adoptnotice;

    private String intro;
}