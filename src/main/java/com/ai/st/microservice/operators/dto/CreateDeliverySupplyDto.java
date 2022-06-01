package com.ai.st.microservice.operators.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateDeliverySupplyDto", description = "Create Delivery Supply Dto")
public class CreateDeliverySupplyDto implements Serializable {

    private static final long serialVersionUID = 2494274349120415860L;

    @ApiModelProperty(required = true, notes = "Observations")
    private String observations;

    @ApiModelProperty(required = true, notes = "Supply Code")
    private Long supplyCode;

    public CreateDeliverySupplyDto() {

    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Long getSupplyCode() {
        return supplyCode;
    }

    public void setSupplyCode(Long supplyCode) {
        this.supplyCode = supplyCode;
    }

    @Override
    public String toString() {
        return "CreateDeliverySupplyDto{" + "observations='" + observations + '\'' + ", supplyCode=" + supplyCode + '}';
    }
}
