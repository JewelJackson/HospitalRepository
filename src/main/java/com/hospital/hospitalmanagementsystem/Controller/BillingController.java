package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Request.BillingRequest;
import com.hospital.hospitalmanagementsystem.Service.BillingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bill")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping(value = "/generate")
    public ResponseEntity<String> addBillDetails(@Valid @RequestBody BillingRequest billingRequest){
        //billingService.payment(billingRequest);
        double amount = billingService.payment(billingRequest);
        return ResponseEntity.ok("The bill is generated"+"\n"+"Total Amount due : Rs "+amount);
    }


}
