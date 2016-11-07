package org.lathike.axiomatics.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "D")
@Table(name = "doctors")
public class Doctor extends MedicalStaff implements Serializable {

    @ManyToOne
    @JoinColumn(name = "specializationid")
    private MedicalSpecialization specialization;

    @OneToMany(mappedBy = "doctor")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Patient> patients;

    public MedicalSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(MedicalSpecialization specialization) {
        this.specialization = specialization;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }
}
