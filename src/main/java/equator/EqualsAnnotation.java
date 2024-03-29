package equator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在需要比较的属性上添加注解@EqualsAnnotation
 *
 * @author zjt
 * @date 2021/8/15 19:19
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EqualsAnnotation {

    /**
     * 属性名称
     */
    String value() default "";

    /**
     * 属性描述
     */
    String describe() default "";

}
