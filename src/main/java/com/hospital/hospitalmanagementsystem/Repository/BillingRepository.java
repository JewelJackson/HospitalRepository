package com.hospital.hospitalmanagementsystem.Repository;

import com.hospital.hospitalmanagementsystem.Entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface BillingRepository extends JpaRepository<Billing,Integer> {
    @Query("SELECT b FROM Billing b WHERE b.patient.id = :patientId")
    List<Billing> findByPatientId(@Param("patientId") int patientId);

    @Query("SELECT SUM(b.totalAmount) FROM Billing b WHERE b.patient.id = :patientId")
    Double getTotalAmountByPatientId(@Param("patientId") int patientId);
    //Double getTotalAmountByPatientId(int patientId);
}
