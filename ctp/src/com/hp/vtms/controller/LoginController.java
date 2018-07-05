package com.hp.vtms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.vtms.model.KeyPairData;
import com.hp.vtms.util.EncryptData;
import com.hp.vtms.util.HashSupport;



@Controller
@RequestMapping("login")
public class LoginController {
	@Autowired
	private EncryptData encryptData;
	
	private static final Logger _LOG = LoggerFactory.getLogger(HashSupport.class);

	@RequestMapping
	public String doLogin(ModelMap map,HttpServletRequest request) {
		HttpSession session=request.getSession();
//		KeyPairData keyPairData=encryptData.getKey();
//        map.put("exponent", keyPairData.getExponent());
//        map.put("modulus", keyPairData.getModulus());
//        session.setAttribute("keyPairData", keyPairData);
		return "login";
	}

}
