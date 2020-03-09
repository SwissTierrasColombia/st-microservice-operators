package com.ai.st.microservice.operators;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.operators.business.OperatorStateBusiness;
import com.ai.st.microservice.operators.entities.OperatorEntity;
import com.ai.st.microservice.operators.entities.OperatorStateEntity;
import com.ai.st.microservice.operators.entities.OperatorUserEntity;
import com.ai.st.microservice.operators.services.IOperatorService;
import com.ai.st.microservice.operators.services.IOperatorStateService;
import com.ai.st.microservice.operators.services.IOperatorUserService;

@Component
public class StMicroserviceOperatorsApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(StMicroserviceOperatorsApplicationStartup.class);

	@Autowired
	private IOperatorStateService operatorStateService;

	@Autowired
	private IOperatorService operatorService;

	@Autowired
	private IOperatorUserService operatorUserService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("ST - Loading Domains ... ");
		this.initOperatorsStates();
		this.initOperators();
	}

	public void initOperatorsStates() {

		Long countStates = operatorStateService.getCount();
		if (countStates == 0) {

			try {

				OperatorStateEntity stateActive = new OperatorStateEntity();
				stateActive.setId(OperatorStateBusiness.OPERATOR_STATE_ACTIVE);
				stateActive.setName("ACTIVO");
				operatorStateService.createOperatorState(stateActive);

				OperatorStateEntity stateInactive = new OperatorStateEntity();
				stateInactive.setId(OperatorStateBusiness.OPERATOR_STATE_INACTIVE);
				stateInactive.setName("INACTIVO");
				operatorStateService.createOperatorState(stateInactive);

				log.info("The domains 'operators states' have been loaded!");
			} catch (Exception e) {
				log.error("Failed to load 'operators states' domains");
			}

		}

	}

	public void initOperators() {

		Long countOperators = operatorService.getCount();
		if (countOperators == 0) {

			try {

				OperatorStateEntity stateActive = operatorStateService
						.getOperatorById(OperatorStateBusiness.OPERATOR_STATE_ACTIVE);

				OperatorEntity operatorEntity = new OperatorEntity();
				operatorEntity.setCreatedAt(new Date());
				operatorEntity.setIsPublic(false);
				operatorEntity.setName("TOPOGRÁFICA DE COLOMBIA");
				operatorEntity.setTaxIdentificationNumber("999-5");
				operatorEntity.setOperatorState(stateActive);
				operatorService.createOperator(operatorEntity);

				OperatorUserEntity operatorUser1 = new OperatorUserEntity();
				operatorUser1.setCreatedAt(new Date());
				operatorUser1.setUserCode((long) 8);
				operatorUser1.setOperator(operatorEntity);
				operatorUserService.createUserOperator(operatorUser1);

				OperatorEntity operator1Entity = new OperatorEntity();
				operator1Entity.setCreatedAt(new Date());
				operator1Entity.setIsPublic(false);
				operator1Entity.setName("OPERADOR 1");
				operator1Entity.setTaxIdentificationNumber("999-1");
				operator1Entity.setOperatorState(stateActive);
				operatorService.createOperator(operator1Entity);

				OperatorEntity operator2Entity = new OperatorEntity();
				operator2Entity.setCreatedAt(new Date());
				operator2Entity.setIsPublic(false);
				operator2Entity.setName("OPERADOR 2");
				operator2Entity.setTaxIdentificationNumber("999-2");
				operator2Entity.setOperatorState(stateActive);
				operatorService.createOperator(operator2Entity);

				OperatorEntity operator3Entity = new OperatorEntity();
				operator3Entity.setCreatedAt(new Date());
				operator3Entity.setIsPublic(false);
				operator3Entity.setName("OPERADOR 3");
				operator3Entity.setTaxIdentificationNumber("999-3");
				operator3Entity.setOperatorState(stateActive);
				operatorService.createOperator(operator3Entity);

				OperatorEntity operator4Entity = new OperatorEntity();
				operator4Entity.setCreatedAt(new Date());
				operator4Entity.setIsPublic(false);
				operator4Entity.setName("OPERADOR 4");
				operator4Entity.setTaxIdentificationNumber("999-4");
				operator4Entity.setOperatorState(stateActive);
				operatorService.createOperator(operator4Entity);

				OperatorEntity operator5Entity = new OperatorEntity();
				operator5Entity.setCreatedAt(new Date());
				operator5Entity.setIsPublic(false);
				operator5Entity.setName("OPERADOR 5");
				operator5Entity.setTaxIdentificationNumber("999-5");
				operator5Entity.setOperatorState(stateActive);
				operatorService.createOperator(operator5Entity);

				log.info("The domains 'operators' have been loaded!");
			} catch (Exception e) {
				log.error("Failed to load 'operators' domains");
			}

		}

	}

}
