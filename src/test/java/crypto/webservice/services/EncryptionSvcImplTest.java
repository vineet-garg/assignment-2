package crypto.webservice.services;

import static org.junit.Assert.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Test;

public class EncryptionSvcImplTest {

	@Test
	public void testEncrypt_Decrypt() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		String keyString = "C0BAE23DF8B51807B3E17D21925FADF273A70181E1D81B8EDE6C76A5C1F1716E";
		EncryptionSvc esv = new EncryptionSvcImpl(keyString);
		byte[] plainBytes = "plain".getBytes();
		assertEquals("plain", new String(esv.decrypt(esv.encrypt(plainBytes, "0"),"0")));
	}
	
	@Test
	public void testDecrypt_negative() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		String keyString = "C0BAE23DF8B51807B3E17D21925FADF273A70181E1D81B8EDE6C76A5C1F1716E";
		EncryptionSvc esv = new EncryptionSvcImpl(keyString);
		try {
		    esv.decrypt("some random text", "0");
		    fail("decrypting just random bytes will not work");
		} catch (Exception e) {
			// good exception
		}
	}
}
