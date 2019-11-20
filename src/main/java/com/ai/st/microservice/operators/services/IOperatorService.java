package com.ai.st.microservice.operators.services;

import com.ai.st.microservice.operators.entities.OperatorEntity;

public interface IOperatorService {

	public Long getCount();

	public OperatorEntity createOperator(OperatorEntity opeatorEntity);

}
