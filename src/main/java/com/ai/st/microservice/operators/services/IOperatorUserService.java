package com.ai.st.microservice.operators.services;

import java.util.List;

import com.ai.st.microservice.operators.entities.OperatorEntity;
import com.ai.st.microservice.operators.entities.OperatorUserEntity;

public interface IOperatorUserService {

	public OperatorUserEntity createUserOperator(OperatorUserEntity operatorUser);

	public List<OperatorUserEntity> getOperatorUsersByUserCode(Long userCode);

	public OperatorUserEntity getOperatorUserByOperatorAndUserCode(OperatorEntity operator, Long userCode);

}
