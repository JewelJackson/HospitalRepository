package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Request.BillingRequest;
import com.hospital.hospitalmanagementsystem.Request.MedicineRequest;
import com.hospital.hospitalmanagementsystem.Service.MedicineService;
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
@RequestMapping(value = "/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @Operation(summary = "Add medicines",
            description = "To add medicines")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicine added successfully",content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to add medicine", content = @Content)})
    @PostMapping(value = "/add")
    public ResponseEntity<String> addMedicines(@RequestBody @Parameter(
            name = "medicineRequest",
            description = "MedicineRequest object",
            required = true,
            content = @Content(schema = @Schema(implementation = MedicineRequest.class)))MedicineRequest medicineRequest){
        medicineService.add(medicineRequest);
        return ResponseEntity.ok("Medicine added.");
    }
}
