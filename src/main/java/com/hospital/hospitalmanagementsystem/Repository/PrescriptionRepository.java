package com.hospital.hospitalmanagementsystem.Repository;

import com.hospital.hospitalmanagementsystem.Entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Component
public interface PrescriptionRepository extends JpaRepository<Prescription,Integer> {

    Optional<Prescription> findByPrescriptionId(int prescription);

    //Prescription findByMedicineName(String medicineName);
}
