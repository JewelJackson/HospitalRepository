package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Request.BillingRequest;
import com.hospital.hospitalmanagementsystem.Service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bill")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addBill(@RequestBody BillingRequest billingRequest){
        billingService.payment(billingRequest);
        return ResponseEntity.ok("The bill is added");
    }


}
