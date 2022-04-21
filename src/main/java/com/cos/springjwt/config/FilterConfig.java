package com.cos.springjwt.config;

import com.cos.springjwt.filter.MyFilter1;
import com.cos.springjwt.filter.MyFilter2;
import com.cos.springjwt.filter.MyFilter3;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterRegistration;

@Configuration
public class FilterConfig {

//    @Bean
//    public FilterRegistrationBean<MyFilter2> filter2(){
//        FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
//        bean.addUrlPatterns("/*");
//        bean.setOrder(1);
//        return bean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<MyFilter3> filter3(){
//        FilterRegistrationBean<MyFilter3> bean = new FilterRegistrationBean<>(new MyFilter3());
//        bean.addUrlPatterns("/*");
//        bean.setOrder(2);
//        return bean;
//    }
}
