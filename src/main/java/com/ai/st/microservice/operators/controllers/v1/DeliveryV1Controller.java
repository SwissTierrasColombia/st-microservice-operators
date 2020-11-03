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
import com.ai.st.microservice.operators.dto.UpdateDeliveryDto;
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

			responseDto = deliveryBusiness.updateSupplyDelivered(deliveryId, supplyId, updateSupply.getObservations(),
					updateSupply.getDownloaded(), updateSupply.getDownloadedBy(), updateSupply.getReportUrl());
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

	@RequestMapping(value = "{deliveryId}/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Disable delivery")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Delivery disabled", response = DeliveryDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> disabledDelivery(@PathVariable Long deliveryId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = deliveryBusiness.disableDelivery(deliveryId);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			responseDto = new ErrorDto(e.getMessage(), 3);
			log.error("Error DeliveryV1Controller@disabledDelivery#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			responseDto = new ErrorDto(e.getMessage(), 4);
			log.error("Error DeliveryV1Controller@disabledDelivery#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "{deliveryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get delivery by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Delivery obtained", response = DeliveryDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> getDeliveryById(@PathVariable Long deliveryId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = deliveryBusiness.getDeliveryById(deliveryId);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			responseDto = new ErrorDto(e.getMessage(), 3);
			log.error("Error DeliveryV1Controller@getDeliveryById#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			responseDto = new ErrorDto(e.getMessage(), 4);
			log.error("Error DeliveryV1Controller@getDeliveryById#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "{deliveryId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update delivered")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Delivery updated", response = DeliveryDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> updateDelivery(@PathVariable Long deliveryId,
			@RequestBody UpdateDeliveryDto updateDelivery) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = deliveryBusiness.updateDelivery(deliveryId, updateDelivery.getReportUrl());
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			responseDto = new ErrorDto(e.getMessage(), 3);
			log.error("Error DeliveryV1Controller@updateDelivery#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			responseDto = new ErrorDto(e.getMessage(), 4);
			log.error("Error DeliveryV1Controller@updateDelivery#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "/managers/{managerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get deliveries by manager")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get deliveries by manager", response = DeliveryDto.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> getDeliveriesByManager(@PathVariable Long managerId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = deliveryBusiness.getDeliveriesByManager(managerId);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			responseDto = new ErrorDto(e.getMessage(), 3);
			log.error("Error DeliveryV1Controller@getDeliveriesByManager#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			responseDto = new ErrorDto(e.getMessage(), 4);
			log.error("Error DeliveryV1Controller@getDeliveriesByManager#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

}
