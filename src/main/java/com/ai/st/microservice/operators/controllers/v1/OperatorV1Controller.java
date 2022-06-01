package com.ai.st.microservice.operators.controllers.v1;

import java.util.ArrayList;
import java.util.List;

import com.ai.st.microservice.common.dto.general.BasicResponseDto;
import com.ai.st.microservice.operators.services.tracing.SCMTracing;
import com.ai.st.microservice.operators.services.tracing.TracingKeyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ai.st.microservice.operators.business.DeliveryBusiness;
import com.ai.st.microservice.operators.business.OperatorBusiness;
import com.ai.st.microservice.operators.dto.CreateDeliveryDto;
import com.ai.st.microservice.operators.dto.CreateDeliverySupplyDto;
import com.ai.st.microservice.operators.dto.CreateOperatorDto;
import com.ai.st.microservice.operators.dto.DeliveryDto;
import com.ai.st.microservice.operators.dto.OperatorDto;
import com.ai.st.microservice.operators.dto.UpdateOperatorDto;
import com.ai.st.microservice.operators.exceptions.BusinessException;
import com.ai.st.microservice.operators.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Operators", tags = { "Operators" })
@RestController
@RequestMapping("api/operators/v1/operators")
public class OperatorV1Controller {

    private final Logger log = LoggerFactory.getLogger(OperatorV1Controller.class);

    private final OperatorBusiness operatorBusiness;
    private final DeliveryBusiness deliveryBusiness;

    public OperatorV1Controller(OperatorBusiness operatorBusiness, DeliveryBusiness deliveryBusiness) {
        this.operatorBusiness = operatorBusiness;
        this.deliveryBusiness = deliveryBusiness;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get operators")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get operators", response = OperatorDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<List<OperatorDto>> getOperators(
            @RequestParam(required = false, name = "state") Long operatorStateId) {

        HttpStatus httpStatus;
        List<OperatorDto> listOperators;

        try {

            SCMTracing.setTransactionName("getOperators");

            listOperators = operatorBusiness.getOperators(operatorStateId);

            httpStatus = HttpStatus.OK;
        } catch (BusinessException e) {
            listOperators = null;
            log.error("Error OperatorV1Controller@getOperators#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            listOperators = null;
            log.error("Error OperatorV1Controller@getOperators#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(listOperators, httpStatus);
    }

    @GetMapping(value = "/{operatorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get operator by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get operator by id", response = OperatorDto.class),
            @ApiResponse(code = 404, message = "Operator not found", response = String.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<OperatorDto> getOperatorById(@PathVariable Long operatorId) {

        HttpStatus httpStatus;
        OperatorDto operatorDto = null;

        try {

            SCMTracing.setTransactionName("getOperatorById");

            operatorDto = operatorBusiness.getOperatorById(operatorId);
            httpStatus = (operatorDto != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        } catch (BusinessException e) {
            log.error("Error OperatorV1Controller@getOperatorById#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error OperatorV1Controller@getOperatorById#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(operatorDto, httpStatus);
    }

    @PostMapping(value = "/{operatorId}/deliveries", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add delivery to operator")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Supplies delivered", response = DeliveryDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> addDeliveryToOperator(@PathVariable Long operatorId,
            @RequestBody CreateDeliveryDto createDeliveryDto) {

        HttpStatus httpStatus;
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("addDeliveryToOperator");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, createDeliveryDto.toString());

            // validation manager
            Long managerCode = createDeliveryDto.getManagerCode();
            if (managerCode == null || managerCode <= 0) {
                throw new InputValidationException("El gestor es requerido.");
            }

            // validation municipality
            String municipalityCode = createDeliveryDto.getMunicipalityCode();
            if (municipalityCode == null || municipalityCode.isEmpty()) {
                throw new InputValidationException("El municipio requerido.");
            }

            // validation observations
            String observations = createDeliveryDto.getObservations();
            if (observations == null || observations.isEmpty()) {
                throw new InputValidationException("Las observaciones son requeridas.");
            }

            // validation supplies
            List<CreateDeliverySupplyDto> supplies = createDeliveryDto.getSupplies();
            if (supplies == null || supplies.size() == 0) {
                throw new InputValidationException("La entrega no contiene ningún insumo.");
            } else {
                for (CreateDeliverySupplyDto supplyDto : supplies) {
                    if (supplyDto.getSupplyCode() == null || supplyDto.getSupplyCode() <= 0) {
                        throw new InputValidationException("La entrega contiene un insumo inválido.");
                    }
                }
            }

            responseDto = deliveryBusiness.createDelivery(operatorId, managerCode, municipalityCode, observations,
                    supplies);
            httpStatus = HttpStatus.CREATED;

        } catch (InputValidationException e) {
            log.error("Error OperatorV1Controller@addDeliveryToOperator#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error OperatorV1Controller@addDeliveryToOperator#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error OperatorV1Controller@addDeliveryToOperator#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "/{operatorId}/deliveries", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get deliveries by operator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get deliveries", response = DeliveryDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getDeliveriesByOperator(@PathVariable Long operatorId,
            @RequestParam(name = "municipality", required = false) String municipalityCode,
            @RequestParam(name = "active", required = false) Boolean active) {

        HttpStatus httpStatus;
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("getDeliveriesByOperator");

            responseDto = deliveryBusiness.getDeliveriesByOperator(operatorId, municipalityCode, active);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error OperatorV1Controller@getDeliveriesByOperator#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error OperatorV1Controller@getDeliveriesByOperator#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "/{operatorId}/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get users by operator")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get users by operator", response = OperatorDto.class),
            @ApiResponse(code = 404, message = "Operator not found", response = String.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getUsersByOperator(@PathVariable Long operatorId) {

        HttpStatus httpStatus;
        Object operatorDto = null;

        try {

            SCMTracing.setTransactionName("getUsersByOperator");

            operatorDto = operatorBusiness.getUsersByOperator(operatorId);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error OperatorV1Controller@getUsersByOperator#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error OperatorV1Controller@getUsersByOperator#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(operatorDto, httpStatus);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create Operator")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Create Operator", response = OperatorDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> createOperator(@RequestBody CreateOperatorDto requestCreateOperator) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("createOperator");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, requestCreateOperator.toString());

            // validation operator name
            String operatorName = requestCreateOperator.getName();
            if (operatorName.isEmpty()) {
                throw new InputValidationException("El nombre del operador es requerido");
            }

            // validation operator tax id
            String taxIdentification = requestCreateOperator.getTaxIdentificationNumber();
            if (taxIdentification.isEmpty()) {
                throw new InputValidationException("El identificador de impuesto es requerido.");
            }

            // validation operator is public
            Boolean isPublic = requestCreateOperator.getIsPublic();
            if (isPublic == null) {
                throw new InputValidationException("La bandera de público es requerida.");
            }

            responseDto = operatorBusiness.addOperator(operatorName, taxIdentification, isPublic,
                    requestCreateOperator.getAlias());
            httpStatus = HttpStatus.CREATED;

        } catch (InputValidationException e) {
            log.error("Error OperatorV1Controller@createOperator#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error OperatorV1Controller@createOperator#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error OperatorV1Controller@createOperator#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PutMapping("/{id}/enable")
    @ApiOperation(value = "Activate operator")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operator activated", response = OperatorDto.class),
            @ApiResponse(code = 404, message = "Manager not found"),
            @ApiResponse(code = 500, message = "Error Server") })
    public ResponseEntity<OperatorDto> activateOperator(@PathVariable Long id) {

        HttpStatus httpStatus;
        OperatorDto operatorDtoResponse = null;

        try {

            SCMTracing.setTransactionName("activateOperator");

            operatorDtoResponse = operatorBusiness.activateOperator(id);
            httpStatus = HttpStatus.OK;
        } catch (BusinessException e) {
            log.error("Error OperatorV1Controller@activateOperator#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error OperatorV1Controller@activateOperator#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(operatorDtoResponse, httpStatus);
    }

    @PutMapping("/{id}/disable")
    @ApiOperation(value = "Disable operator")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operator disabled", response = OperatorDto.class),
            @ApiResponse(code = 404, message = "Operator not found"),
            @ApiResponse(code = 500, message = "Error Server") })
    public ResponseEntity<OperatorDto> deactivateOperator(@PathVariable Long id) {

        HttpStatus httpStatus;
        OperatorDto operatorDtoResponse = null;

        try {

            SCMTracing.setTransactionName("deactivateOperator");

            operatorDtoResponse = operatorBusiness.deactivateOperator(id);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error OperatorV1Controller@deactivateOperator#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error OperatorV1Controller@deactivateOperator#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(operatorDtoResponse, httpStatus);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Operator")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Update Operator", response = OperatorDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> updateOperator(@RequestBody UpdateOperatorDto requestUpdateOperator) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("updateOperator");

            // validation operator id
            Long operatorId = requestUpdateOperator.getId();
            if (operatorId <= 0) {
                throw new InputValidationException("El id del operador es requerido");
            }

            // validation operator name
            String operatorName = requestUpdateOperator.getName();
            if (operatorName.isEmpty()) {
                throw new InputValidationException("El nombre del operador es requerido");
            }

            // validation operator tax id
            String taxIdentification = requestUpdateOperator.getTaxIdentificationNumber();
            if (taxIdentification.isEmpty()) {
                throw new InputValidationException("El identificador de impuesto es requerido.");
            }

            // validation operator is public
            Boolean isPublic = requestUpdateOperator.getIsPublic();
            if (isPublic == null) {
                throw new InputValidationException("La bandera de público es requerida.");
            }

            responseDto = operatorBusiness.updateManager(operatorId, operatorName, taxIdentification, isPublic,
                    requestUpdateOperator.getAlias());
            httpStatus = HttpStatus.OK;

        } catch (InputValidationException e) {
            log.error("Error OperatorV1Controller@updateOperator#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ManagerV1Controller@updateOperator#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error OperatorV1Controller@updateOperator#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
