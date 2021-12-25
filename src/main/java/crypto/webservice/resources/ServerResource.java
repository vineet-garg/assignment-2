package crypto.webservice.resources;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
	
	private StatsSvc statsSvc;
	private EncryptionSvc encSvc;
	
	// dependent services can be injected dynamically, just keeping it simple
	public ServerResource(StatsSvc statsSvc, EncryptionSvc encSvc) {
		this.statsSvc = statsSvc;
		this.encSvc = encSvc;
	}
	
	@POST
	@Path("/push-and-recalculate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PlainStats pushAndRecalculate(PlainInt i) {
		return statsSvc.GetRunningStats(i.getNum());
	}
	
	@POST
	@Path("/push-and-recalculate-encrypt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EncryptedStats pushRecalculateAndEncrypt(PlainInt i) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		PlainStats ps = pushAndRecalculate(i);
		String encryptedAvg = encSvc.encrypt(ps.getAvg().getNum());
		String encryptedSd = encSvc.encrypt(ps.getSd().getNum());
		EncryptedFloat avg = new EncryptedFloat(encryptedAvg);
		EncryptedFloat sd = new EncryptedFloat(encryptedSd);
		return new EncryptedStats(avg, sd);
	}
	
	@POST
	@Path("/decrypt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// TODO There is an ambiguity wrt signature of this API in the requirement doc.
	// It says An encrypted Number and at the same time output of API 2. The output of API2 is not a single number
	// but 2 numbers.
	public PlainFloat Decrypt(EncryptedFloat s) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		return encSvc.decrypt(s.getCipherTxt());
	}
}
