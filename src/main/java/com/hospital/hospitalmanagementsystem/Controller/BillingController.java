package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Request.AppointmentRequest;
import com.hospital.hospitalmanagementsystem.Request.BillingRequest;
import com.hospital.hospitalmanagementsystem.Service.BillingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bill")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @Operation(summary = "To generate bill",
            description = "To generate bill and show the total amount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The bill is generated",content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to generate the bill", content = @Content)})
    @PostMapping(value = "/generate")
    public ResponseEntity<String> addBillDetails(@Valid @RequestBody @Parameter(
            name = "billingRequest",
            description = "BillingRequest object",
            required = true,
            content = @Content(schema = @Schema(implementation = BillingRequest.class))) BillingRequest billingRequest){
        double amount = billingService.payment(billingRequest);
        return ResponseEntity.ok("The bill is generated"+"\n"+"Total Amount due : Rs "+amount);
    }


}
