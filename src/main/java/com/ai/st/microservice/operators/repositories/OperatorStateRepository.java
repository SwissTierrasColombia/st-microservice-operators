package com.ai.st.microservice.operators.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.operators.entities.OperatorStateEntity;

public interface OperatorStateRepository extends CrudRepository<OperatorStateEntity, Long> {

}
