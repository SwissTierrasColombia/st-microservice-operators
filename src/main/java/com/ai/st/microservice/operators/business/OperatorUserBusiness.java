package com.ai.st.microservice.operators.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.operators.dto.OperatorDto;
import com.ai.st.microservice.operators.dto.OperatorStateDto;
import com.ai.st.microservice.operators.entities.OperatorEntity;
import com.ai.st.microservice.operators.entities.OperatorUserEntity;
import com.ai.st.microservice.operators.exceptions.BusinessException;
import com.ai.st.microservice.operators.services.IOperatorUserService;

@Component
public class OperatorUserBusiness {

	@Autowired
	private IOperatorUserService operatorUserService;

	public OperatorDto getOperatorByUserCode(Long userCode) throws BusinessException {

		OperatorDto operatorDto = null;

		List<OperatorUserEntity> operatorUsers = operatorUserService.getOperatorUsersByUserCode(userCode);

		for (OperatorUserEntity operatorUserEntity : operatorUsers) {

			OperatorEntity operatorEntity = operatorUserEntity.getOperator();

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
