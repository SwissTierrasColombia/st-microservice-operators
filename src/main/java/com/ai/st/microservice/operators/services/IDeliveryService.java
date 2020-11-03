package com.ai.st.microservice.operators.services;

import java.util.List;

import com.ai.st.microservice.operators.entities.DeliveryEntity;
import com.ai.st.microservice.operators.entities.OperatorEntity;

public interface IDeliveryService {

	public DeliveryEntity createDelivery(DeliveryEntity delivery);

	public List<DeliveryEntity> getDeliveriesByOperatorAndActive(OperatorEntity operator, Boolean isActive);

	public List<DeliveryEntity> getDeliveriesByOperatorAndMunicipalityAndActive(OperatorEntity operator,
			String municipalityCode, Boolean isActive);

	public List<DeliveryEntity> getDeliveriesByOperator(OperatorEntity operator);

	public List<DeliveryEntity> getDeliveriesByOperatorAndMunicipality(OperatorEntity operator,
			String municipalityCode);

	public DeliveryEntity getDeliveryById(Long deliveryId);

	public DeliveryEntity updateDelivery(DeliveryEntity delivery);

	public List<DeliveryEntity> getDeliveriesByManager(Long managerCode);

}
