package com.ai.st.microservice.operators.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ai.st.microservice.operators.entities.OperatorEntity;

public interface OperatorRepository extends CrudRepository<OperatorEntity, Long> {

	@Override
	List<OperatorEntity> findAll();

	@Query("SELECT o FROM OperatorEntity o WHERE o.operatorState.id = :operatorStateId")
	List<OperatorEntity> getOperatorsByStateId(@Param("operatorStateId") Long operatorStateId);

	OperatorEntity findByTaxIdentificationNumber(String tin);

}
