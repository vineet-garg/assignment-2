package crypto.webservice.api;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptedFloat {
	//encrypted float along with algo, keyID and iv encoded in the same string
	@NotEmpty
	private String cipherTxt;
	
	@NotEmpty
	public EncryptedFloat(){
		
	}
	
	public EncryptedFloat(String cipherTxt) {
		this.cipherTxt = cipherTxt;
	}

	@JsonProperty
	public String getCipherTxt() {
		return cipherTxt;
	}
	
	@JsonProperty
	public void setCipherTxt(String cipherTxt) {
		this.cipherTxt = cipherTxt;
	}
}
