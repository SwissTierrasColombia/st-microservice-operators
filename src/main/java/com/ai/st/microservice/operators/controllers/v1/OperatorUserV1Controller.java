package com.ai.st.microservice.operators.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.st.microservice.operators.business.OperatorUserBusiness;
import com.ai.st.microservice.operators.dto.OperatorDto;
import com.ai.st.microservice.operators.exceptions.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Operators Users", description = "Manage Operators Users", tags = { "Operators Users" })
@RestController
@RequestMapping("api/operators/v1/users")
public class OperatorUserV1Controller {

	private final Logger log = LoggerFactory.getLogger(OperatorUserV1Controller.class);

	@Autowired
	private OperatorUserBusiness operatorUserBusiness;

	@RequestMapping(value = "/{userCode}/operators", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get operator by user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Get operator by user", response = OperatorDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> getOperatorsByUser(@PathVariable Long userCode) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = operatorUserBusiness.getOperatorByUserCode(userCode);
			httpStatus = (responseDto instanceof OperatorDto) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		} catch (BusinessException e) {
			log.error("Error ManagerUserV1Controller@getManagersByUser#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			log.error("Error ManagerUserV1Controller@getManagersByUser#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

}
