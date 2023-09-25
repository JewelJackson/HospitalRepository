package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Entity.Prescription;
import com.hospital.hospitalmanagementsystem.Request.PrescriptionRequest;
import com.hospital.hospitalmanagementsystem.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/prescription")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> getPrescription(@RequestBody PrescriptionRequest prescriptionRequest){
        prescriptionService.addPrescription(prescriptionRequest);
        return ResponseEntity.ok("Saved");
    }
}
