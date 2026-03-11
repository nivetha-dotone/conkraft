package com.wfd.dot1.cwfm.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionTimeoutFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // ✅ Allow login page and static resources
        if (uri.contains("UserLogin.jsp") ||
            uri.contains("login") ||
            uri.contains("css") ||
            uri.contains("js") ||
            uri.contains("images")) {

            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));


        if (session == null || session.getAttribute("loginuser") == null) {

            if (session != null) {
                session.invalidate();   // ✅ clears all session attributes including loginuser
            }

            if (isAjax) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                res.sendRedirect(req.getContextPath() + "/UserLogin.jsp");
            }

            return;
        }

        chain.doFilter(request, response);
    }
}