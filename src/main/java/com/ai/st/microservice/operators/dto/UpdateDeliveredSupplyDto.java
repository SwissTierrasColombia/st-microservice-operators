package com.ai.st.microservice.operators.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UpdateDeliveredSupplyDto", description = "Update Delivered Supply Dto")
public class UpdateDeliveredSupplyDto implements Serializable {

	private static final long serialVersionUID = 6618248721399600770L;

	@ApiModelProperty(required = true, notes = "Has the input been downloaded?")
	private Boolean downloaded;

	@ApiModelProperty(required = true, notes = "Observations")
	private String observations;

	@ApiModelProperty(required = true, notes = "Report URL")
	private String reportUrl;

	@ApiModelProperty(required = true, notes = "Downloaded by")
	private Long downloadedBy;

	public UpdateDeliveredSupplyDto() {

	}

	public Boolean getDownloaded() {
		return downloaded;
	}

	public void setDownloaded(Boolean downloaded) {
		this.downloaded = downloaded;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getReportUrl() {
		return reportUrl;
	}

	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}

	public Long getDownloadedBy() {
		return downloadedBy;
	}

	public void setDownloadedBy(Long downloadedBy) {
		this.downloadedBy = downloadedBy;
	}

}
