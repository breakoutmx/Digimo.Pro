/*Copyright (c) 2017-2018 Breakout.MX All Rights Reserved.
 This software is the confidential and proprietary information of breakout.mx You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with Breakout.MX*/
package com.digimo_pro.createtempusername;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;


import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.service.annotations.ExposeToClient;
import com.wavemaker.runtime.service.annotations.HideFromClient;

import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.sql.Timestamp;
import java.text.*;

import java.security.SecureRandom;
import java.math.BigInteger;


// <%@page import="java.io.*, java.util.Date, java.util.Enumeration" %> 


//import com.digimo_pro.createtempusername.model.*;

/**
 * This is a singleton class with all its public methods exposed as REST APIs via generated controller class.
 * To avoid exposing an API for a particular public method, annotate it with @HideFromClient.
 *
 * Method names will play a major role in defining the Http Method for the generated APIs. For example, a method name
 * that starts with delete/remove, will make the API exposed as Http Method "DELETE".
 *
 * Method Parameters of type primitives (including java.lang.String) will be exposed as Query Parameters &
 * Complex Types/Objects will become part of the Request body in the generated API.
 */
@ExposeToClient
public class CreateTempUsername {

    private static final Logger logger = LoggerFactory.getLogger(CreateTempUsername.class);

    @Autowired
    private SecurityService securityService;
    
    private SecureRandom random = new SecureRandom();

    /**
     * Generate a random alphanumeric base-32 encoded string securely.
     */
    @HideFromClient
    public String randomId() {
        return new BigInteger(60, random).toString(32);
    }

    /**
     * This is sample java operation that accepts an input from the caller and responds with "Hello".
     *
     * SecurityService that is Autowired will provide access to the security context of the caller. It has methods like isAuthenticated(),
     * getUserName() and getUserId() etc which returns the information based on the caller context.
     *
     * Methods in this class can declare HttpServletRequest, HttpServletResponse as input parameters to access the
     * caller's request/response objects respectively. These parameters will be injected when request is made (during API invocation).
     */
    public String sampleJavaOperation(String name, HttpServletRequest request) {
        logger.debug("Starting sample operation with request url " + request.getRequestURL().toString());
        
        String result = null;
        if (securityService.isAuthenticated()) {
            result = "Hello " + name + ", You are logged in as "+  securityService.getLoggedInUser().getUserName();
        } else {
            result = "Hello " + name + ", You are not authenticated yet!";
        }
        logger.debug("Returning {}", result);
        return result;
    }
    
    public String invokeOperation(String name, HttpServletRequest request) {
        logger.debug("Starting create temporary username with request url " + request.getRequestURL().toString());
        
        String result = null;
        // String timestamp = null;
        // java.util.Date date = new java.util.Date();
        // java.sql.Timestamp ts_now = new java.sql.Timestamp(date.getTime());
        // timestamp = new SimpleDateFormat("yyyy.dd.HH.mm.ss").format(date);
        // Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // timestamp = new java.util.Date();
        // timestamp = new SimpleDateFormat("yyyy.dd.HH.mm.ss").format(new Date());
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "uuuu.MM.dd.HH.mm.ss" );
        // String timestamp = zdt.format( formatter );

        result = "tempusername_" + randomId();
        
        logger.debug("Returning temp username as", result);
        return result;
    }

}
