package crypto.webservice.resources;

import java.nio.ByteBuffer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import crypto.webservice.api.EncryptedFloat;
import crypto.webservice.api.EncryptedStats;
import crypto.webservice.api.PlainFloat;
import crypto.webservice.api.PlainInt;
import crypto.webservice.api.PlainStats;
import crypto.webservice.services.EncryptionSvc;
import crypto.webservice.services.EncryptionSvcException;
import crypto.webservice.services.StatsSvc;

@Path("")
public class ServerResource {

	private static final int FLOAT_BASE64_BYTE_SIZE = 8;
	private final String currentKeyId;
	private StatsSvc statsSvc;
	private EncryptionSvc encSvc;

	// TODO dependent services can be injected dynamically, just keeping it
	// simple for now
	public ServerResource(StatsSvc statsSvc, EncryptionSvc encSvc, String currentKeyID) {
		this.statsSvc = statsSvc;
		this.encSvc = encSvc;
		this.currentKeyId = currentKeyID;
	}

	/**
	 * Adds input integer into the running metrics and returns new running
	 * average and running standard deviation. The calculations are done using
	 * Welford's online algorithm. Individual numbers are not saved.
	 * 
	 * @param i
	 *            any 32 bit integer
	 * @return Stats (running average and running standard deviation) in plain
	 */
	@POST
	@Path("/push-and-recalculate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PlainStats pushAndRecalculate(@NotNull @Valid PlainInt i) {
		return statsSvc.getRunningStats(i.getNum());
	}

	/**
	 * Adds input integer into the running metrics and returns new running
	 * average and running standard deviation in Encrypted form The calculations
	 * are done using Welford's online algorithm. Individual numbers are not
	 * saved.
	 * 
	 * @param i
	 *            PlainInt wrapper of any 32 bit integer
	 * @return EncryptedStats containing EncryptedFloat values of running
	 *         average and running standard deviation. KeyId identifying the key
	 *         used for encryption is also part of the returned data.
	 */
	@POST
	@Path("/push-recalculate-and-encrypt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EncryptedStats pushRecalculateAndEncrypt(@NotNull @Valid PlainInt i) {
		PlainStats ps = pushAndRecalculate(i);

		byte[] avgBytes = ByteBuffer.allocate(4).putFloat(ps.getAvg().getNum()).array();
		String encryptedAvg;
		try {
			encryptedAvg = encSvc.encrypt(avgBytes, currentKeyId);
		} catch (EncryptionSvcException e) {
			throw new WebApplicationException(e.getMessage(), e);
		}

		byte[] sdBytes = ByteBuffer.allocate(4).putFloat(ps.getSd().getNum()).array();
		String encryptedSd;
		try {
			encryptedSd = encSvc.encrypt(sdBytes, currentKeyId);
		} catch (EncryptionSvcException e) {
			throw new WebApplicationException(e.getMessage(), e);
		}
		EncryptedFloat avg = new EncryptedFloat(encryptedAvg, currentKeyId);
		EncryptedFloat sd = new EncryptedFloat(encryptedSd, currentKeyId);
		return new EncryptedStats(avg, sd);
	}

	/**
	 * Decrypts cipherTxt value in EncryptedFloat value using the key defined by
	 * keyId.
	 * 
	 * @param s
	 *            EncryptedFloat Containing ciphertext and keyId with which the
	 *            encryption was performed.
	 * @return PlainFloat representing Plain value of the encrypted float value.
	 */
	@POST
	@Path("/decrypt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// Note: There is an ambiguity wrt signature of this API in the requirement
	// doc.
	// It says "An" encrypted Number and at the same time output of API 2. The
	// output of API2 is not a single number
	// but 2 numbers. Assuming the requirement as single number.
	public PlainFloat Decrypt(@NotNull @Valid EncryptedFloat s) {
		if (!encSvc.isKeyValid(s.getKeyId())) {
			throw new WebApplicationException("Key ID is Invalid", Status.BAD_REQUEST);
		}
		if (s.getCipherTxt().length() != encSvc.getExtraBytes(s.getKeyId()) + FLOAT_BASE64_BYTE_SIZE) {
			// Note: This check prevents encryption service from trying to
			// decrypt very large invalid input
			EncryptionSvcException e = new EncryptionSvcException();
			throw new WebApplicationException(e.getMessage(), e);
		}
		byte[] plainBytes;
		try {
			plainBytes = encSvc.decrypt(s.getCipherTxt(), s.getKeyId());
		} catch (EncryptionSvcException e) {
			throw new WebApplicationException(e.getMessage(), e);
		}
		float f = ByteBuffer.wrap(plainBytes).getFloat();
		return new PlainFloat(f);
	}
}