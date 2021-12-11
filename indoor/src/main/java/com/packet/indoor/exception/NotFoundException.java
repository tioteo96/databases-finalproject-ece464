package com.packet.indoor.exception;

import com.packet.indoor.util.ErrorMessage;

public class NotFoundException extends RuntimeException{
    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage.value());
    }

}
