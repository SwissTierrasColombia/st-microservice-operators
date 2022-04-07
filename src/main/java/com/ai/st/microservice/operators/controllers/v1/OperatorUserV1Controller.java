package com.ai.st.microservice.operators.controllers.v1;

import com.ai.st.microservice.common.dto.general.BasicResponseDto;
import com.ai.st.microservice.operators.services.tracing.SCMTracing;
import com.ai.st.microservice.operators.services.tracing.TracingKeyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ai.st.microservice.operators.business.OperatorBusiness;
import com.ai.st.microservice.operators.business.OperatorUserBusiness;
import com.ai.st.microservice.operators.dto.AddUserToOperatorDto;
import com.ai.st.microservice.operators.dto.OperatorDto;
import com.ai.st.microservice.operators.exceptions.BusinessException;
import com.ai.st.microservice.operators.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Operators Users", tags = { "Operators Users" })
@RestController
@RequestMapping("api/operators/v1/users")
public class OperatorUserV1Controller {

    private final Logger log = LoggerFactory.getLogger(OperatorUserV1Controller.class);

    private final OperatorUserBusiness operatorUserBusiness;
    private final OperatorBusiness operatorBusiness;

    public OperatorUserV1Controller(OperatorUserBusiness operatorUserBusiness, OperatorBusiness operatorBusiness) {
        this.operatorUserBusiness = operatorUserBusiness;
        this.operatorBusiness = operatorBusiness;
    }

    @GetMapping(value = "/{userCode}/operators", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get operator by user")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get operator by user", response = OperatorDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getOperatorByUser(@PathVariable Long userCode) {

        HttpStatus httpStatus;
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("getOperatorByUser");

            responseDto = operatorUserBusiness.getOperatorByUserCode(userCode);
            httpStatus = (responseDto != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        } catch (BusinessException e) {
            log.error("Error OperatorUserV1Controller@getOperatorByUser#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error OperatorUserV1Controller@getOperatorByUser#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add user to operator")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Add user to operator", response = OperatorDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> addUserToOperator(@RequestBody AddUserToOperatorDto requestAddUser) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("addUserToOperator");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, requestAddUser.toString());

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
            log.error("Error ProviderUserV1Controller@addUserToOperator#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ProviderUserV1Controller@addUserToOperator#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderUserV1Controller@addUserToOperator#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
