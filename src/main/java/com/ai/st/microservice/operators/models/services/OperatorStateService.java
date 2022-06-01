package com.ai.st.microservice.operators.models.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.operators.entities.OperatorStateEntity;
import com.ai.st.microservice.operators.models.repositories.OperatorStateRepository;

@Service
public class OperatorStateService implements IOperatorStateService {

    @Autowired
    private OperatorStateRepository operatorStateRepository;

    @Override
    public Long getCount() {
        return operatorStateRepository.count();
    }

    @Override
    @Transactional
    public OperatorStateEntity createOperatorState(OperatorStateEntity operatorStateEntity) {
        return operatorStateRepository.save(operatorStateEntity);
    }

    @Override
    public OperatorStateEntity getOperatorById(Long id) {
        return operatorStateRepository.findById(id).orElse(null);
    }

}
