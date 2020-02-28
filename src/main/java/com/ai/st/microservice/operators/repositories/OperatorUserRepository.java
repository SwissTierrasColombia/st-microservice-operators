package com.ai.st.microservice.operators.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.operators.entities.OperatorUserEntity;

public interface OperatorUserRepository extends CrudRepository<OperatorUserEntity, Long> {

	List<OperatorUserEntity> findByUserCode(Long userCode);

}
