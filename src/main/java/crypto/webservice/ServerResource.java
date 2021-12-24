package crypto.webservice;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import crypto.webservice.api.EncryptedOutput;
import crypto.webservice.api.PlainInput;
import crypto.webservice.api.PlainOutput;

@Path("")
public class ServerResource {
	
	@POST
	@Path("/push-and-recalculate")
	public PlainOutput pushAndRecalculate(PlainInput i) {
		return new PlainOutput();
		
	}
	
	@POST
	@Path("/push-and-recalculate-encrypt")
	public EncryptedOutput pushRecalculateAndEncrypt(PlainInput i) {
		return new EncryptedOutput();
		
	}
	
	@POST
	@Path("/decrypt")
	public int Decrypt(PlainInput i) {
		return 0;
		
	}
}
