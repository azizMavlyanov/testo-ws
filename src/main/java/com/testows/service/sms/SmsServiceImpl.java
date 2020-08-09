package com.testows.service.sms;

import com.testows.models.SmsCode;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class SmsServiceImpl implements SmsService {
    public static final String ACCOUNT_SID = "ACcdcf9db2cbaffe5eef93d099cfc72db9";
    public static final String AUTH_TOKEN = "af4160bb13c4f52588efddd4870ca979";
    public static final String TWILIO_NUMBER = "+12017620709";

    @Override
    public SmsCode getSmsCode(String phone) {
        String code = RandomString.make(6);

        try {
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

            // Build a filter for the MessageList
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Body", String.format("Disposable code: %s", code)));
            params.add(new BasicNameValuePair("To", String.format("+7%s", phone))); //Add real number here
            params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
        }
        catch (TwilioRestException e) {
            System.out.println(e.getErrorMessage());
        }

        return new SmsCode(code, 60);
    }
}
