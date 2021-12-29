package crypto.webservice;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class ServerConfiguration extends Configuration {
	
	/**
	 * Hexadecimal excoded value of 256 bit AES key
	 */
	@NotEmpty
	
    private String key;
	
	/**
	 * Value of keyId, only "0" supported currently
	 */
	@NotEmpty 
	private String keyId;

	@JsonProperty
	public String getKeyId() {
		return keyId;
	}

	@JsonProperty
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	@JsonProperty
	public String getKey() {
		return key;
	}

	@JsonProperty
	public void setKey(String key) {
		this.key = key;
	}
}
