package com.ai.st.microservice.operators.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.operators.dto.OperatorDto;
import com.ai.st.microservice.operators.dto.OperatorStateDto;
import com.ai.st.microservice.operators.dto.OperatorUserDto;
import com.ai.st.microservice.operators.entities.OperatorEntity;
import com.ai.st.microservice.operators.entities.OperatorUserEntity;
import com.ai.st.microservice.operators.exceptions.BusinessException;
import com.ai.st.microservice.operators.services.IOperatorService;
import com.ai.st.microservice.operators.services.IOperatorUserService;

@Component
public class OperatorBusiness {

	@Autowired
	private IOperatorService operatorService;

	@Autowired
	private IOperatorUserService operatorUserService;

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

	public OperatorDto addUserToOperator(Long userCode, Long operatorId) throws BusinessException {

		// verify if the operator does exists
		OperatorEntity operatorEntity = operatorService.getOperatorById(operatorId);
		if (!(operatorEntity instanceof OperatorEntity)) {
			throw new BusinessException("El operador no existe.");
		}

		OperatorUserEntity existsUser = operatorUserService.getOperatorUserByOperatorAndUserCode(operatorEntity,
				userCode);
		if (existsUser instanceof OperatorUserEntity) {
			throw new BusinessException("El usuario ya ha sido asignado al operador.");
		}

		OperatorUserEntity operatorUserEntity = new OperatorUserEntity();
		operatorUserEntity.setCreatedAt(new Date());
		operatorUserEntity.setOperator(operatorEntity);
		operatorUserEntity.setUserCode(userCode);

		operatorUserEntity = operatorUserService.createUserOperator(operatorUserEntity);

		operatorEntity = operatorUserEntity.getOperator();

		OperatorDto operatorDto = new OperatorDto();
		operatorDto.setId(operatorEntity.getId());
		operatorDto.setCreatedAt(operatorEntity.getCreatedAt());
		operatorDto.setIsPublic(operatorEntity.getIsPublic());
		operatorDto.setName(operatorEntity.getName());
		operatorDto.setTaxIdentificationNumber(operatorEntity.getTaxIdentificationNumber());
		operatorDto.setOperatorState(new OperatorStateDto(operatorEntity.getOperatorState().getId(),
				operatorEntity.getOperatorState().getName()));

		return operatorDto;
	}

	public List<OperatorUserDto> getUsersByOperator(Long operatorId) throws BusinessException {

		List<OperatorUserDto> users = new ArrayList<>();

		// verify if the operator does exists
		OperatorEntity operatorEntity = operatorService.getOperatorById(operatorId);
		if (!(operatorEntity instanceof OperatorEntity)) {
			throw new BusinessException("El operador no existe.");
		}

		List<OperatorUserEntity> usersEntity = operatorEntity.getUsers();
		for (OperatorUserEntity userEntity : usersEntity) {
			users.add(new OperatorUserDto(userEntity.getUserCode()));
		}

		return users;
	}

}
