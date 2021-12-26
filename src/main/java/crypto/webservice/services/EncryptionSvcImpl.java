package crypto.webservice.services;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class EncryptionSvcImpl implements EncryptionSvc {
	private Key key;
	
	public EncryptionSvcImpl(String keyString) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException{
		byte[] keyValue = DatatypeConverter.parseHexBinary(keyString);
		key = new SecretKeySpec(keyValue, "AES");
	}

	public String encrypt(byte[] b, String keyId) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		if (keyId != "0") {
			throw new IllegalArgumentException("keyId: " + keyId + " is not supported." );
		}
		Cipher c1 = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] iv = new byte[128/8];
		SecureRandom srandom = new SecureRandom();
		srandom.nextBytes(iv);
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		c1.init(Cipher.ENCRYPT_MODE, key, ivspec);
		
		byte[] encVal = c1.doFinal(b);
		String encryptedValue = Base64.getEncoder().encodeToString(encVal);
		String ivValue = Base64.getEncoder().encodeToString(iv);
		return ivValue + encryptedValue;
	}

	public byte[] decrypt(String ciphertext, String keyId) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		if (keyId != "0") {
			throw new IllegalArgumentException("keyId: " + keyId + " is not supported." );
	    }
		String ivStr = ciphertext.substring(0, 24);
		ciphertext = ciphertext.substring(24);
		byte[] iv = Base64.getDecoder().decode(ivStr);
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		Cipher c2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c2.init(Cipher.DECRYPT_MODE, key, ivspec);
		byte[] b = Base64.getDecoder().decode(ciphertext);
		byte[] plainBytes = c2.doFinal(b);
		return plainBytes;
	}
}
