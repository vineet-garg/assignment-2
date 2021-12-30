package crypto.webservice.api;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlainStats {
	
	@NotNull
	private PlainFloat avg;
	@NotNull
	private PlainFloat sd;
	
	public PlainStats(){
        // Jackson deserialization
	}
	
	public PlainStats(PlainFloat avg, PlainFloat sd) {
		this.avg = avg;
		this.sd = sd;
	}
	/**
	 * @return PlainFloat value of running average
	 */
	@JsonProperty
	public PlainFloat getAvg() {
		return avg;
	}
	
	/**
	 * @return PlainFloat value of running Standard Deviation
	 */
	@JsonProperty
	public PlainFloat getSd() {
		return sd;
	}
}
