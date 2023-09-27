package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Entity.Prescription;
import com.hospital.hospitalmanagementsystem.Request.BillingRequest;
import com.hospital.hospitalmanagementsystem.Request.PrescriptionRequest;
import com.hospital.hospitalmanagementsystem.Service.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Add prescription",
            description = "To add prescription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prescription saved successfully",content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed save the prescription", content = @Content)})
    @PostMapping(value = "/add")
    public ResponseEntity<String> getPrescription(@RequestBody @Parameter(
            name = "prescriptionRequest",
            description = "PrescriptionRequest object",
            required = true,
            content = @Content(schema = @Schema(implementation = PrescriptionRequest.class))) PrescriptionRequest prescriptionRequest){
        prescriptionService.addPrescription(prescriptionRequest);
        return ResponseEntity.ok("Saved");
    }
}
