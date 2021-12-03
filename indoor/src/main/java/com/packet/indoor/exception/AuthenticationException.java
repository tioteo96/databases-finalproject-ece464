package com.packet.indoor.exception;

import com.packet.indoor.util.ErrorMessage;

public class AuthenticationException extends SecurityException{
    public AuthenticationException(ErrorMessage errorMessage){
        super(errorMessage.value());
    }
}
