package com.Revature.Controllers;

import com.Revature.Aspects.AuthAspect;
import com.Revature.Models.DTOs.OutgoingUser;
import com.Revature.Models.User;
import com.Revature.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {
    UserService userService;
    HttpSession session;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
        //session = AuthAspect.getUserSession();
        
    }

    public void getSession(){
        if (session==null){
            session = AuthAspect.getUserSession();
        }
    }

    @PostMapping("login")
    public ResponseEntity<OutgoingUser> login(@RequestBody User user){//, HttpSession session){
        getSession();

        OutgoingUser valid_user = userService.login(user);
        System.out.println(valid_user);
        System.out.println("Before setting");
        System.out.println(session.getAttribute("username"));
        session.setAttribute("userId", valid_user.getUserId());
        session.setAttribute("username", valid_user.getUsername());
        session.setAttribute("role", valid_user.getRole());
        System.out.println("After setting");
        System.out.println(session.getAttribute("username"));

        //need user id for future calls


        return ResponseEntity.ok(valid_user);
    }

    @PostMapping("register")
    public ResponseEntity<OutgoingUser> register(@RequestBody User user){//}, HttpSession session){
        //HttpSession session = AuthAspect.getUserSession();
        getSession();
        OutgoingUser saved_user = userService.register(user);
        System.out.println(saved_user);
        session.setAttribute("userId", saved_user.getUserId());
        session.setAttribute("username", saved_user.getUsername());
        session.setAttribute("role",saved_user.getRole());
        System.out.println(session.getAttribute("userId"));
        System.out.println(session.getAttribute("username"));
        System.out.println(session.getAttribute("role"));

        return ResponseEntity.status(HttpStatus.CREATED).body(saved_user);
    }

    @GetMapping()
    public ResponseEntity<OutgoingUser> getAuth(){//(HttpSession session){
        ///HttpSession session = AuthAspect.getUserSession();
        getSession();
        if (session.getAttribute("userId")==null){
            //throw new IllegalArgumentException("Not logged in");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new OutgoingUser());
        }
        System.out.println("AUTH username");
        System.out.println(session.getAttribute("username"));

        System.out.println("AUTH userid");
        System.out.println(session.getAttribute("userId"));
        return ResponseEntity.ok(userService.retrieveUser(((Integer) session.getAttribute("userId"))));

    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(){//(HttpSession session){
        getSession();
        //HttpSession session = AuthAspect.getUserSession();
        System.out.println("Before setting");
        System.out.println(session.getAttribute("username"));
        session.setAttribute("userId",null);
        session.setAttribute("username",null);
        session.setAttribute("role",null);
        System.out.println("After setting");
        //session.invalidate();
        System.out.println(session.getAttribute("username"));
        return ResponseEntity.ok("logged out");
    }

}
