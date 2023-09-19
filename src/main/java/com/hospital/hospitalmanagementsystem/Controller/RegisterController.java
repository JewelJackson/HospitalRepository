package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Entity.*;
import com.hospital.hospitalmanagementsystem.Service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hospital.hospitalmanagementsystem.Handler.*;

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
    public ResponseEntity<String> registerDetails(@Valid @RequestBody @Parameter(name = "adminRequest",
            description = "Admin object",
            required = true,
            content = @Content(schema = @Schema(implementation = Register.class))) Register RequestRegister) throws InvalidException {

            registerService.save(RequestRegister);
            return ResponseEntity.ok("Registered successfully");
    }
}
