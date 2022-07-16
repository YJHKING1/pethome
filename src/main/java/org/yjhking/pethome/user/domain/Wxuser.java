package org.yjhking.pethome.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Wxuser extends BaseDomain {
    private String openid;

    private String nickname;

    private Integer sex;

    private String address;

    private String headimgurl;

    private String unionid;

    private Long userId;
}