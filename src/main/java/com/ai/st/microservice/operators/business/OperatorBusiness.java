package com.ai.st.microservice.operators.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ai.st.microservice.operators.services.tracing.SCMTracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.operators.dto.OperatorDto;
import com.ai.st.microservice.operators.dto.OperatorStateDto;
import com.ai.st.microservice.operators.dto.OperatorUserDto;
import com.ai.st.microservice.operators.entities.OperatorEntity;
import com.ai.st.microservice.operators.entities.OperatorStateEntity;
import com.ai.st.microservice.operators.entities.OperatorUserEntity;
import com.ai.st.microservice.operators.exceptions.BusinessException;
import com.ai.st.microservice.operators.models.services.IOperatorService;
import com.ai.st.microservice.operators.models.services.IOperatorStateService;
import com.ai.st.microservice.operators.models.services.IOperatorUserService;

@Component
public class OperatorBusiness {

    private final Logger log = LoggerFactory.getLogger(OperatorBusiness.class);

    @Autowired
    private IOperatorService operatorService;

    @Autowired
    private IOperatorUserService operatorUserService;

    @Autowired
    private IOperatorStateService operatorStateService;

    public List<OperatorDto> getOperators(Long operatorStateId) throws BusinessException {

        List<OperatorDto> listOperatorsDto = new ArrayList<>();

        List<OperatorEntity> listOperatorEntity;

        if (operatorStateId != null) {
            listOperatorEntity = operatorService.getOperatorsByStateId(operatorStateId);
        } else {
            listOperatorEntity = operatorService.getAllOperators();
        }

        for (OperatorEntity operatorEntity : listOperatorEntity) {
            OperatorDto operatorDto = transformEntityToDto(operatorEntity);
            listOperatorsDto.add(operatorDto);
        }

        return listOperatorsDto;
    }

    public OperatorDto getOperatorById(Long operatorId) throws BusinessException {

        OperatorDto operatorDto = null;

        OperatorEntity operatorEntity = operatorService.getOperatorById(operatorId);
        if (operatorEntity != null) {
            operatorDto = transformEntityToDto(operatorEntity);
        }

        return operatorDto;
    }

    public OperatorDto addUserToOperator(Long userCode, Long operatorId) throws BusinessException {

        // verify if the operator does exist
        OperatorEntity operatorEntity = operatorService.getOperatorById(operatorId);
        if (operatorEntity == null) {
            throw new BusinessException("El operador no existe.");
        }

        OperatorUserEntity existsUser = operatorUserService.getOperatorUserByOperatorAndUserCode(operatorEntity,
                userCode);
        if (existsUser != null) {
            throw new BusinessException("El usuario ya ha sido asignado al operador.");
        }

        OperatorUserEntity operatorUserEntity = new OperatorUserEntity();
        operatorUserEntity.setCreatedAt(new Date());
        operatorUserEntity.setOperator(operatorEntity);
        operatorUserEntity.setUserCode(userCode);

        operatorUserEntity = operatorUserService.createUserOperator(operatorUserEntity);

        operatorEntity = operatorUserEntity.getOperator();

        return transformEntityToDto(operatorEntity);
    }

    public List<OperatorUserDto> getUsersByOperator(Long operatorId) throws BusinessException {

        List<OperatorUserDto> users = new ArrayList<>();

        // verify if the operator does exist
        OperatorEntity operatorEntity = operatorService.getOperatorById(operatorId);
        if (operatorEntity == null) {
            throw new BusinessException("El operador no existe.");
        }

        List<OperatorUserEntity> usersEntity = operatorEntity.getUsers();
        for (OperatorUserEntity userEntity : usersEntity) {
            users.add(new OperatorUserDto(userEntity.getUserCode()));
        }

        return users;
    }

    public Object addOperator(String operatorName, String taxIdentification, Boolean isPublic, String alias)
            throws BusinessException {

        if (operatorName.isEmpty()) {
            throw new BusinessException("El nombre del operador es requerido.");
        }

        if (taxIdentification.isEmpty()) {
            throw new BusinessException("El NIT del operador es requerido.");
        }

        OperatorEntity operatorFound = operatorService.getOperatorByTaxIdentificationNumber(taxIdentification);
        if (operatorFound != null) {
            throw new BusinessException("Ya existe un operador registrado con el mismo NIT");
        }

        OperatorStateEntity operatorState = operatorStateService
                .getOperatorById(OperatorStateBusiness.OPERATOR_STATE_ACTIVE);

        OperatorEntity operatorEntity = new OperatorEntity();

        operatorEntity.setName(operatorName.toUpperCase());
        operatorEntity.setCreatedAt(new Date());
        operatorEntity.setTaxIdentificationNumber(taxIdentification);
        operatorEntity.setIsPublic(isPublic);
        operatorEntity.setOperatorState(operatorState);

        if (alias != null) {
            operatorEntity.setAlias(alias);
        }

        operatorEntity = operatorService.createOperator(operatorEntity);

        return this.transformEntityToDto(operatorEntity);
    }

    protected OperatorDto transformEntityToDto(OperatorEntity operatorEntity) {

        OperatorDto operatorDto = new OperatorDto();
        operatorDto.setId(operatorEntity.getId());
        operatorDto.setAlias(operatorEntity.getAlias());
        operatorDto.setCreatedAt(operatorEntity.getCreatedAt());
        operatorDto.setName(operatorEntity.getName());
        operatorDto.setIsPublic(operatorEntity.getIsPublic());
        operatorDto.setTaxIdentificationNumber(operatorEntity.getTaxIdentificationNumber());

        OperatorStateDto managerStateDto = new OperatorStateDto();
        managerStateDto.setId(operatorEntity.getOperatorState().getId());
        managerStateDto.setName(operatorEntity.getOperatorState().getName());

        operatorDto.setOperatorState(managerStateDto);

        return operatorDto;
    }

    public OperatorDto activateOperator(Long id) throws BusinessException {

        OperatorDto operatorDto;

        // verify manager exists
        OperatorEntity operatorEntity = operatorService.getOperatorById(id);
        if (operatorEntity == null) {
            throw new BusinessException("El operador no existe.");
        }

        // set operator state
        OperatorStateEntity operatorStateEntity = operatorStateService
                .getOperatorById(OperatorStateBusiness.OPERATOR_STATE_ACTIVE);
        if (operatorStateEntity == null) {
            throw new BusinessException("El estado no existe.");
        }

        operatorEntity.setOperatorState(operatorStateEntity);

        try {
            OperatorEntity operatorUpdatedEntity = operatorService.updateOperator(operatorEntity);
            operatorDto = this.transformEntityToDto(operatorUpdatedEntity);
        } catch (Exception e) {
            String messageError = String.format("Error activando el operador %d : %s", id, e.getMessage());
            SCMTracing.sendError(messageError);
            log.error(messageError);
            throw new BusinessException("No se ha podido activar el operador.");
        }

        return operatorDto;

    }

    public OperatorDto deactivateOperator(Long id) throws BusinessException {
        OperatorDto operatorDto;

        // verify manager exists
        OperatorEntity operatorEntity = operatorService.getOperatorById(id);
        if (operatorEntity == null) {
            throw new BusinessException("No se ha encontrado el operador.");
        }

        // set operator state
        OperatorStateEntity operatorStateEntity = operatorStateService
                .getOperatorById(OperatorStateBusiness.OPERATOR_STATE_INACTIVE);
        if (operatorStateEntity == null) {
            throw new BusinessException("El estado del operador no existe.");
        }

        operatorEntity.setOperatorState(operatorStateEntity);

        try {
            OperatorEntity operatorUpdatedEntity = operatorService.updateOperator(operatorEntity);
            operatorDto = this.transformEntityToDto(operatorUpdatedEntity);
        } catch (Exception e) {
            String messageError = String.format("Error desactivando el operador %d : %s", id, e.getMessage());
            SCMTracing.sendError(messageError);
            log.error(messageError);
            throw new BusinessException("No se ha podido desactivar el operador.");
        }

        return operatorDto;
    }

    public Object updateManager(Long operatorId, String operatorName, String taxIdentification, Boolean isPublic,
            String alias) throws BusinessException {
        OperatorDto operatorDto;

        // verify operator exists
        OperatorEntity operatorEntity = operatorService.getOperatorById(operatorId);
        if (operatorEntity == null) {
            throw new BusinessException("No se ha encontrado al operador.");
        }

        operatorEntity.setName(operatorName.toUpperCase());
        operatorEntity.setTaxIdentificationNumber(taxIdentification);
        operatorEntity.setIsPublic(isPublic);

        operatorEntity.setAlias(alias);

        try {
            OperatorEntity operatorUpdatedEntity = operatorService.updateOperator(operatorEntity);
            operatorDto = this.transformEntityToDto(operatorUpdatedEntity);
        } catch (Exception e) {
            String messageError = String.format("Error actualizando el operador %d : %s", operatorId, e.getMessage());
            SCMTracing.sendError(messageError);
            log.error(messageError);
            throw new BusinessException("No se ha podido actualizar el operador.");
        }

        return operatorDto;
    }

}
