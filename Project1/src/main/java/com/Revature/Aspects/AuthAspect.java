package com.Revature.Aspects;

import com.Revature.Exception.InvalidAccountException;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthAspect {
    private static HttpSession session;


    @Before("within(com.Revature.Controllers.*) && !execution(* com.Revature.Controllers.AuthController.*(..))")
    @Order(1)
    public void checkLoggedIn(){
        //ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        //HttpSession session = attr.getRequest().getSession(false);
        HttpSession session = AuthAspect.getUserSession();
        System.out.println("Checking logged in");

        if (session == null || session.getAttribute("userId") == null) {
            throw new InvalidAccountException("User is not logged in");
        }
    }

    @Before("@annotation(com.Revature.Aspects.ManagerOnly)")
    public void checkManager(){
        System.out.println(getSessionUserRole());
        if (!"Manager".equals(getSessionUserRole())){

            throw new InvalidAccountException("Unauthorized User");
        }

    }

    //utility methods
    public static int getSessionUserId(){
        //System.out.println(((ServletRequestAttributes)  RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute("userId"));
        //System.out.println((String) ((ServletRequestAttributes)  RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute("userId"));
        //Integer i_or_null = ((Integer) ((ServletRequestAttributes)  RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute("userId"));
        getUserSession();
        Integer i_or_null = (Integer) AuthAspect.session.getAttribute("userId");
        return  (i_or_null==null? 0:i_or_null);
    }

    public static String getSessionUserRole(){
        //return (String) ((ServletRequestAttributes)  RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute("role");
        getUserSession();
        return  (String) AuthAspect.session.getAttribute("role");

    }

    public static String getSessionUsername(){
        //return (String) ((ServletRequestAttributes)  RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute("username");
        getUserSession();
        return  (String) AuthAspect.session.getAttribute("username");

    }

    public static HttpSession  getUserSession(){
        if (AuthAspect.session == null) {
            AuthAspect.session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        }
        return AuthAspect.session;
    }
}
