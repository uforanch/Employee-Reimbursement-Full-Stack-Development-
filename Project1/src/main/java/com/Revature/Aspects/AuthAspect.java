package com.Revature.Aspects;

import com.Revature.Exception.InvalidAccountException;
import com.Revature.Models.User;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.UUID;

@Aspect
@Component
public class AuthAspect {
    //private static HttpSession session;


    @Before("@annotation(com.Revature.Aspects.ManagerOnly)")
    public void checkManager(){
        System.out.println(getSessionUserRoles());
        if (!getSessionUserRoles().contains("Manager")){
            throw new InvalidAccountException("Unauthorized User");
        }

    }

    public static List<String> getSessionUserRoles(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(s->s.toString()).toList();
    }

    public static UUID getSessionUser(){
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
    }

}
