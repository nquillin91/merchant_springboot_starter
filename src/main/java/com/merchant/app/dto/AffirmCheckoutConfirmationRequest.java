package com.merchant.app.dto;

import lombok.Data;

@Data
public class AffirmCheckoutConfirmationRequest {
	private String checkoutToken;
}