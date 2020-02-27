package com.ai.st.microservice.operators.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.operators.dto.CreateDeliverySupplyDto;
import com.ai.st.microservice.operators.dto.DeliveryDto;
import com.ai.st.microservice.operators.dto.OperatorDto;
import com.ai.st.microservice.operators.dto.SupplyDeliveryDto;
import com.ai.st.microservice.operators.entities.DeliveryEntity;
import com.ai.st.microservice.operators.entities.OperatorEntity;
import com.ai.st.microservice.operators.entities.SupplyDeliveredEntity;
import com.ai.st.microservice.operators.exceptions.BusinessException;
import com.ai.st.microservice.operators.services.IDeliveryService;
import com.ai.st.microservice.operators.services.IOperatorService;

@Component
public class DeliveryBusiness {

	@Autowired
	private IOperatorService operatorService;

	@Autowired
	private IDeliveryService deliveryService;

	public DeliveryDto createDelivery(Long operatorId, Long managerCode, String municipalityCode, String observations,
			List<CreateDeliverySupplyDto> supplies) throws BusinessException {

		OperatorEntity operatorEntity = operatorService.getOperatorById(operatorId);
		if (!(operatorEntity instanceof OperatorEntity)) {
			throw new BusinessException("El operador no existe.");
		}

		DeliveryEntity deliveryEntity = new DeliveryEntity();
		deliveryEntity.setCreatedAt(new Date());
		deliveryEntity.setManagerCode(managerCode);
		deliveryEntity.setMunicipalityCode(municipalityCode);
		deliveryEntity.setObservations(observations);
		deliveryEntity.setOperator(operatorEntity);

		List<SupplyDeliveredEntity> suppliesEntity = new ArrayList<SupplyDeliveredEntity>();
		for (CreateDeliverySupplyDto supplyDto : supplies) {
			SupplyDeliveredEntity supplyEntity = new SupplyDeliveredEntity();
			supplyEntity.setCreatedAt(new Date());
			supplyEntity.setDelivery(deliveryEntity);
			supplyEntity.setDownloaded(false);
			supplyEntity.setObservations(supplyDto.getObservations());
			supplyEntity.setSupplyCode(supplyDto.getSupplyCode());
			suppliesEntity.add(supplyEntity);
		}
		deliveryEntity.setSupplies(suppliesEntity);

		deliveryEntity = deliveryService.createDelivery(deliveryEntity);

		DeliveryDto deliveryDto = this.transformEntityToDto(deliveryEntity);

		return deliveryDto;
	}

	private DeliveryDto transformEntityToDto(DeliveryEntity deliveryEntity) {

		DeliveryDto deliveryDto = new DeliveryDto();
		deliveryDto.setId(deliveryEntity.getId());
		deliveryDto.setCreatedAt(deliveryEntity.getCreatedAt());
		deliveryDto.setManagerCode(deliveryEntity.getManagerCode());
		deliveryDto.setMunicipalityCode(deliveryEntity.getMunicipalityCode());
		deliveryDto.setObservations(deliveryEntity.getObservations());

		OperatorEntity operatorEntity = deliveryEntity.getOperator();
		OperatorDto operatorDto = new OperatorDto();
		operatorDto.setId(operatorEntity.getId());
		operatorDto.setIsPublic(operatorEntity.getIsPublic());
		operatorDto.setCreatedAt(operatorEntity.getCreatedAt());
		operatorDto.setName(operatorEntity.getName());
		operatorDto.setTaxIdentificationNumber(operatorEntity.getTaxIdentificationNumber());
		deliveryDto.setOperator(operatorDto);

		for (SupplyDeliveredEntity supplyEntity : deliveryEntity.getSupplies()) {

			SupplyDeliveryDto supplyDto = new SupplyDeliveryDto();
			supplyDto.setId(supplyEntity.getId());
			supplyDto.setCreatedAt(supplyEntity.getCreatedAt());
			supplyDto.setDownloaded(supplyEntity.getDownloaded());
			supplyDto.setDownloadedAt(supplyEntity.getDownloadedAt());
			supplyDto.setObservations(supplyEntity.getObservations());
			supplyDto.setSupplyCode(supplyEntity.getSupplyCode());

			deliveryDto.getSupplies().add(supplyDto);
		}

		return deliveryDto;
	}

}
