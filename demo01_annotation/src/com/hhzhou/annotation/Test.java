package com.hhzhou.annotation;

import java.lang.annotation.*;

/**
 * @description:
 * @author: hhzhou
 * @create:2020/10/09
 */
@myAnnotation
public class Test {
    public static void main(String[] args) {

    }

}

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@interface myAnnotation{
    String name() default "hhzhou";
    int[] num() default {1,2,3};
}