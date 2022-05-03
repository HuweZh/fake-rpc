package com.huhusw.annotaion;

import java.lang.annotation.*;

/**
 * RPC reference annotation, autowire the service implementation class
 * RPC引用注解，自动装配服务实现类
 */
//元注解，生成工具类是，保留其注释信息
@Documented
//元注解，注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
@Retention(RetentionPolicy.RUNTIME)
//元注解，适用范围，作用于字段
@Target({ElementType.FIELD})
//元注解，被它修饰的注解具有继承性
@Inherited
public @interface RpcReference {
    /**
     * Service version, default value is empty string
     */
    String version() default "";

    /**
     * Service group, default value is empty string
     */
    String group() default "";
}
