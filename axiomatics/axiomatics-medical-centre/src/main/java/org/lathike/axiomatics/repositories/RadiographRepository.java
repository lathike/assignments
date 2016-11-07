package org.lathike.axiomatics.repositories;

import org.lathike.axiomatics.model.Radiograph;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface RadiographRepository extends CrudRepository<Radiograph, Integer>, QueryDslPredicateExecutor<Radiograph> {

}
