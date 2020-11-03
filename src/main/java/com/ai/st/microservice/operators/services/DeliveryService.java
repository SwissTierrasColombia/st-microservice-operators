package com.ai.st.microservice.operators.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.operators.entities.DeliveryEntity;
import com.ai.st.microservice.operators.entities.OperatorEntity;
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

	@Override
	public List<DeliveryEntity> getDeliveriesByOperatorAndActive(OperatorEntity operator, Boolean isActive) {
		return deliveryRepository.findByOperatorAndIsActive(operator, isActive);
	}

	@Override
	public List<DeliveryEntity> getDeliveriesByOperatorAndMunicipalityAndActive(OperatorEntity operator,
			String municipalityCode, Boolean isActive) {
		return deliveryRepository.findByOperatorAndMunicipalityCodeAndIsActive(operator, municipalityCode, isActive);
	}

	@Override
	public List<DeliveryEntity> getDeliveriesByOperator(OperatorEntity operator) {
		return deliveryRepository.findByOperator(operator);
	}

	@Override
	public List<DeliveryEntity> getDeliveriesByOperatorAndMunicipality(OperatorEntity operator,
			String municipalityCode) {
		return deliveryRepository.findByOperatorAndMunicipalityCode(operator, municipalityCode);
	}

	@Override
	public DeliveryEntity getDeliveryById(Long deliveryId) {
		return deliveryRepository.findById(deliveryId).orElse(null);
	}

	@Override
	@Transactional
	public DeliveryEntity updateDelivery(DeliveryEntity delivery) {
		return deliveryRepository.save(delivery);
	}

	@Override
	public List<DeliveryEntity> getDeliveriesByManager(Long managerCode) {
		return deliveryRepository.findByManagerCode(managerCode);
	}

}
