package com.ai.st.microservice.operators.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SupplyDeliveryDto", description = "Supply Delivery Dto")
public class SupplyDeliveryDto implements Serializable {

	private static final long serialVersionUID = 6986943352552791077L;

	@ApiModelProperty(required = true, notes = "Supply Delivery ID")
	private Long id;

	@ApiModelProperty(required = true, notes = "Date creation")
	private Date createdAt;

	@ApiModelProperty(required = true, notes = "Is downloaded?")
	private Boolean downloaded;

	@ApiModelProperty(required = true, notes = "Date downloaded")
	private Date downloadedAt;

	@ApiModelProperty(required = true, notes = "Observations")
	private String observations;

	@ApiModelProperty(required = true, notes = "Supply Code")
	private Long supplyCode;

	public SupplyDeliveryDto() {

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

	public Boolean getDownloaded() {
		return downloaded;
	}

	public void setDownloaded(Boolean downloaded) {
		this.downloaded = downloaded;
	}

	public Date getDownloadedAt() {
		return downloadedAt;
	}

	public void setDownloadedAt(Date downloadedAt) {
		this.downloadedAt = downloadedAt;
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

}
