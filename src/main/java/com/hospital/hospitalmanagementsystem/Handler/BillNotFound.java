package com.hospital.hospitalmanagementsystem.Handler;

public class BillNotFound extends RuntimeException{
    public BillNotFound(String msg){
        super(msg);
    }
}
