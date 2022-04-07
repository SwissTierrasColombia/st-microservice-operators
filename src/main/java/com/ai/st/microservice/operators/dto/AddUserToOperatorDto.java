package com.ai.st.microservice.operators.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AddUserToOperatorDto", description = "Add User To Operator Dto")
public class AddUserToOperatorDto implements Serializable {

    private static final long serialVersionUID = 3281389121792970476L;

    @ApiModelProperty(required = true, notes = "User code")
    private Long userCode;

    @ApiModelProperty(required = true, notes = "Operator ID")
    private Long operatorId;

    public AddUserToOperatorDto() {

    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "AddUserToOperatorDto{" + "userCode=" + userCode + ", operatorId=" + operatorId + '}';
    }
}
