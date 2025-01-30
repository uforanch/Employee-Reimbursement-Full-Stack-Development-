package com.Revature.Controllers;

import com.Revature.Aspects.AuthAspect;
import com.Revature.Exception.OtherException;
import com.Revature.Models.DTOs.IncomingLogin;
import com.Revature.Models.DTOs.OutgoingUser;
import com.Revature.Models.DTOs.OutgoingUserAndTokenDTO;
import com.Revature.Models.User;
import com.Revature.Security.JwtTokenUtil;
import com.Revature.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    private OutgoingUserAndTokenDTO login_function(String username, String password){
        System.out.println(username + " : " + password);
        try {

            System.out.println("login function entered");
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            System.out.println("authenticated");
            //User valid_user = userService.getUserByUsername(user.username());
            User valid_user = (User) auth.getPrincipal();
            System.out.println("principal obtained");
            String token = jwtTokenUtil.generateAccessToken(valid_user);
            System.out.println("token obtained");
            return new OutgoingUserAndTokenDTO(new OutgoingUser(valid_user), token);
        } catch (Exception ex){
            throw new OtherException(ex.getMessage());
        }


    }



    @PostMapping("login")
    public ResponseEntity<OutgoingUserAndTokenDTO> login(@RequestBody IncomingLogin user){
        return ResponseEntity.ok(login_function(user.username(), user.password()));
    }

    @PostMapping("register")
    public ResponseEntity<OutgoingUserAndTokenDTO> register(@RequestBody User user){
        String password = user.getPassword();
        OutgoingUser saved_user = userService.register(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(login_function(user.getUsername(), password));
    }
    // since we aren't tracking session and just tracking token, no longer a purpose for these functions

//
//    @GetMapping()
//    public ResponseEntity<OutgoingUser> getAuth(){//(HttpSession session){
//        ///HttpSession session = AuthAspect.getUserSession();
//
//        if (session.getAttribute("userId")==null){
//            //throw new IllegalArgumentException("Not logged in");
//            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new OutgoingUser());
//        }
//
//    }
//
//    @PostMapping("logout")
//    public ResponseEntity<String> logout(){//(HttpSession session){
//        getSession();
//        //HttpSession session = AuthAspect.getUserSession();
//        System.out.println("Before setting");
//        System.out.println(session.getAttribute("username"));
//        session.setAttribute("userId",null);
//        session.setAttribute("username",null);
//        session.setAttribute("role",null);
//        System.out.println("After setting");
//        //session.invalidate();
//        System.out.println(session.getAttribute("username"));
//        return ResponseEntity.ok("logged out");
//    }

}
