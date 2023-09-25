package com.hospital.hospitalmanagementsystem.Handler;

public class AppointmentNotFound extends RuntimeException{
    public AppointmentNotFound(String msg) {
        super(msg);
    }
}
