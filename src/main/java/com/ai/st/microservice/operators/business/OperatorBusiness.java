package com.ai.st.microservice.operators.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.operators.dto.OperatorDto;
import com.ai.st.microservice.operators.dto.OperatorStateDto;
import com.ai.st.microservice.operators.entities.OperatorEntity;
import com.ai.st.microservice.operators.exceptions.BusinessException;
import com.ai.st.microservice.operators.services.IOperatorService;

@Component
public class OperatorBusiness {

	@Autowired
	private IOperatorService operatorService;

	public List<OperatorDto> getOperators(Long operatorStateId) throws BusinessException {

		List<OperatorDto> listOperatorsDto = new ArrayList<OperatorDto>();

		List<OperatorEntity> listOperatorEntity = new ArrayList<OperatorEntity>();

		if (operatorStateId != null) {
			listOperatorEntity = operatorService.getOperatorsByStateId(operatorStateId);
		} else {
			listOperatorEntity = operatorService.getAllOperators();
		}

		for (OperatorEntity operatorEntity : listOperatorEntity) {

			OperatorDto operatorDto = new OperatorDto();
			operatorDto.setId(operatorEntity.getId());
			operatorDto.setCreatedAt(operatorEntity.getCreatedAt());
			operatorDto.setIsPublic(operatorEntity.getIsPublic());
			operatorDto.setName(operatorEntity.getName());
			operatorDto.setTaxIdentificationNumber(operatorEntity.getTaxIdentificationNumber());
			operatorDto.setOperatorState(new OperatorStateDto(operatorEntity.getOperatorState().getId(),
					operatorEntity.getOperatorState().getName()));

			listOperatorsDto.add(operatorDto);
		}

		return listOperatorsDto;
	}

	public OperatorDto getOperatorById(Long operatorId) throws BusinessException {

		OperatorDto operatorDto = null;

		OperatorEntity operatorEntity = operatorService.getOperatorById(operatorId);
		if (operatorEntity instanceof OperatorEntity) {
			operatorDto = new OperatorDto();
			operatorDto.setId(operatorEntity.getId());
			operatorDto.setCreatedAt(operatorEntity.getCreatedAt());
			operatorDto.setIsPublic(operatorEntity.getIsPublic());
			operatorDto.setName(operatorEntity.getName());
			operatorDto.setTaxIdentificationNumber(operatorEntity.getTaxIdentificationNumber());
			operatorDto.setOperatorState(new OperatorStateDto(operatorEntity.getOperatorState().getId(),
					operatorEntity.getOperatorState().getName()));
		}

		return operatorDto;
	}

}