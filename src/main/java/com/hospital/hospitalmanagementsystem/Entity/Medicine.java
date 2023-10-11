package com.hospital.hospitalmanagementsystem.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicineId;
    @Column(unique = true)
    private String medicineName;
    //@Size(min = 0,message = "Medicine price cannot be negative.")
    private double price;


    public Medicine(String medicine2, double m) {
    }
}
