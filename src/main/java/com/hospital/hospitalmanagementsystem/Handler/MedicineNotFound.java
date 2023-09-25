package com.hospital.hospitalmanagementsystem.Handler;

public class MedicineNotFound extends RuntimeException{
    public MedicineNotFound (String msg){
        super(msg);
    }
}
