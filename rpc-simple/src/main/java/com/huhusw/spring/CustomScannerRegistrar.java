package com.huhusw.spring;

import com.huhusw.annotaion.RpcScan;
import com.huhusw.annotaion.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * scan and filter specified annotations
 * 扫描和过滤特定注解
 */
@Slf4j
public class CustomScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private static final String SPRING_BEAN_PACKAGE = "com.huhusw";
    private static final String BASE_PACKAGE_ATTRIBUTE_NAME = "basePackage";
    //资源加载器
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        //get the attributes and values of RpcScan annotation
        AnnotationAttributes rpcScanAnnotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(RpcScan.class.getName()));
        String[] rpcScanBasePackages = new String[0];
        if (rpcScanAnnotationAttributes != null) {
            // get the value of the basePackage property
            rpcScanBasePackages = rpcScanAnnotationAttributes.getStringArray(BASE_PACKAGE_ATTRIBUTE_NAME);
        }
        //注解没有指定扫描的包，默认扫描annotationMetadata对应的包
        if (rpcScanBasePackages.length == 0) {
            rpcScanBasePackages = new String[]{((StandardAnnotationMetadata) (annotationMetadata)).getIntrospectedClass().getPackage().getName()};
        }
        // Scan the RpcService annotation
        CustomScanner rpcServiceScanner = new CustomScanner(registry, RpcService.class);
        // Scan the Component annotation
        CustomScanner springBeanScanner = new CustomScanner(registry, Component.class);
        if (resourceLoader != null) {
            rpcServiceScanner.setResourceLoader(resourceLoader);
            springBeanScanner.setResourceLoader(resourceLoader);
        }
        int springBeanAmount = springBeanScanner.scan(SPRING_BEAN_PACKAGE);
        log.info("springBeanScanner扫描的数量【{}】", springBeanAmount);
        int rpcServiceCount = rpcServiceScanner.scan(rpcScanBasePackages);
        log.info("rpcServiceScanner扫描的数量【{}】", rpcServiceCount);
    }
}
