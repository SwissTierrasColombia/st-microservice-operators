package com.ai.st.microservice.operators.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "OperatorUserDto", description = "Operator User Dto")
public class OperatorUserDto implements Serializable {

	private static final long serialVersionUID = 8040320082459436795L;

	@ApiModelProperty(required = true, notes = "User code")
	private Long userCode;

	public OperatorUserDto() {

	}

	public OperatorUserDto(Long userCode) {
		super();
		this.userCode = userCode;
	}

	public Long getUserCode() {
		return userCode;
	}

	public void setUserCode(Long userCode) {
		this.userCode = userCode;
	}

}
