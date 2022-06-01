package com.ai.st.microservice.operators.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UpdateDeliveryDto")
public class UpdateDeliveryDto implements Serializable {

    private static final long serialVersionUID = -3896720955663959193L;

    @ApiModelProperty(required = true, notes = "Report URL")
    private String reportUrl;

    public UpdateDeliveryDto() {

    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    @Override
    public String toString() {
        return "UpdateDeliveryDto{" + "reportUrl='" + reportUrl + '\'' + '}';
    }
}
