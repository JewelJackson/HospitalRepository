package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Request.MedicineRequest;
import com.hospital.hospitalmanagementsystem.Service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addMedicines(@RequestBody MedicineRequest medicineRequest){
        medicineService.add(medicineRequest);
        return ResponseEntity.ok("Medicine added.");
    }
}
