package com.hp.vtms.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HashSupport {

	private static final Logger _LOG = LoggerFactory.getLogger(HashSupport.class);
	private static final String _02X = "%02x";
	private static final String ENCRYPT_ALGORITHM = "SHA-1";
	private static final String ENCODING = "UTF-8";

	public String sha1Hash(String input) {
		_LOG.debug("password before encrypted: {}", input);
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
			crypt.reset();
			crypt.update(input.getBytes(ENCODING));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			_LOG.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			_LOG.error(e.getMessage(), e);
		}

		_LOG.debug("password after encrypted: {}", sha1);

		return sha1;
	}

	// encrypt
	public String base64Hash(String input) {
		return new String(Base64.encodeBase64(input.getBytes()));
	}

	// decrypt
	public String decryptBASE64(String key) {
		return new String(Base64.decodeBase64(key.getBytes()));
	}

	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format(_02X, b);
		}
		String result = formatter.toString();
		formatter.close();

		return result;
	}

}
