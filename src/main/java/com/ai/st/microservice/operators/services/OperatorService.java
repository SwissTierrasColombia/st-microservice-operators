package com.ai.st.microservice.operators.services;

import java.util.List;

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

	@Override
	public List<OperatorEntity> getOperatorsByStateId(Long operatorStateId) {
		return operatorRepository.getOperatorsByStateId(operatorStateId);
	}

	@Override
	public OperatorEntity getOperatorById(Long id) {
		return operatorRepository.findById(id).orElse(null);
	}

	@Override
	public List<OperatorEntity> getAllOperators() {
		return operatorRepository.findAll();
	}

	@Override
	public OperatorEntity updateManager(OperatorEntity operatorEntity) {
		return operatorRepository.save(operatorEntity);
	}

}
