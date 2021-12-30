package crypto.webservice.api;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptedStats {
	@NotNull
	private EncryptedFloat avg ;
	
	@NotNull
	private EncryptedFloat sd;
	
	public EncryptedStats() {
        // Jackson deserialization
	}
	
	public EncryptedStats(EncryptedFloat avg, EncryptedFloat sd) {
		this.avg = avg;
		this.sd = sd;
	}
	
	@JsonProperty
	public EncryptedFloat getAvg() {
		return this.avg;
	}
	
	@JsonProperty
	public EncryptedFloat getSd(){
		return this.sd;
	}
}
