package com.merchant.app.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merchant.app.dto.AffirmAuthResponse;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class AffirmService {

	@Value("${affirm.public.api.key}")
	private String publicApiKey;
	
	@Value("${affirm.private.api.key}")
	private String privateApiKey;
	
	@Value("${affirm.api.endpoint}")
	private String affirmApiEndpoint;
	
	public void processCheckout(String checkoutToken) throws Exception {
		try {
			// Make the auth request and then validate the response
			AffirmAuthResponse authResponse = makeAuthRequest(checkoutToken);
			boolean isValidAuth = validateAuthResponse(authResponse);
			
			if (isValidAuth) {
				// Do internal processing of order
			}
		} catch(Exception e) {
			e.printStackTrace();
			// Do error handling
			throw new Exception("There was a problem checking out with Affirm.");
		}
	}
	
	private AffirmAuthResponse makeAuthRequest(String checkoutToken) throws Exception {
		// Prepare our object mapper that with convert the response from Affirm into its
		// appropriate pojo
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
		// Prep the encoded basic auth string
	    String authString = publicApiKey + ":" + privateApiKey;
        byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        
        // Setup our http client and create the response body using the the passed in checkout token
        // TODO: using a hard-coded default order_id
        OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
	    RequestBody requestBody = RequestBody.create("{\"checkout_token\":\""
	    		+ checkoutToken + "\",\"order_id\":\"SpringBoot_Test_OrderID\"}", mediaType);
	    
	    // Setup the post request to the auth endpoint`
	    Request request = new Request.Builder()
	    		.url(affirmApiEndpoint)
	    		.method("POST", requestBody)
	    		.addHeader("Authorization", "Basic " + authStringEnc)
	    		.addHeader("Content-Type", "application/json")
	    		.build();
	    
	    // Get the response and map it to our pojo before closing the request body's stream
	    Response response = client.newCall(request).execute();
	    AffirmAuthResponse authResponse = objectMapper.readValue(response.body().string(), AffirmAuthResponse.class);
		response.body().close();
		
		return authResponse;
	}
	
	private boolean validateAuthResponse(AffirmAuthResponse authResponse) {
		return true;
	}
}