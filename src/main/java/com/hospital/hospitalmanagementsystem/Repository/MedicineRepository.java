package com.hospital.hospitalmanagementsystem.Repository;

import com.hospital.hospitalmanagementsystem.Entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface MedicineRepository extends JpaRepository<Medicine,Integer> {
    Medicine findByMedicineId(int medicineId);
    //Medicine findByMedicineName(List<Medicine> medicineName);

    @Query("SELECT m FROM Medicine m WHERE m.medicineName IN :medicineNames")
    List<Medicine> findAllByNameIn(@Param("medicineNames") List<String> medicineNames);
}
