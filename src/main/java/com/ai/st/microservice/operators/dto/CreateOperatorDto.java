package com.ai.st.microservice.operators.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateOperatorDto")
public class CreateOperatorDto implements Serializable {

    private static final long serialVersionUID = 4784320463657739097L;

    @ApiModelProperty(required = true, notes = "Operator name")
    private String name;

    @ApiModelProperty(required = true, notes = "Operator tax identification number")
    private String taxIdentificationNumber;

    @ApiModelProperty(required = false, notes = "Operator alias")
    private String alias;

    @ApiModelProperty(required = true, notes = "Date creation")
    private Date createdAt;

    @ApiModelProperty(required = true, notes = "Is public ?")
    private Boolean isPublic;

    @ApiModelProperty(required = true, notes = "State")
    private OperatorStateDto operatorState;

    public CreateOperatorDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public OperatorStateDto getOperatorState() {
        return operatorState;
    }

    public void setOperatorState(OperatorStateDto operatorState) {
        this.operatorState = operatorState;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
