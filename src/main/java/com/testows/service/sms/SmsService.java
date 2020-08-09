package com.testows.service.sms;

import com.testows.models.SmsCode;

public interface SmsService {
    SmsCode getSmsCode(String phone);
}
