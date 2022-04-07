package com.ai.st.microservice.operators.models.services;

import com.ai.st.microservice.operators.entities.OperatorStateEntity;

public interface IOperatorStateService {

    Long getCount();

    OperatorStateEntity createOperatorState(OperatorStateEntity operatorStateEntity);

    OperatorStateEntity getOperatorById(Long id);

}
