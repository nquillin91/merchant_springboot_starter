package com.merchant.app.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.merchant.app.dto.AffirmCheckoutConfirmationRequest;
import com.merchant.app.service.AffirmService;

@RestController("AffirmController")
public class AffirmController {
    Logger logger = LoggerFactory.getLogger(AffirmController.class);
    
    @Autowired
    private AffirmService affirmService;
    
    @RequestMapping(value="/affirmCheckout", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public void affirmCheckout(@RequestBody AffirmCheckoutConfirmationRequest request) throws Exception {
        affirmService.processCheckout(request.getCheckoutToken());
    }
}