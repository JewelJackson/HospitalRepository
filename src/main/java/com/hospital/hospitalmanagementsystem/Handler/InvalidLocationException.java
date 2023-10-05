package com.hospital.hospitalmanagementsystem.Handler;

public class InvalidLocationException extends RuntimeException {

    public InvalidLocationException (String msg){
        super(msg);
    }
}
