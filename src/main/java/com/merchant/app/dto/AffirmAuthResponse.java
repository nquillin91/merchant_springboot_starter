package com.merchant.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AffirmAuthResponse {
	@JsonProperty("created")
	private String orderCreatedDate;
	
	@JsonProperty("id")
	private String affirmChargeId;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("amount")
	private String orderAmount;
	
	@JsonProperty("merchant_external_reference")
	private String merchantOrderId;
	
	@JsonProperty("is_instore")
	private boolean isInstoreOrder;
	
	public AffirmAuthResponse() {}
}