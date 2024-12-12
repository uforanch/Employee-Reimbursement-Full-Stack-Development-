package com.Revature.Exception;

public class UnauthorizedLogin extends RuntimeException{
    public UnauthorizedLogin(String message){
        super(message);
    }
}
