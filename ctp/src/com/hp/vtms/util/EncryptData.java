package com.hp.vtms.util;

	/**
	 * Copyright (c) 2013 Jumbomart All Rights Reserved.
	 *
	 * This software is the confidential and proprietary information of Jumbomart.
	 * You shall not disclose such Confidential Information and shall use it only in
	 * accordance with the terms of the license agreement you entered into
	 * with Jumbo.
	 *
	 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
	 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
	 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
	 * PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY DAMAGES
	 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
	 * THIS SOFTWARE OR ITS DERIVATIVES.
	 *
	 */
	import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

	import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.vtms.model.KeyPairData;

	

	@Component
	public class EncryptData   {
		
	    
		private static Logger logger = LoggerFactory.getLogger(EncryptData.class);
//	    @Autowired
//	    private SystemRsaKeyDao systemRsaKeyDao;

	    public KeyPairData getKey(){
	    	KeyPair key=generateKeyPair();
	    	KeyPairData keyPairData=new KeyPairData();
	    	if(key!=null){
	    		RSAPublicKey publicKey  = (RSAPublicKey) key.getPublic(); 
				RSAPrivateKey privateKey = (RSAPrivateKey) key.getPrivate();
		    	String privateKeyString=new String(Hex.encodeHex(privateKey.getEncoded()));
//				String privateKeyString=new String(Base64.encodeBase64(privateKey.getEncoded()));
		    	String exponent=new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));
//		    	String exponent=new String(Base64.encodeBase64(publicKey.getPublicExponent().toByteArray()));
		    	keyPairData.setExponent(exponent);
		    	String modulus=new String(Hex.encodeHex(publicKey.getModulus().toByteArray()));
//		    	String modulus=new String(Base64.encodeBase64(publicKey.getModulus().toByteArray()));
		    	keyPairData.setModulus(modulus);
		    	keyPairData.setPrivateKey(privateKeyString);
//				try {
////					decrypt(Hex.decodeHex(privateKeyString.toCharArray()),Hex.decodeHex("Password*888".toCharArray()));
//				} catch (DecoderException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
	    	}else{
	    		logger.error("System Information Uninitialized RSA KEY is error");
	    	}
	    	
	    	return keyPairData;
	    }
		
		/**
		 * 
		 * @return
		 */
		private  KeyPair generateKeyPair() {
	    	KeyPair key = null;
	    	 try {
				KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
				 // keyPairGen.initialize(1024, new SecureRandom(DateFormatUtils.format(new Date(), "yyyyMMdd").getBytes("UTF-8")));
				 keyPairGen.initialize(4096, new SecureRandom(DateFormatUtils.format(new Date(), "yyyyMMdd").getBytes("UTF-8")));
				 key =  keyPairGen.generateKeyPair();
			} catch (NoSuchAlgorithmException e) {
				logger.error("Random RSA KEY is error");
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return key;
	    }
	    /**
	     * 
	     * 
	     * @param privateKey 
	     * @param data 
	     * @return 
	     */
	    public String decrypt(String privatekey, String data) throws Exception {
	    	//data=URLDecoder.decode(data, "utf-8");
	    	byte[] key=Hex.decodeHex(privatekey.toCharArray());
	    	byte[] pass=Hex.decodeHex(data.toCharArray());
	    	logger.info("decrypt before:"+pass);
//	    	byte[] key=Base64.decodeBase64(privatekey);
//	    	byte[] pass=Base64.decodeBase64(data);
//	    	byte[] pass=data.getBytes("utf-8");
	    	try{
			PrivateKey privateKey = getPrivateKey(key);
			Cipher ci = Cipher.getInstance("RSA", new BouncyCastleProvider());
			ci.init(Cipher.DECRYPT_MODE, privateKey);
			pass=ci.doFinal(pass);

			data=new String(pass,"UTF-8");
			logger.info("revert charset:"+data);
			data= StringUtils.reverse(data);
			logger.info("decrypt:"+data);
			return data;
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		return "";
	    	}
			
	    }
	    /**
	     * 
	     * @param key 
	     * @throws Exception
	     */
	    private   PrivateKey getPrivateKey(byte[] key) throws Exception {
	          PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
	          KeyFactory keyFactory = KeyFactory.getInstance("RSA",new BouncyCastleProvider());
	          PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
	          return privateKey;
	    }
	    
	}

