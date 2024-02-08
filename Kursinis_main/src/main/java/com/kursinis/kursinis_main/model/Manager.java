package com.kursinis.kursinis_main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends User {

    private String employeeId;
    private String medCertificate;
    private LocalDate employmentDate;
    private boolean isAdmin;

    @OneToMany(mappedBy = "managesWarehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Warehouse> managedWarehouses;

    public Manager(String login, String password, LocalDate birthDate, String name, String surname, String employeeId, String medCertificate, LocalDate employmentDate, boolean isAdmin) {
        super(login, password, birthDate, name, surname);
        this.employeeId = employeeId;
        this.medCertificate = medCertificate;
        this.employmentDate = employmentDate;
        this.isAdmin = isAdmin;
    }

    public Manager(String login, String password, LocalDate birthDate) {

    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
