package com.ai.st.microservice.operators.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.operators.entities.OperatorEntity;

public interface OperatorRepository extends CrudRepository<OperatorEntity, Long> {

}
