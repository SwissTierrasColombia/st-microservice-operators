package com.ai.st.microservice.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.operators.business.OperatorStateBusiness;
import com.ai.st.microservice.operators.entities.OperatorStateEntity;
import com.ai.st.microservice.operators.services.IOperatorStateService;

@Component
public class StMicroserviceOperatorsApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(StMicroserviceOperatorsApplicationStartup.class);

	@Autowired
	private IOperatorStateService operatorStateService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("ST - Loading Domains ... ");
		this.initOperatorsStates();
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

}
