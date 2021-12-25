package crypto.webservice;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class ServerConfiguration extends Configuration {
	@NotEmpty
    private String key;

	@JsonProperty
	public String getKey() {
		return key;
	}

	@JsonProperty
	public void setKey(String key) {
		this.key = key;
	}
}
