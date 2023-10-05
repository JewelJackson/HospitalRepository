package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Request.RegisterRequest;
import com.hospital.hospitalmanagementsystem.Service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.hospital.hospitalmanagementsystem.Handler.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Validated
@RequestMapping(value = "/hospital")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Operation(summary = "Registration",
            description = "Registering new admin, doctor, patient, receptionist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Registered successfully"),
            @ApiResponse(responseCode = "404",description = "Registration failed or validation error")})
    @PostMapping(value = "/register")
    public ResponseEntity<String> registerDetails(@Valid @RequestBody @Parameter(name = "registerRequest",
            description = "RegisterRequest object",
            required = true,
            content = @Content(schema = @Schema(implementation = RegisterRequest.class))) RegisterRequest registerRequest) throws InvalidException {
            registerService.register(registerRequest);
            return ResponseEntity.ok("Registered successfully");
    }

    @Operation(summary = "Upload data",
            description = "Upload data from CSV file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Successfully uploaded"),
            @ApiResponse(responseCode = "404",description = "Uploading failed")})
    @PostMapping(value = "/upload/csv")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) throws IOException {
        registerService.extractFromCSV(file);
        return ResponseEntity.ok("The patient details have been successfully uploaded to the database.");
    }
}
