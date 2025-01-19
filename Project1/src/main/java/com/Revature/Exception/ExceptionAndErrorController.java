package com.Revature.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAndErrorController {
    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody String duplicateUsername(DuplicateUsernameException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String invalidAccount(InvalidAccountException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(UnauthorizedLogin.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody String unauthorizedLogin(UnauthorizedLogin ex){
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody String illegalArgument(IllegalArgumentException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(OtherException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String otherEx(OtherException ex){
        return ex.getMessage();
    }

}
