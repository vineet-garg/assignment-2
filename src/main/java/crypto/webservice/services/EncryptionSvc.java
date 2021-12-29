package crypto.webservice.services;

/**
 * Service that provides encryption and decryption of data. 
 * Data is treated as an opaqu value of any size.
 */
public interface EncryptionSvc {
	
	/**
	 * Encrypt data using the specified key
	 * @param b byte[] of input data
	 * @param keyId String value that specifies the key
	 * @return String value of base64 encoded ciphertext 
	 */
	public String encrypt(byte[] b, String keyId);
	
	/**
	 * Decrypt data using the specified key.
	 * @param ciphertext String value of base64 encoded ciphertext. 
	 * It should be the same value as returned during Encrypt operation.
	 * @param keyId String value that specifies the key.
	 * @return byte[] value of decrypted data.
	 */
	public byte[] decrypt(String ciphertext, String keyId);
    
	/**
     * Returns Extra bytes of data a particular encryption identified by keyId adds.
     * Clients should use this method to validate the input before invoking decrypt.
     * @param keyId String value that specifies the key.
     * @return
     */
    public int getExtraBytes(String keyId);
}