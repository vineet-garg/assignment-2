package crypto.webservice.services;

import java.security.GeneralSecurityException;

/**
 * Generic Exception from EncryptionSvc The underlying cause is not returned but
 * just logged in server logs.
 */
public class EncryptionSvcException extends GeneralSecurityException {
	private static final long serialVersionUID = 1L;
	private static final String MSG = "Error While Encrypting/Decrypting: Check your Input Or Server logs. "
			+ "Exact cause cannot be returned to maintain security of the service";

	public EncryptionSvcException() {
		super(MSG);
	}
}