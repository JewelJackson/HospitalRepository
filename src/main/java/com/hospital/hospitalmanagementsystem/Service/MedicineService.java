package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.Medicine;
import com.hospital.hospitalmanagementsystem.Repository.MedicineRepository;
import com.hospital.hospitalmanagementsystem.Request.MedicineRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public void add(MedicineRequest medicineRequest){
        Medicine medicine = new Medicine();

        medicine.setMedicineName(medicineRequest.getMedicineName());
        medicine.setPrice(medicineRequest.getPrice());

        medicineRepository.save(medicine);
    }
}
