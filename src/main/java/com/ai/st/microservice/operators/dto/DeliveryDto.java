package com.ai.st.microservice.operators.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "DeliveryDto", description = "Delivery Dto")
public class DeliveryDto implements Serializable {

    private static final long serialVersionUID = -5027313678039653625L;

    @ApiModelProperty(required = true, notes = "Delivery ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Date creation")
    private Date createdAt;

    @ApiModelProperty(required = true, notes = "Manager Code")
    private Long managerCode;

    @ApiModelProperty(required = true, notes = "Municipality Code")
    private String municipalityCode;

    @ApiModelProperty(required = true, notes = "is active?")
    private Boolean isActive;

    @ApiModelProperty(required = true, notes = "Observations")
    private String observations;

    @ApiModelProperty(required = false, notes = "Operator")
    private OperatorDto operator;

    @ApiModelProperty(required = true, notes = "Supplies")
    private List<SupplyDeliveryDto> supplies;

    @ApiModelProperty(required = true, notes = "Report url")
    private String downloadReportUrl;

    public DeliveryDto() {
        this.supplies = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public OperatorDto getOperator() {
        return operator;
    }

    public void setOperator(OperatorDto operator) {
        this.operator = operator;
    }

    public List<SupplyDeliveryDto> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<SupplyDeliveryDto> supplies) {
        this.supplies = supplies;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getDownloadReportUrl() {
        return downloadReportUrl;
    }

    public void setDownloadReportUrl(String downloadReportUrl) {
        this.downloadReportUrl = downloadReportUrl;
    }

}
