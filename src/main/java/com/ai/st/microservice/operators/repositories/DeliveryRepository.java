package com.ai.st.microservice.operators.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.operators.entities.DeliveryEntity;
import com.ai.st.microservice.operators.entities.OperatorEntity;

public interface DeliveryRepository extends CrudRepository<DeliveryEntity, Long> {

	List<DeliveryEntity> findByOperatorAndIsActive(OperatorEntity operator, Boolean isActive);

	List<DeliveryEntity> findByOperatorAndMunicipalityCodeAndIsActive(OperatorEntity operator, String municipalityCode,
			Boolean isActive);

	List<DeliveryEntity> findByOperator(OperatorEntity operator);

	List<DeliveryEntity> findByOperatorAndMunicipalityCode(OperatorEntity operator, String municipalityCode);

}
