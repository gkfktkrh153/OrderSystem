package com.example.ordersystem.security.handler;

import com.example.ordersystem.domain.entity.Account;
import com.example.ordersystem.domain.entity.Role;
import com.example.ordersystem.security.service.AccountContext;
import com.example.ordersystem.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    // 사용자가 거쳐 왔던 요청에 관련된 정보를 담고있는 세션객체를 참조

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 인증에 성공했을 때 인증 정보를 담은 인증객체 authentication
        //setDefaultTargetUrl("/admin");
        //redirectStrategy.sendRedirect(request,response,getDefaultTargetUrl());

        Account account= (Account) authentication.getPrincipal();
        // Authentication 객체의 getPrincipal() 메서드를 실행하게 되면, UserDetails를 구현한 사용자 객체를 Return 한다.
        String username = account.getUsername();

        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(username);
        Set<Role> userRoles = accountContext.getAccount().getUserRoles();

        HttpSession session = request.getSession();
        session.setAttribute("name", username);

        for (Role userRole :userRoles){
            if (userRole.getRoleName().equals("ROLE_USER")){
                System.out.println(username);
                redirectStrategy.sendRedirect(request,response,"/user");
            }
            else if(userRole.getRoleName().equals("ROLE_ADMIN"))
            {
                System.out.println(username);
                redirectStrategy.sendRedirect(request,response,"/admin");
            }
        }



        /*
        SavedRequest savedRequest = requestCache.getRequest(request, response);
         if (savedRequest != null) {
         String targetUrl = savedRequest.getRedirectUrl();
         System.out.println("savedRequst != null ->"+targetUrl);
         redirectStrategy.sendRedirect(request, response, targetUrl);

         } else {
         System.out.println("savedRequst == null ->"+getDefaultTargetUrl());
         redirectStrategy.sendRedirect(request,response,getDefaultTargetUrl());

         }
         */



    }
}
