package crypto.webservice.api;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptedFloat {

	@NotEmpty
	private String cipherTxt;

	@NotEmpty
	private String keyId;

	public EncryptedFloat() {
		// Jackson deserialization
	}

	public EncryptedFloat(String cipherTxt, String keyId) {
		this.cipherTxt = cipherTxt;
		this.keyId = keyId;
	}

	/**
	 * @return encrypted float and iv encoded in the same string
	 */
	@JsonProperty
	public String getCipherTxt() {
		return cipherTxt;
	}

	@JsonProperty
	public void setCipherTxt(String cipherTxt) {
		this.cipherTxt = cipherTxt;
	}

	/**
	 * KeyID with which the encryption was done. Note: This will be useful while
	 * doing a phased rotation of keys or moving to a new algorithm.
	 * 
	 * @return
	 */
	@JsonProperty
	public String getKeyId() {
		return keyId;
	}

	@JsonProperty
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
}
