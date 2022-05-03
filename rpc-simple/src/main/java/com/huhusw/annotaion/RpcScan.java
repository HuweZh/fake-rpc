package com.huhusw.annotaion;

import com.huhusw.spring.CustomScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * scan custom annotations
 * 扫描自定义注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegistrar.class)
@Documented
public @interface RpcScan {
    //要扫描的包
    String[] basePackage();
}
