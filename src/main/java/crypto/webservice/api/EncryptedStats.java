package crypto.webservice.api;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptedStats {
	@NotEmpty
	private EncryptedFloat avg ;
	
	@NotEmpty
	private EncryptedFloat sd;
	
	public EncryptedStats(){
		
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
