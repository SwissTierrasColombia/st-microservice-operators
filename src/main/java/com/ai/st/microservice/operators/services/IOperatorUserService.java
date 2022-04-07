package com.ai.st.microservice.operators.services;

import java.util.List;

import com.ai.st.microservice.operators.entities.OperatorEntity;
import com.ai.st.microservice.operators.entities.OperatorUserEntity;

public interface IOperatorUserService {

    OperatorUserEntity createUserOperator(OperatorUserEntity operatorUser);

    List<OperatorUserEntity> getOperatorUsersByUserCode(Long userCode);

    OperatorUserEntity getOperatorUserByOperatorAndUserCode(OperatorEntity operator, Long userCode);

}
