package com.fpoly.java6.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.java6.resp.ResponseData;
import com.fpoly.java6.services.UploadFileServices;

@CrossOrigin(value = { "*" })
@RestController
public class FileUploadController {
    @Autowired
    UploadFileServices services;

    @PostMapping("/upload")
    public ResponseEntity<ResponseData> uploadFile(@RequestPart("images") List<MultipartFile> images) {
	ArrayList<String> urlImages = new ArrayList<String>();

	for (MultipartFile file : images) {
	    String urlImage = services.uploadFile(file);
	    if (urlImage != null) {

		// Luu DATABASE

		urlImages.add(urlImage);
	    }
	}

	ResponseData responseData = new ResponseData();
	responseData.setStatus(true);
	responseData.setMessage("Success!");
	responseData.setData(urlImages);

	return ResponseEntity.ok(responseData);
    }
}
