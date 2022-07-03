package com.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserAlreadyExists extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExists(String message){
        super(message);
    }
}
