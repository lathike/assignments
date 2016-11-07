package org.lathike.axiomatics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "patients")
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 12)
    @NotNull
    private String socialSecurityNumber;
    @Column(length = 96)
    @NotNull
    private String firstName;
    @Column(length = 96)
    @NotNull
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "doctorid")
    private Doctor doctor;

//    @OneToMany(mappedBy = "patient")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private Set<Radiograph> xrays;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public Set<Radiograph> getXrays() {
//        return xrays;
//    }

//    public void setXrays(Set<Radiograph> xrays) {
//        this.xrays = xrays;
//    }
}
