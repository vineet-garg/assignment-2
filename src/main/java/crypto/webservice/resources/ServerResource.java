package crypto.webservice.resources;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
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
	
	// dependent services can be injected dynamically, just keeping it simple
	public ServerResource(StatsSvc statsSvc, EncryptionSvc encSvc, String keyID) {
		this.statsSvc = statsSvc;
		this.encSvc = encSvc;
		this.keyID = keyID;
	}
	
	@POST
	@Path("/push-and-recalculate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PlainStats pushAndRecalculate(@NotNull @Valid PlainInt i) {
		return statsSvc.GetRunningStats(i.getNum());
	}
	
	@POST
	@Path("/push-and-recalculate-encrypt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EncryptedStats pushRecalculateAndEncrypt(@NotNull @Valid PlainInt i) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		PlainStats ps = pushAndRecalculate(i);
		
		byte[] avgBytes = ByteBuffer.allocate(4).putFloat(ps.getAvg().getNum()).array();
		String encryptedAvg = encSvc.encrypt(avgBytes, keyID);
		
		byte[] sdBytes = ByteBuffer.allocate(4).putFloat(ps.getSd().getNum()).array();
		String encryptedSd = encSvc.encrypt(sdBytes, keyID);
		EncryptedFloat avg = new EncryptedFloat(encryptedAvg, keyID);
		EncryptedFloat sd = new EncryptedFloat(encryptedSd, keyID);
		return new EncryptedStats(avg, sd);
	}
	
	@POST
	@Path("/decrypt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// TODO There is an ambiguity wrt signature of this API in the requirement doc.
	// It says "An" encrypted Number and at the same time output of API 2. The output of API2 is not a single number
	// but 2 numbers. Assuming the requirement as single number.
	public PlainFloat Decrypt(@NotNull @Valid EncryptedFloat s) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
	    if (s.getCipherTxt().length() != encSvc.getExtraBytes(s.getKeyId()) + FLOAT_BASE64_BYTE_SIZE) {
	    	// this check prevents encryption service from trying to decrypt very large invalid input
	    	throw new IllegalArgumentException("invalid input");
	    }
		byte[] plainBytes = encSvc.decrypt(s.getCipherTxt(), s.getKeyId());
		float f = ByteBuffer.wrap(plainBytes).getFloat();
		return new PlainFloat(f);
	}
}
