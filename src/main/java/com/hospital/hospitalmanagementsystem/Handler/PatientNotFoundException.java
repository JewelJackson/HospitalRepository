package com.hospital.hospitalmanagementsystem.Handler;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String msg){
        super(msg);
    }
}
