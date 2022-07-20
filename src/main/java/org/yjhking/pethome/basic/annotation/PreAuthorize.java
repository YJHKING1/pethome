package org.yjhking.pethome.basic.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 权限
 *
 * @author YJH
 */
@Target({java.lang.annotation.ElementType.METHOD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthorize {
    /**
     * name
     *
     * @return name
     */
    String name();
    
    /**
     * sn
     *
     * @return sn
     */
    String sn();
}
