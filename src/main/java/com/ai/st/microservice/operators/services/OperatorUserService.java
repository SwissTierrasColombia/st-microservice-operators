package com.ai.st.microservice.operators.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.operators.entities.OperatorUserEntity;
import com.ai.st.microservice.operators.repositories.OperatorUserRepository;

@Service
public class OperatorUserService implements IOperatorUserService {

	@Autowired
	private OperatorUserRepository operatorUserRepository;

	@Override
	@Transactional
	public OperatorUserEntity createUserOperator(OperatorUserEntity operatorUser) {
		return operatorUserRepository.save(operatorUser);
	}

}
