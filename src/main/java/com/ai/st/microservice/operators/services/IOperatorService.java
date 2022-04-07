package com.ai.st.microservice.operators.services;

import java.util.List;

import com.ai.st.microservice.operators.entities.OperatorEntity;

public interface IOperatorService {

    Long getCount();

    OperatorEntity createOperator(OperatorEntity opeatorEntity);

    List<OperatorEntity> getOperatorsByStateId(Long operatorStateId);

    OperatorEntity getOperatorById(Long id);

    List<OperatorEntity> getAllOperators();

    OperatorEntity updateOperator(OperatorEntity operatorEntity);

    OperatorEntity getOperatorByTaxIdentificationNumber(String tin);

}
