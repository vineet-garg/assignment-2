package crypto.webservice.resources;

import java.nio.ByteBuffer;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import crypto.webservice.api.EncryptedFloat;
import crypto.webservice.api.EncryptedStats;
import crypto.webservice.api.PlainFloat;
import crypto.webservice.api.PlainInt;
import crypto.webservice.api.PlainStats;
import crypto.webservice.services.EncryptionSvc;
import crypto.webservice.services.StatsSvc;

//TODO Input validation

@Path("")
public class ServerResource {
	
	private static final int FLOAT_BASE64_BYTE_SIZE = 8;
	private final String keyID;
	private StatsSvc statsSvc;
	private EncryptionSvc encSvc;
	
	// TODO dependent services can be injected dynamically, just keeping it simple for now
	public ServerResource(StatsSvc statsSvc, EncryptionSvc encSvc, String keyID) {
		this.statsSvc = statsSvc;
		this.encSvc = encSvc;
		this.keyID = keyID;
	}
	
	/**
	 * Adds input integer into the running metrics and 
	 * returns new running average and running standard deviation.
	 * The calculations are done using Welford's online algorithm. Individual numbers
	 * are not saved.
	 * @param i any 32 bit integer
	 * @return Stats (running average and running standard deviation) in plain
	 */
	@POST
	@Path("/push-and-recalculate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PlainStats pushAndRecalculate(@NotNull PlainInt i) {
		return statsSvc.getRunningStats(i.getNum());
	}
	
	/**
	 * /**
	 * Adds input integer into the running metrics and 
	 * returns new running average and running standard deviation in Encrypted form
	 * The calculations are done using Welford's online algorithm. Individual numbers
	 * are not saved.
	 * @param i PlainInt wrapper of any 32 bit integer
	 * @return EncryptedStats containing EncryptedFloat values of running average and running standard deviation. 
	 * KeyId identifying the key used for encryption is also part of the returned data.
	 */
	@POST
	@Path("/push-and-recalculate-encrypt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EncryptedStats pushRecalculateAndEncrypt(@NotNull PlainInt i) {
		PlainStats ps = pushAndRecalculate(i);
		
		byte[] avgBytes = ByteBuffer.allocate(4).putFloat(ps.getAvg().getNum()).array();
		String encryptedAvg = encSvc.encrypt(avgBytes, keyID);
		
		byte[] sdBytes = ByteBuffer.allocate(4).putFloat(ps.getSd().getNum()).array();
		String encryptedSd = encSvc.encrypt(sdBytes, keyID);
		EncryptedFloat avg = new EncryptedFloat(encryptedAvg, keyID);
		EncryptedFloat sd = new EncryptedFloat(encryptedSd, keyID);
		return new EncryptedStats(avg, sd);
	}
	
	/**
	 * Decrypts cipherTxt value in  EncryptedFloat value using the key defined by keyId.
	 * @param s EncryptedFloat Containing ciphertext and keyId with which the encryption was performed.
	 * @return PlainFloat representing Plain value of the encrypted float value.
	 */
	@POST
	@Path("/decrypt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// Note: There is an ambiguity wrt signature of this API in the requirement doc.
	// It says "An" encrypted Number and at the same time output of API 2. The output of API2 is not a single number
	// but 2 numbers. Assuming the requirement as single number.
	public PlainFloat Decrypt(@NotNull EncryptedFloat s) {
	    if (s.getCipherTxt().length() != encSvc.getExtraBytes(s.getKeyId()) + FLOAT_BASE64_BYTE_SIZE) {
	    	// Note: This check prevents encryption service from trying to decrypt very large invalid input
	    	throw new RuntimeException("Error While Encrypting/Decrypting: Check the Input Or Server logs");
	    }
		byte[] plainBytes = encSvc.decrypt(s.getCipherTxt(), s.getKeyId());
		float f = ByteBuffer.wrap(plainBytes).getFloat();
		return new PlainFloat(f);
	}
}
