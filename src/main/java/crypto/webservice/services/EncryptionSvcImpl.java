package crypto.webservice.services;

import java.nio.ByteBuffer;
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

import crypto.webservice.api.PlainFloat;

public class EncryptionSvcImpl implements EncryptionSvc {
	private Key key;
	
	public EncryptionSvcImpl() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException{
		String keyString = "C0BAE23DF8B51807B3E17D21925FADF273A70181E1D81B8EDE6C76A5C1F1716E";
		byte[] keyValue = DatatypeConverter.parseHexBinary(keyString);
		key = new SecretKeySpec(keyValue, "AES");
	}
	

	public String encrypt(float num) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Cipher c1 = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] iv = new byte[128/8];
		SecureRandom srandom = new SecureRandom();
		srandom.nextBytes(iv);
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		c1.init(Cipher.ENCRYPT_MODE, key, ivspec);
		byte[] b = ByteBuffer.allocate(4).putFloat(num).array();
		byte[] encVal = c1.doFinal(b);
		String encryptedValue = Base64.getEncoder().encodeToString(encVal);
		//TODO add initialization vector, keyID, version
		String ivValue = Base64.getEncoder().encodeToString(iv);
		return ivValue + encryptedValue;
	}

	public PlainFloat decrypt(String ciphertext) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		String ivStr = ciphertext.substring(0, 24);
		ciphertext = ciphertext.substring(24);
		byte[] iv = Base64.getDecoder().decode(ivStr);
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		Cipher c2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c2.init(Cipher.DECRYPT_MODE, key, ivspec);
		byte[] b = Base64.getDecoder().decode(ciphertext);
		byte[] plainBytes = c2.doFinal(b);
		float f = ByteBuffer.wrap(plainBytes).getFloat();
		return new PlainFloat(f);
	}

}
