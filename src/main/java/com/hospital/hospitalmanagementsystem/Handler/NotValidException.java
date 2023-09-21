package com.hospital.hospitalmanagementsystem.Handler;

public class NotValidException extends RuntimeException{
    public NotValidException (String msg){
        super(msg);
    }
}
