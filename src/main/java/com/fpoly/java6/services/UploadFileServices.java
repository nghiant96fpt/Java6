package com.fpoly.java6.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServices {

    private static final String IMAGE_URL_BASE = "http://localhost:8080/images/";

    public String uploadFile(MultipartFile image) {
	Path filePath = Paths.get("images");
	try {
//		    	image.getContentType() => Dinh dang cua file upload
	    Files.createDirectories(filePath);
	    Date date = new Date();
	    String fileName = date.getTime() + ".jpg";
	    Files.copy(image.getInputStream(), filePath.resolve(fileName));

	    return IMAGE_URL_BASE + fileName;
	} catch (IOException e) {
	    e.printStackTrace();
	    return null;
	}
    }
}
