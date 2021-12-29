package crypto.webservice.api;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptedFloat {
	/**
	 * encrypted float and iv encoded in the same string
	 */
	@NotEmpty
	private String cipherTxt;
	
	/**
	 * KeyID with which the encryption was done.
	 * Note: This will be useful while doing a phased rotation of keys 
	 * or moving to a new algorithm.
	 */
	@NotEmpty
	private String keyId;

	
	public EncryptedFloat(){
        // Jackson deserialization

	}
	
	public EncryptedFloat(String cipherTxt, String keyId) {
		this.cipherTxt = cipherTxt;
		this.keyId = keyId;
	}

	@JsonProperty
	public String getCipherTxt() {
		return cipherTxt;
	}
	
	@JsonProperty
	public void setCipherTxt(String cipherTxt) {
		this.cipherTxt = cipherTxt;
	}
	
	@JsonProperty
	public String getKeyId() {
		return keyId;
	}

	@JsonProperty
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
}
