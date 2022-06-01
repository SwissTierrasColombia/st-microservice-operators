package com.ai.st.microservice.operators.models.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.operators.entities.OperatorEntity;
import com.ai.st.microservice.operators.entities.OperatorUserEntity;
import com.ai.st.microservice.operators.models.repositories.OperatorUserRepository;

@Service
public class OperatorUserService implements IOperatorUserService {

    @Autowired
    private OperatorUserRepository operatorUserRepository;

    @Override
    @Transactional
    public OperatorUserEntity createUserOperator(OperatorUserEntity operatorUser) {
        return operatorUserRepository.save(operatorUser);
    }

    @Override
    public List<OperatorUserEntity> getOperatorUsersByUserCode(Long userCode) {
        return operatorUserRepository.findByUserCode(userCode);
    }

    @Override
    public OperatorUserEntity getOperatorUserByOperatorAndUserCode(OperatorEntity operator, Long userCode) {
        return operatorUserRepository.findByOperatorAndUserCode(operator, userCode);
    }

}
