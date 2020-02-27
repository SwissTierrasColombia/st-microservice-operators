package com.ai.st.microservice.operators.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.operators.entities.DeliveryEntity;

public interface DeliveryRepository extends CrudRepository<DeliveryEntity, Long> {

}
