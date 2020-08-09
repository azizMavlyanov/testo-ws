package com.testows.controllers;

import com.testows.service.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping(value = "/api/v1/sms")
//@Validated
public class SmsController {
    @Autowired
    private SmsService smsService;

    @GetMapping(
            value = "/code",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> getSmsCode(
            @Valid
            @RequestParam(value = "phone")
            @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Invalid phone number")
                    String phone
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(smsService.getSmsCode(phone));
    };
}
