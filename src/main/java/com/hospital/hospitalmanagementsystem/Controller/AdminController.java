package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Entity.Admin;
import com.hospital.hospitalmanagementsystem.Response.AdminResponse;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Operation(summary = "Login for Admin",
            description = "login admin by using email and password as inputs.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "Logged in successfully"),
    @ApiResponse(responseCode = "404",description = "Login failed")})
    @PostMapping(value="/login")
    public AdminResponse adminLoginDetails(@Valid @RequestBody @Parameter(
            name = "adminRequest",
            description = "Admin object",
            required = true,
            content = @Content(schema = @Schema(implementation = AdminResponse.class)))
            Admin adminRequest) throws InvalidException {
        return adminService.adminLogin(adminRequest);
    }
}


