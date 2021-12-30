package crypto.webservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.junit.Test;

public class EncryptionSvcImplTest {

	@Test
	public void testEncrypt_Decrypt() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, EncryptionSvcException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		SecretKey key = keyGenerator.generateKey();
		EncryptionSvc esv = new EncryptionSvcImpl(key.getEncoded(), "0");
		byte[] plainBytes = "plain".getBytes();
		assertEquals("plain", new String(esv.decrypt(esv.encrypt(plainBytes, "0"), "0")));
	}

	@Test
	public void testEncrypt_Decrypt_InvalidKeyID()
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException, EncryptionSvcException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		SecretKey key = keyGenerator.generateKey();
		EncryptionSvc esv = new EncryptionSvcImpl(key.getEncoded(), "0");
		byte[] plainBytes = "plain".getBytes();
		try {
			esv.decrypt(esv.encrypt(plainBytes, "1"), "0");
			fail("Invalid keyId");
		} catch (IllegalArgumentException e) {
			// good exception
		}
	}

	@Test
	public void testDecrypt_GarbageInput() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		SecretKey key = keyGenerator.generateKey();
		EncryptionSvc esv = new EncryptionSvcImpl(key.getEncoded(), "0");
		try {
			esv.decrypt("some random text", "0");
			fail("decrypting just random bytes will not work");
		} catch (EncryptionSvcException e) {
			// good exception
		}
	}
}
