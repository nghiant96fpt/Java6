package com.fpoly.java6.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java6.beans.TwilioBean;
import com.fpoly.java6.beans.TwilioCreateChannelNewUserBean;
import com.fpoly.java6.resp.ResponseData;
import com.fpoly.java6.resp.TwilioResponse;
import com.fpoly.java6.utils.Constans;
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.ChatGrant;
import com.twilio.rest.chat.v2.service.Channel;
import com.twilio.rest.chat.v2.service.Channel.ChannelType;
import com.twilio.rest.chat.v2.service.User;
import com.twilio.rest.chat.v2.service.channel.Member;

@CrossOrigin(value = { "*" })
@RestController
@RequestMapping("/twilio")
public class TwilioController {

    @PostMapping("/get-token")
    public ResponseEntity<ResponseData> getTwilioToken(@RequestBody TwilioBean twilioBean) {
	ResponseData responseData = new ResponseData();

	ChatGrant grant = new ChatGrant();
	grant.setServiceSid(Constans.TWILIO_SERVICE_ID);

	try {
	    User userTwilio = User.fetcher(Constans.TWILIO_SERVICE_ID, twilioBean.getUserId()).fetch();

	    AccessToken token = new AccessToken.Builder(Constans.TWILIO_AUTH_TOKEN, Constans.TWILIO_SECRET,
		    Constans.TWILIO_ACCOUNT_SID).identity(userTwilio.getIdentity()).grant(grant).ttl(3600).build();

	    responseData.setStatus(true);
	    responseData.setMessage("Success");
	    responseData.setData(token.toJwt());

	    return ResponseEntity.status(HttpStatus.OK).body(responseData);
	} catch (Exception e) {
	    e.printStackTrace();

	    User userTwilio = User.creator(Constans.TWILIO_SERVICE_ID, twilioBean.getUserId())
		    .setFriendlyName(twilioBean.getName()).setAttributes("{\"name\": \"" + twilioBean.getName() + "\"}")
		    .create();

	    AccessToken token = new AccessToken.Builder(Constans.TWILIO_AUTH_TOKEN, Constans.TWILIO_SECRET,
		    Constans.TWILIO_ACCOUNT_SID).identity(userTwilio.getIdentity()) // Unique identity for the user
		    .grant(grant).ttl(3600) // Token validity in seconds
		    .build();

	    responseData.setStatus(true);
	    responseData.setMessage("Success");
	    responseData.setData(token.toJwt());

	    return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
	}
    }

    @PostMapping("/create-channel/new-user")
    public ResponseEntity<ResponseData> createChannelWithNewUser(
	    @RequestBody TwilioCreateChannelNewUserBean twilioBean) {
	ResponseData responseData = new ResponseData();

	User userTwilioOne = null;

	User userTwilioTwo = null;

	try {
	    userTwilioOne = User.fetcher(Constans.TWILIO_SERVICE_ID, twilioBean.getUserIdOne()).fetch();
	} catch (Exception e) {
	    userTwilioOne = User.creator(Constans.TWILIO_SERVICE_ID, twilioBean.getUserIdOne())
		    .setFriendlyName(twilioBean.getNameOne())
		    .setAttributes("{\"name\": \"" + twilioBean.getNameOne() + "\"}").create();
	}

	try {
	    userTwilioTwo = User.fetcher(Constans.TWILIO_SERVICE_ID, twilioBean.getUserIdTwo()).fetch();
	} catch (Exception e) {
	    userTwilioTwo = User.creator(Constans.TWILIO_SERVICE_ID, twilioBean.getUserIdTwo())
		    .setFriendlyName(twilioBean.getNameTwo())
		    .setAttributes("{\"name\": \"" + twilioBean.getNameTwo() + "\"}").create();
	}

	try {

	    Channel channel = Channel.creator(Constans.TWILIO_SERVICE_ID)
		    .setFriendlyName(String.format("%s - %s", twilioBean.getNameOne(), twilioBean.getNameTwo()))
		    .setType(ChannelType.PUBLIC).setAttributes("{\"autoJoin\": true}").create();

	    Member.creator(Constans.TWILIO_SERVICE_ID, channel.getSid(), userTwilioOne.getIdentity()).create();

	    Member.creator(Constans.TWILIO_SERVICE_ID, channel.getSid(), userTwilioTwo.getIdentity()).create();

	    responseData.setStatus(true);
	    responseData.setMessage("Create Channel Success!");

	    TwilioResponse response = new TwilioResponse();

	    ChatGrant grant = new ChatGrant();
	    grant.setServiceSid(Constans.TWILIO_SERVICE_ID);

	    AccessToken token = new AccessToken.Builder(Constans.TWILIO_AUTH_TOKEN, Constans.TWILIO_SECRET,
		    Constans.TWILIO_ACCOUNT_SID).identity(userTwilioOne.getIdentity()).grant(grant).ttl(3600).build();

	    response.setChannelId(channel.getSid());
	    response.setToken(token.toJwt());

	    responseData.setData(response);

	    return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
	} catch (Exception e) {
	    e.printStackTrace();

	    responseData.setStatus(false);
	    responseData.setMessage("Create Channel Fail!");
	    responseData.setData(null);

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
	}
    }

}
