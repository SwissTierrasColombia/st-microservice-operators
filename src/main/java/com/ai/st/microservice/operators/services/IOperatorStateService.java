package com.ai.st.microservice.operators.services;

import com.ai.st.microservice.operators.entities.OperatorStateEntity;

public interface IOperatorStateService {

    public Long getCount();

    public OperatorStateEntity createOperatorState(OperatorStateEntity operatorStateEntity);

    public OperatorStateEntity getOperatorById(Long id);

}
