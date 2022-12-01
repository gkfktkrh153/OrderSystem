package com.example.ordersystem.config;

import com.example.ordersystem.repository.ResourcesRepository;
import com.example.ordersystem.service.SecurityResourcesService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class AppConfig { ///

    @Bean
    public SecurityResourcesService securityResourcesService(ResourcesRepository resourcesRepository){
        SecurityResourcesService securityResourcesService = new SecurityResourcesService(resourcesRepository);
        return securityResourcesService;
    }
    /*
        @Bean
    public FilterRegistrationBean encodingFilterBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }
     */


}
