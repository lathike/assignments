package org.lathike.axiomatics.repositories;

import org.lathike.axiomatics.model.Patient;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Integer>, QueryDslPredicateExecutor<Patient> {

    Optional<Patient> findBySocialSecurityNumber(String socialSecurityNumber);
}
