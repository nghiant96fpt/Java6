package com.fpoly.java6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fpoly.java6.utils.Constans;
import com.twilio.Twilio;

@SpringBootApplication
public class Java6Application {

    static {
	Twilio.init(Constans.TWILIO_ACCOUNT_SID, Constans.TWILIO_AUTH_TOKEN);
    }

    public static void main(String[] args) {
	SpringApplication.run(Java6Application.class, args);
    }

}
