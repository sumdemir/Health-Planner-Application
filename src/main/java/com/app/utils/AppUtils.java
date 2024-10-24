package com.app.utils;

import com.app.contens.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppUtils {

    private AppUtils (){

    }

    public static ResponseEntity<String> getResponseEntity (String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpStatus);
    }
}
