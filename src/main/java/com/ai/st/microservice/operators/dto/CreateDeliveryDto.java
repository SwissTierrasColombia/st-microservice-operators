package com.ai.st.microservice.operators.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateDeliveryDto", description = "Create Delivery Dto")
public class CreateDeliveryDto implements Serializable {

	private static final long serialVersionUID = 6501515533475244560L;

	@ApiModelProperty(required = true, notes = "Manager Code")
	private Long managerCode;

	@ApiModelProperty(required = true, notes = "Municipality Code")
	private String municipalityCode;

	@ApiModelProperty(required = true, notes = "Observations")
	private String observations;

	@ApiModelProperty(required = true, notes = "Supplies delivered")
	private List<CreateDeliverySupplyDto> supplies;

	public CreateDeliveryDto() {
		this.supplies = new ArrayList<CreateDeliverySupplyDto>();
	}

	public Long getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(Long managerCode) {
		this.managerCode = managerCode;
	}

	public String getMunicipalityCode() {
		return municipalityCode;
	}

	public void setMunicipalityCode(String municipalityCode) {
		this.municipalityCode = municipalityCode;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public List<CreateDeliverySupplyDto> getSupplies() {
		return supplies;
	}

	public void setSupplies(List<CreateDeliverySupplyDto> supplies) {
		this.supplies = supplies;
	}

}
