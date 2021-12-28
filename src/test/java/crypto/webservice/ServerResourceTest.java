package crypto.webservice;

import static org.junit.Assert.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.junit.Test;

import crypto.webservice.api.PlainInt;
import crypto.webservice.resources.ServerResource;
import crypto.webservice.services.EncryptionSvc;
import crypto.webservice.services.EncryptionSvcImpl;
import crypto.webservice.services.StatsSvc;
import crypto.webservice.services.StatsSvcImpl;

public class ServerResourceTest {

	@Test
	public void testPushAndRecalculate_avg() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		StatsSvc statsSvc = new StatsSvcImpl();
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		SecretKey key = keyGenerator.generateKey();
		EncryptionSvc eSvc = new EncryptionSvcImpl(key.getEncoded(), "0");
		ServerResource svcRes = new ServerResource(statsSvc, eSvc, "0");
		int[] input = new int[]{1, 10, 100, 3, 90, 0, 2000, 5000000};
		float[] averages = new float[]{1, 5.5f, 37f, 28.5f, 40.8f, 34f, 314.85f, 625275.5f};
		for (int i=0; i < input.length; i++){
		    PlainInt pi = new PlainInt();
		    pi.setNum(input[i]);
		    assertEquals(averages[i], svcRes.pushAndRecalculate(pi).getAvg().getNum(), 0.09f);
		}
	}

	@Test
	public void testPushAndRecalculate_sd() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		StatsSvc statsSvc = new StatsSvcImpl();
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		SecretKey key = keyGenerator.generateKey();
		EncryptionSvc eSvc = new EncryptionSvcImpl(key.getEncoded(), "0");
		ServerResource svcRes = new ServerResource(statsSvc, eSvc, "0");
		int[] input = new int[]{1, 10, 100, 3, 90, 0, 2000, 5000000};
		float[] sds = new float[]{0, 4.5f, 44.69f, 41.41f, 44.46f, 43.34f, 689.12f, 1653490.56f};
		for (int i=0; i < input.length; i++){
		    PlainInt pi = new PlainInt();
		    pi.setNum(input[i]);
		    assertEquals(sds[i], svcRes.pushAndRecalculate(pi).getSd().getNum(), 0.9f);
		}
	}
	
	@Test
	public void testPushRecalculateAndEncrypt_Decrypt_sd() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		StatsSvc statsSvc = new StatsSvcImpl();
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		SecretKey key = keyGenerator.generateKey();
		EncryptionSvc eSvc = new EncryptionSvcImpl(key.getEncoded(), "0");
		ServerResource svcRes = new ServerResource(statsSvc, eSvc, "0");
		int[] input = new int[]{1, 10, 100, 3, 90, 0, 2000, 5000000};
		float[] sds = new float[]{0, 4.5f, 44.69f, 41.41f, 44.46f, 43.34f, 689.12f, 1653490.56f};
		for (int i=0; i < input.length; i++){
		    PlainInt pi = new PlainInt();
		    pi.setNum(input[i]);
		    assertEquals(sds[i], svcRes.Decrypt(svcRes.pushRecalculateAndEncrypt(pi).getSd()).getNum(), 0.9f);
		}
	}

	@Test
	public void testPushRecalculateAndEncrypt_Decrypt_avg() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		StatsSvc statsSvc = new StatsSvcImpl();
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		SecretKey key = keyGenerator.generateKey();
		EncryptionSvc eSvc = new EncryptionSvcImpl(key.getEncoded(), "0");
		ServerResource svcRes = new ServerResource(statsSvc, eSvc, "0");
		int[] input = new int[]{1, 10, 100, 3, 90, 0, 2000, 5000000};
		float[] averages = new float[]{1, 5.5f, 37f, 28.5f, 40.8f, 34f, 314.85f, 625275.5f};
		for (int i=0; i < input.length; i++){
		    PlainInt pi = new PlainInt();
		    pi.setNum(input[i]);
		    assertEquals(averages[i], svcRes.Decrypt(svcRes.pushRecalculateAndEncrypt(pi).getAvg()).getNum(), 0.9f);
		}
	}
}
