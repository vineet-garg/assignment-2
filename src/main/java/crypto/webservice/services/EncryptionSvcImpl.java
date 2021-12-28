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
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionSvcImpl implements EncryptionSvc {
	
	// TODO make encryption params configurable for better crypto agility
	private static final String ALGO = "AES";
	private static final String MODE = "AES/GCM/NoPadding";
	private static final int GCM_IV_SIZE = 96;
	private static final int GCM_IV_BASE64_SIZE = GCM_IV_SIZE * 8 / 6;
	private static final int GCM_TAG_LENGTH = 96;
	private static final int GCM_TAG_BASE64_LENGTH = GCM_TAG_LENGTH * 8 / 6;
	
	// TODO convert this to map when there are multiple keys in future
	private Key key;
	private String keyId;
	
	public EncryptionSvcImpl(byte[] keyRaw, String keyId) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException{
		key = new SecretKeySpec(keyRaw, ALGO);
		this.keyId = keyId;
	}

	public String encrypt(byte[] b, String keyId) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		if (keyId != this.keyId) {
			throw new IllegalArgumentException("keyId: " + keyId + " is not valid." );
		}
		Cipher c1 = Cipher.getInstance(MODE);
		byte[] iv = new byte[GCM_IV_SIZE/8];
		SecureRandom srandom = new SecureRandom();
		srandom.nextBytes(iv);
		GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
		c1.init(Cipher.ENCRYPT_MODE, key, gcmSpec);
		
		byte[] encVal = c1.doFinal(b);
		String encryptedValue = Base64.getEncoder().encodeToString(encVal);
		String ivValue = Base64.getEncoder().encodeToString(iv);
		return ivValue + encryptedValue;
	}

	public byte[] decrypt(String ciphertext, String keyId) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		if (keyId != this.keyId) {
			throw new IllegalArgumentException("keyId: " + keyId + " is invalid." );
	    }
		String ivStr = ciphertext.substring(0, GCM_IV_BASE64_SIZE/8);
		ciphertext = ciphertext.substring(GCM_IV_BASE64_SIZE/8);
		byte[] iv = Base64.getDecoder().decode(ivStr);
		Cipher c2 = Cipher.getInstance(MODE);
		GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
		c2.init(Cipher.DECRYPT_MODE, key, gcmSpec);
		byte[] b = Base64.getDecoder().decode(ciphertext);
		byte[] plainBytes = c2.doFinal(b);
		return plainBytes;
	}
	
	public int getExtraBytes(String keyId) {
		if (keyId != this.keyId) {
			throw new IllegalArgumentException("keyId: " + keyId + " is invalid." );
	    }
		return GCM_IV_BASE64_SIZE/8  + GCM_TAG_BASE64_LENGTH/8;
	}
}
