package com.ai.st.microservice.operators.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.operators.entities.DeliveryEntity;
import com.ai.st.microservice.operators.repositories.DeliveryRepository;

@Service
public class DeliveryService implements IDeliveryService {

	@Autowired
	private DeliveryRepository deliveryRepository;

	@Override
	@Transactional
	public DeliveryEntity createDelivery(DeliveryEntity delivery) {
		return deliveryRepository.save(delivery);
	}

}
