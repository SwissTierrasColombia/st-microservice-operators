package com.ai.st.microservice.operators.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.operators.entities.OperatorUserEntity;

public interface OperatorUserRepository extends CrudRepository<OperatorUserEntity, Long> {

}
