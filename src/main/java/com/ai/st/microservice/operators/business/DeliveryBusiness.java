package com.ai.st.microservice.operators.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        if (operatorEntity == null) {
            throw new BusinessException("El operador no existe.");
        }

        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.setCreatedAt(new Date());
        deliveryEntity.setManagerCode(managerCode);
        deliveryEntity.setMunicipalityCode(municipalityCode);
        deliveryEntity.setObservations(observations);
        deliveryEntity.setOperator(operatorEntity);
        deliveryEntity.setIsActive(true);

        List<SupplyDeliveredEntity> suppliesEntity = new ArrayList<>();
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

        return this.transformEntityToDto(deliveryEntity);
    }

    public List<DeliveryDto> getDeliveriesByOperator(Long operatorId, String municipalityCode, Boolean isActive)
            throws BusinessException {

        List<DeliveryDto> deliveriesDto = new ArrayList<>();

        OperatorEntity operatorEntity = operatorService.getOperatorById(operatorId);
        if (operatorEntity == null) {
            throw new BusinessException("El operador no existe.");
        }

        List<DeliveryEntity> deliveriesEntity;

        if (municipalityCode != null && !municipalityCode.isEmpty()) {

            if (isActive == null) {
                deliveriesEntity = deliveryService.getDeliveriesByOperatorAndMunicipality(operatorEntity,
                        municipalityCode);
            } else {
                deliveriesEntity = deliveryService.getDeliveriesByOperatorAndMunicipalityAndActive(operatorEntity,
                        municipalityCode, isActive);
            }

        } else {

            if (isActive == null) {
                deliveriesEntity = deliveryService.getDeliveriesByOperator(operatorEntity);
            } else {
                deliveriesEntity = deliveryService.getDeliveriesByOperatorAndActive(operatorEntity, isActive);
            }

        }

        for (DeliveryEntity deliveryEntity : deliveriesEntity) {
            DeliveryDto deliveryDto = this.transformEntityToDto(deliveryEntity);
            deliveriesDto.add(deliveryDto);
        }

        return deliveriesDto;
    }

    public DeliveryDto updateSupplyDelivered(Long deliveryId, Long supplyId, String observations, Boolean downloaded,
            Long downloadedBy, String reportUrl) throws BusinessException {

        DeliveryEntity deliveryEntity = deliveryService.getDeliveryById(deliveryId);
        if (deliveryEntity == null) {
            throw new BusinessException("La entrega no existe.");
        }

        SupplyDeliveredEntity supplyDeliveredEntity = deliveryEntity.getSupplies().stream()
                .filter(supply -> supply.getSupplyCode().equals(supplyId)).findAny().orElse(null);
        if (supplyDeliveredEntity == null) {
            throw new BusinessException("El insumo no existe.");
        }

        if (observations != null && !observations.isEmpty()) {
            supplyDeliveredEntity.setObservations(observations);
        }

        if (downloaded != null) {
            supplyDeliveredEntity.setDownloaded(downloaded);
            supplyDeliveredEntity.setDownloadedAt(new Date());
            supplyDeliveredEntity.setDownloadedBy(downloadedBy);
        }

        if (reportUrl != null) {
            supplyDeliveredEntity.setDownloadReportUrl(reportUrl);
        }

        List<SupplyDeliveredEntity> suppliesEntities = deliveryEntity.getSupplies().stream()
                .filter(supply -> !supply.getId().equals(supplyId)).collect(Collectors.toList());
        suppliesEntities.add(supplyDeliveredEntity);

        deliveryEntity.setSupplies(suppliesEntities);

        deliveryEntity = deliveryService.updateDelivery(deliveryEntity);

        return this.transformEntityToDto(deliveryEntity);
    }

    public DeliveryDto disableDelivery(Long deliveryId) throws BusinessException {

        DeliveryEntity deliveryEntity = deliveryService.getDeliveryById(deliveryId);
        if (deliveryEntity == null) {
            throw new BusinessException("La entrega no existe.");
        }

        deliveryEntity.setIsActive(false);
        deliveryEntity = deliveryService.updateDelivery(deliveryEntity);

        return this.transformEntityToDto(deliveryEntity);
    }

    public DeliveryDto updateDelivery(Long deliveryId, String urlReport) throws BusinessException {

        DeliveryEntity deliveryEntity = deliveryService.getDeliveryById(deliveryId);
        if (deliveryEntity == null) {
            throw new BusinessException("La entrega no existe.");
        }

        if (urlReport != null) {
            deliveryEntity.setDownloadReportUrl(urlReport);
        }

        deliveryEntity = deliveryService.updateDelivery(deliveryEntity);

        return this.transformEntityToDto(deliveryEntity);
    }

    public DeliveryDto getDeliveryById(Long deliveryId) throws BusinessException {

        DeliveryEntity deliveryEntity = deliveryService.getDeliveryById(deliveryId);
        if (deliveryEntity == null) {
            throw new BusinessException("La entrega no existe.");
        }

        return this.transformEntityToDto(deliveryEntity);
    }

    public List<DeliveryDto> getDeliveriesByManager(Long managerCode) throws BusinessException {

        List<DeliveryDto> deliveriesDto = new ArrayList<>();

        List<DeliveryEntity> deliveriesEntity = deliveryService.getDeliveriesByManager(managerCode);

        for (DeliveryEntity deliveryEntity : deliveriesEntity) {
            DeliveryDto deliveryDto = this.transformEntityToDto(deliveryEntity);
            deliveriesDto.add(deliveryDto);
        }

        return deliveriesDto;
    }

    private DeliveryDto transformEntityToDto(DeliveryEntity deliveryEntity) {

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(deliveryEntity.getId());
        deliveryDto.setCreatedAt(deliveryEntity.getCreatedAt());
        deliveryDto.setManagerCode(deliveryEntity.getManagerCode());
        deliveryDto.setMunicipalityCode(deliveryEntity.getMunicipalityCode());
        deliveryDto.setObservations(deliveryEntity.getObservations());
        deliveryDto.setIsActive(deliveryEntity.getIsActive());
        deliveryDto.setDownloadReportUrl(deliveryEntity.getDownloadReportUrl());

        OperatorEntity operatorEntity = deliveryEntity.getOperator();
        OperatorDto operatorDto = new OperatorDto();
        operatorDto.setId(operatorEntity.getId());
        operatorDto.setIsPublic(operatorEntity.getIsPublic());
        operatorDto.setAlias(operatorEntity.getAlias());
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
            supplyDto.setDownloadedBy(supplyEntity.getDownloadedBy());
            supplyDto.setDownloadReportUrl(supplyEntity.getDownloadReportUrl());

            deliveryDto.getSupplies().add(supplyDto);
        }

        return deliveryDto;
    }

}
