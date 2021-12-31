package crypto.webservice;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class ServerConfiguration extends Configuration {

	/**
	 * Hexadecimal encoded value of 256 bit AES key
	 */
	@NotEmpty
	private String key;

	@NotEmpty
	private String keyId;

	/**
	 * Value of keyId, eg. "0"
	 */
	@JsonProperty
	public String getKeyId() {
		return keyId;
	}

	/**
	 * Value of keyId, eg. "0"
	 */
	@JsonProperty
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	/**
	 * Hexadecimal encoded value of 256 bit AES key
	 */
	@JsonProperty
	public String getKey() {
		return key;
	}

	/**
	 * Hexadecimal encoded value of 256 bit AES key
	 */
	@JsonProperty
	public void setKey(String key) {
		this.key = key;
	}
}
