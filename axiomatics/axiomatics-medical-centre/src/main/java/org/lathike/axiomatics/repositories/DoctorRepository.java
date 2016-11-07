package org.lathike.axiomatics.repositories;

import org.lathike.axiomatics.model.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

    List<Doctor> findAll();
}
