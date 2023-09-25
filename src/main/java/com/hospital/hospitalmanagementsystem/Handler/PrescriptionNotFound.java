package com.hospital.hospitalmanagementsystem.Handler;

public class PrescriptionNotFound extends RuntimeException{
    public PrescriptionNotFound(String msg) {
        super(msg);
    }
}
