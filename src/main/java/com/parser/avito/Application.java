package com.parser.avito;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    public static void main(String[] args) {

        sendSms("+79992251506", "My test sms 1");

        SpringApplication.run(Application.class, args);
    }

    private static void sendSms(String toPhoneNumber, String messageText) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber("+18705764547"),
                messageText)
                .create();
        System.out.println(message.getSid());
    }

}
