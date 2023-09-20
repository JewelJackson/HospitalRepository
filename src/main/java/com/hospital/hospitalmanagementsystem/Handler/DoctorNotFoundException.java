package com.hospital.hospitalmanagementsystem.Handler;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(String msg) {
        super(msg);
    }
}
