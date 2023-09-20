package com.hospital.hospitalmanagementsystem.Repository;

import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Component
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    Doctor findByEmail(@NotNull(message = "Email required") String email);

    Optional<Doctor> findByDoctorId(int doctorId);

    List<Doctor> findByDoctorStatus(String doctorStatus);
}
