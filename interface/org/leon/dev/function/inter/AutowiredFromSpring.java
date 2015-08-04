package org.leon.dev.function.inter;


import java.lang.annotation.*;

/**
 * Created by LeonWong on 15/7/4.
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutowiredFromSpring {
    boolean required() default true;
}