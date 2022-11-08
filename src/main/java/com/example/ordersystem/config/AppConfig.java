package com.example.ordersystem.config;

import com.example.ordersystem.repository.ResourcesRepository;
import com.example.ordersystem.service.SecurityResourcesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SecurityResourcesService securityResourcesService(ResourcesRepository resourcesRepository){
        SecurityResourcesService securityResourcesService = new SecurityResourcesService(resourcesRepository);
        return securityResourcesService;
    }
}
