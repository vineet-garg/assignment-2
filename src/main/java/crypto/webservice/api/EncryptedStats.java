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
	
	/**
	 * @return EncryptedFloat value of running average
	 */
	@JsonProperty
	public EncryptedFloat getAvg() {
		return this.avg;
	}
	
	/**
	 * @return EncryptedFloat value of running Standard Deviation
	 */
	@JsonProperty
	public EncryptedFloat getSd(){
		return this.sd;
	}
}
