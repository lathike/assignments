package org.lathike.axiomatics.repositories;

import org.lathike.axiomatics.model.MedicalStaff;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MedicalStaffRepository extends CrudRepository<MedicalStaff, Integer> {

    Optional<MedicalStaff> findBySocialSecurityNumber(String socialSecurityNumber);
}
