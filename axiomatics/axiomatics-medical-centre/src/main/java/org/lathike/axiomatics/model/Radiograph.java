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
import java.sql.Timestamp;

@Entity
@Table(name = "radiographs")
public class Radiograph implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "patientId")
    @NotNull
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "performedbyradiologistid")
    private Radiologist performedBy;

    @ManyToOne
    @JoinColumn(name = "requesteddoctorsid")
    @NotNull
    private Doctor requestedBy;

    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp requestedOn;

    private Timestamp performedOn;

    private String diagnosis;

    @NotNull
    private String reason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Radiologist getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(Radiologist performedBy) {
        this.performedBy = performedBy;
    }

    public Doctor getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Doctor requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Timestamp getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(Timestamp requestedOn) {
        this.requestedOn = requestedOn;
    }

    public Timestamp getPerformedOn() {
        return performedOn;
    }

    public void setPerformedOn(Timestamp performedOn) {
        this.performedOn = performedOn;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
