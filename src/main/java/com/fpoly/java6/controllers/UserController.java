package com.fpoly.java6.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java6.beans.UserBean;
import com.fpoly.java6.resp.ResponseData;
import com.fpoly.java6.services.LoginService;
import com.fpoly.java6.services.UserService;

@CrossOrigin(value = { "*" })
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<ResponseData> registerUser(@RequestBody UserBean user) {
	ResponseData responseData = new ResponseData();
	try {
	    userService.registerUser(user.getUsername(), user.getPassword(), user.getRole());

	    responseData.setStatus(true);
	    responseData.setMessage("Register success!");
	    responseData.setData(null);

	    return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
	} catch (Exception e) {
	    responseData.setStatus(false);
	    responseData.setMessage(e.getMessage());
	    responseData.setData(null);
	    return ResponseEntity.badRequest().body(responseData);
	}
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@RequestParam String username, @RequestParam String password) {
	ResponseData responseData = new ResponseData();
	try {
	    String token = loginService.login(username, password);

	    responseData.setStatus(true);
	    responseData.setMessage("Login success!");
	    responseData.setData(token);

	    return ResponseEntity.ok(responseData);
	} catch (IllegalArgumentException e) {
	    responseData.setStatus(false);
	    responseData.setMessage("Login error!");
	    responseData.setData(null);
	    return ResponseEntity.status(401).body(responseData);
	}
    }
}
