package com.bishe.interceptor;

import com.bishe.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class UserInterceptor implements HandlerInterceptor {

    // Controller方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.out.println(request.getRequestURI());
            if (request.getRequestURI().equals("/index/index.html") || request.getRequestURI().equals("/")) {
                Cookie cookie = new Cookie("isLogin", "false");
                response.addCookie(cookie);
                return true;
            }
            String HomePageUrl = "/";
            response.sendRedirect(HomePageUrl);
            return false;
        }

        // 只有返回true才会继续向下执行，返回false取消当前请求
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    //Controller方法执行之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // 整个请求完成后（包括Thymeleaf渲染完毕）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
