package com.example.se2project.interceptor;

import com.example.se2project.controller.user.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class LogInterceptor extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        MyUserDetails customUserDetails = (MyUserDetails) authentication.getPrincipal();
        String redirectURL = request.getContextPath();
        if (customUserDetails.hasRole("Admin")) {
            redirectURL += "/admin_home";
        }
        response.sendRedirect(redirectURL);
    }
}
