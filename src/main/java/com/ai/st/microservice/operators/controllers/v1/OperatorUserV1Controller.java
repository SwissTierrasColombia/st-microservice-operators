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

import com.ai.st.microservice.operators.business.OperatorBusiness;
import com.ai.st.microservice.operators.business.OperatorUserBusiness;
import com.ai.st.microservice.operators.dto.AddUserToOperatorDto;
import com.ai.st.microservice.operators.dto.ErrorDto;
import com.ai.st.microservice.operators.dto.OperatorDto;
import com.ai.st.microservice.operators.exceptions.BusinessException;
import com.ai.st.microservice.operators.exceptions.InputValidationException;

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

	@Autowired
	private OperatorBusiness operatorBusiness;

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
			httpStatus = (responseDto != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		} catch (BusinessException e) {
			log.error("Error OperatorUserV1Controller@getOperatorsByUser#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			log.error("Error OperatorUserV1Controller@getOperatorsByUser#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add user to operator")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Add user to operator", response = OperatorDto.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> addUserToOperator(@RequestBody AddUserToOperatorDto requestAddUser) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			// validation user code
			Long userCode = requestAddUser.getUserCode();
			if (userCode == null || userCode <= 0) {
				throw new InputValidationException("El código de usuario es inválido.");
			}

			// validation operator id
			Long operatorId = requestAddUser.getOperatorId();
			if (operatorId == null || operatorId <= 0) {
				throw new InputValidationException("El operador es inválido.");
			}

			responseDto = operatorBusiness.addUserToOperator(userCode, operatorId);
			httpStatus = HttpStatus.CREATED;

		} catch (InputValidationException e) {
			log.error("Error ProviderUserV1Controller@addUserToProvider#Validation ---> " + e.getMessage());
			httpStatus = HttpStatus.BAD_REQUEST;
			responseDto = new ErrorDto(e.getMessage(), 1);
		} catch (BusinessException e) {
			log.error("Error ProviderUserV1Controller@addUserToProvider#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderUserV1Controller@addUserToProvider#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

}
