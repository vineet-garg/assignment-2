package crypto.webservice.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlainStats {
	
	private PlainFloat avg;
	private PlainFloat sd;
	
	public PlainStats(){
        // Jackson deserialization
	}
	
	public PlainStats(PlainFloat avg, PlainFloat sd) {
		this.avg = avg;
		this.sd = sd;
	}
	@JsonProperty
	public PlainFloat getAvg() {
		return avg;
	}
	
	@JsonProperty
	public PlainFloat getSd() {
		return sd;
	}
}
