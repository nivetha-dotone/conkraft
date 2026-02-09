package com.wfd.dot1.cwfm.config;


import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/login") // Filter will be applied to the userlogin page
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false); // Retrieve the session without creating a new one if it doesn't exist
        
        // Check if the session is null
        if (session == null || session.getAttribute("loginuser") == null) {
            // If not logged in, redirect to the login page
            res.sendRedirect(req.getContextPath() + "/UserLogin.jsp");
        } else {
            // If logged in, proceed to the userlogin page
            Object loginuser = session.getAttribute("loginuser");
            System.out.println("Value of loginuser attribute: " + loginuser);
            chain.doFilter(request, response);
        }
    }
    // Other methods of the Filter interface
}

