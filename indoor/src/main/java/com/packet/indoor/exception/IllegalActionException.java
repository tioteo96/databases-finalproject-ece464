package com.packet.indoor.exception;

import com.packet.indoor.util.ErrorMessage;

public class IllegalActionException extends RuntimeException{
    public IllegalActionException(ErrorMessage errorMessage) {super(errorMessage.value());}
}
