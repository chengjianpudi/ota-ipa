package com.raspberry.ota.serviceConfig;

import lombok.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;

/**
 * @program: ota
 * @description: 请求配置
 * @author: Wangshihai
 * @create: 2019-12-31 15:43
 **/
@Component
@ConfigurationProperties(prefix = "spring.test")
public class ServiceConfig {

    private  String hostUrl;
}
