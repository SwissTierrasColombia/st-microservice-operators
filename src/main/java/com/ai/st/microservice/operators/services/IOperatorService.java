package com.ai.st.microservice.operators.services;

import java.util.List;

import com.ai.st.microservice.operators.entities.OperatorEntity;

public interface IOperatorService {

    public Long getCount();

    public OperatorEntity createOperator(OperatorEntity opeatorEntity);

    public List<OperatorEntity> getOperatorsByStateId(Long operatorStateId);

    public OperatorEntity getOperatorById(Long id);

    public List<OperatorEntity> getAllOperators();

    public OperatorEntity updateManager(OperatorEntity operatorEntity);

    public OperatorEntity getOperatorByTaxIdentificationNumber(String tin);

}
