package com.hospital.hospitalmanagementsystem.Handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {
    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<String> handleInvalidField(InvalidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<String> handleNotValidField(NotValidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<String> handleDoctorNotFound(DoctorNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> handleAdminNotFound(PatientNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(ReceptionistNotFoundException.class)
    public ResponseEntity<String> handleReceptionistNotFound(ReceptionistNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(AppointmentNotFound.class)
    public ResponseEntity<String> handleAppointmentNotFound(AppointmentNotFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(MedicineNotFound.class)
    public ResponseEntity<String> handleMedicineNotFound(MedicineNotFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(BillNotFound.class)
    public ResponseEntity<String> handleBillNotFound(BillNotFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(InvalidLocationException.class)
    public ResponseEntity<String> handleInvalidLocation(InvalidLocationException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError() != null
                ? ex.getBindingResult().getFieldError().getDefaultMessage()
                : "Validation failed.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
