package com.ai.st.microservice.operators.controllers.v1;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.st.microservice.operators.business.OperatorBusiness;
import com.ai.st.microservice.operators.dto.OperatorDto;
import com.ai.st.microservice.operators.exceptions.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Operators", description = "Operators", tags = { "Operators" })
@RestController
@RequestMapping("api/operators/v1/operators")
public class OperatorV1Controller {

	private final Logger log = LoggerFactory.getLogger(OperatorV1Controller.class);

	@Autowired
	private OperatorBusiness operatorBusiness;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get operators")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get operators", response = OperatorDto.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<List<OperatorDto>> getOperators(
			@RequestParam(required = false, name = "state") Long operatorStateId) {

		HttpStatus httpStatus = null;
		List<OperatorDto> listOperators = new ArrayList<OperatorDto>();

		try {

			listOperators = operatorBusiness.getOperators(operatorStateId);

			httpStatus = HttpStatus.OK;
		} catch (BusinessException e) {
			listOperators = null;
			log.error("Error OperatorV1Controller@getOperators#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			listOperators = null;
			log.error("Error OperatorV1Controller@getOperators#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(listOperators, httpStatus);
	}

	@RequestMapping(value = "/{operatorId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get operator by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Get operator by id", response = OperatorDto.class),
			@ApiResponse(code = 404, message = "Operator not found", response = String.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<OperatorDto> getOperatorById(@PathVariable Long operatorId) {

		HttpStatus httpStatus = null;
		OperatorDto operatorDto = null;

		try {

			operatorDto = operatorBusiness.getOperatorById(operatorId);
			httpStatus = (operatorDto instanceof OperatorDto) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		} catch (BusinessException e) {
			log.error("Error OperatorV1Controller@getOperatorById#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			log.error("Error OperatorV1Controller@getOperatorById#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(operatorDto, httpStatus);
	}

}
