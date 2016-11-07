package org.lathike.axiomatics.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "R")
@Table(name = "radiologists")
public class Radiologist extends MedicalStaff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String assignedLab;

    @OneToMany(mappedBy = "performedBy")
    private Set<Radiograph> radiographs;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getAssignedLab() {
        return assignedLab;
    }

    public void setAssignedLab(String assignedLab) {
        this.assignedLab = assignedLab;
    }

    public Set<Radiograph> getRadiographs() {
        return radiographs;
    }

    public void setRadiographs(Set<Radiograph> radiographs) {
        this.radiographs = radiographs;
    }
}
