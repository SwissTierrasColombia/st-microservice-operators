package com.ai.st.microservice.operators.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.operators.entities.OperatorEntity;
import com.ai.st.microservice.operators.repositories.OperatorRepository;

@Service
public class OperatorService implements IOperatorService {

	@Autowired
	private OperatorRepository operatorRepository;

	@Override
	public Long getCount() {
		return operatorRepository.count();
	}

	@Override
	@Transactional
	public OperatorEntity createOperator(OperatorEntity opeatorEntity) {
		return operatorRepository.save(opeatorEntity);
	}

}
