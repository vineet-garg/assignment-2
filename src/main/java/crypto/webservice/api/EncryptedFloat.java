package crypto.webservice.api;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptedFloat {
	//encrypted float and iv encoded in the same string
	@NotEmpty
	private String cipherTxt;
	
	// This will be useful while doing a phased rotation of keys.
	@NotEmpty
	private String keyId;

	@NotEmpty
	public EncryptedFloat(){
		
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
