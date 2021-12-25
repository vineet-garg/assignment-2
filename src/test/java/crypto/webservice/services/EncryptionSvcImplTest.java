package crypto.webservice.services;

import static org.junit.Assert.assertEquals;

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
		float num = 10.5f;
		assertEquals(num, esv.decrypt(esv.encrypt(num)).getNum(), 0.0f);
		
		num = 1000.555f;
		assertEquals(num, esv.decrypt(esv.encrypt(num)).getNum(), 0.0f);
	}

}
