package crypto.webservice.services;

/**
 * Service that provides encryption and decryption of data. Data is treated as
 * an opaque value of any size.
 */
public interface EncryptionSvc {

	/**
	 * Encrypt data using the specified key
	 * 
	 * @param b
	 *            byte[] of input data
	 * @param keyId
	 *            String value that specifies the key
	 * @return String value of base64 encoded ciphertext
	 * @throws EncryptionSvcException
	 *             General Exception without underlying cause.
	 */
	public String encrypt(byte[] b, String keyId) throws EncryptionSvcException;

	/**
	 * Decrypt data using the specified key.
	 * 
	 * @param ciphertext
	 *            String value of base64 encoded ciphertext. It should be the
	 *            same value as returned during Encrypt operation.
	 * @param keyId
	 *            String value that specifies the key.
	 * @return byte[] value of decrypted data.
	 * @throws EncryptionSvcException
	 *             General Exception without underlying cause.
	 */
	public byte[] decrypt(String ciphertext, String keyId) throws EncryptionSvcException;

	/**
	 * Returns count of extra bytes of data a particular encryption, identified by keyId, adds.
	 * Clients should use this method to validate the input before invoking decrypt.
	 * 
	 * @param keyId
	 *            String value that specifies the key.
	 * @return Value of number of extra bytes.
	 */
	public int getExtraBytes(String keyId);

	/**
	 * Checks if key is valid.
	 * 
	 * @param keyId
	 * @return true/false depending of whether the keyId is valid.
	 */
	public boolean isKeyValid(String keyId);
}