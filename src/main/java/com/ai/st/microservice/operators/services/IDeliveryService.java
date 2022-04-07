package com.ai.st.microservice.operators.services;

import java.util.List;

import com.ai.st.microservice.operators.entities.DeliveryEntity;
import com.ai.st.microservice.operators.entities.OperatorEntity;

public interface IDeliveryService {

    DeliveryEntity createDelivery(DeliveryEntity delivery);

    List<DeliveryEntity> getDeliveriesByOperatorAndActive(OperatorEntity operator, Boolean isActive);

    List<DeliveryEntity> getDeliveriesByOperatorAndMunicipalityAndActive(OperatorEntity operator,
            String municipalityCode, Boolean isActive);

    List<DeliveryEntity> getDeliveriesByOperator(OperatorEntity operator);

    List<DeliveryEntity> getDeliveriesByOperatorAndMunicipality(OperatorEntity operator, String municipalityCode);

    DeliveryEntity getDeliveryById(Long deliveryId);

    DeliveryEntity updateDelivery(DeliveryEntity delivery);

    List<DeliveryEntity> getDeliveriesByManager(Long managerCode);

}
