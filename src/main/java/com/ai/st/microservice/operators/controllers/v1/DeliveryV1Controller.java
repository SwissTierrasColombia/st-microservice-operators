package com.ai.st.microservice.operators.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.st.microservice.operators.business.DeliveryBusiness;
import com.ai.st.microservice.operators.dto.DeliveryDto;
import com.ai.st.microservice.operators.dto.ErrorDto;
import com.ai.st.microservice.operators.dto.UpdateDeliveredSupplyDto;
import com.ai.st.microservice.operators.exceptions.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage deliveries", description = "Manage deliveries", tags = { "Deliveries" })
@RestController
@RequestMapping("api/operators/v1/deliveries")
public class DeliveryV1Controller {

	private final Logger log = LoggerFactory.getLogger(DeliveryV1Controller.class);

	@Autowired
	private DeliveryBusiness deliveryBusiness;

	@RequestMapping(value = "{deliveryId}/supplies/{supplyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update delivered supply")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Supply updated", response = DeliveryDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> updateDeliveredSupply(@PathVariable Long deliveryId, @PathVariable Long supplyId,
			@RequestBody UpdateDeliveredSupplyDto updateSupply) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			deliveryBusiness.updateSupplyDelivered(deliveryId, supplyId, updateSupply.getObservations(),
					updateSupply.getDownloaded());
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			responseDto = new ErrorDto(e.getMessage(), 3);
			log.error("Error DeliveryV1Controller@updateDeliveredSupply#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			responseDto = new ErrorDto(e.getMessage(), 4);
			log.error("Error DeliveryV1Controller@updateDeliveredSupply#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

}
