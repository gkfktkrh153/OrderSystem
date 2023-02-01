package com.example.ordersystem.service;

import com.example.ordersystem.domain.entity.Resources;
import com.example.ordersystem.repository.ResourcesRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SecurityResourcesService {


    private final ResourcesRepository resourcesRepository;

    public SecurityResourcesService(ResourcesRepository resourcesRepository) {
        this.resourcesRepository = resourcesRepository;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList(){
        System.out.println("__________________________getResourceList_____________________________");
        LinkedHashMap<RequestMatcher,List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourceList = resourcesRepository.findAllResources();
        resourceList.forEach(re -> { // re -> Resources(id=8, resourceName=/user, httpMethod=, orderNum=4, resourceType=url)
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            re.getRoleSet().forEach(role ->{ // Resource하나 당 여러개의 Role이 담긴다.
                System.out.println(re.getResourceName());
                System.out.println(role.getRoleName());
                configAttributeList.add(new SecurityConfig(role.getRoleName()));
                // ConfigAttribute의 구현체인 SecurityConfig 타입으로 객체를 담는다
                 result.put(new AntPathRequestMatcher(re.getResourceName()), configAttributeList);
            });
        });
        return result;
    }
}
