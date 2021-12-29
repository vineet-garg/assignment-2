package crypto.webservice.api;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Currently a wrapper on float
 * Used as json input/output for APIs
 * More fields cann be added in future while keeping it backward compatible
 */
public class PlainFloat {
	@NotEmpty
	private float num;
	
	public PlainFloat(){
        // Jackson deserialization
	}
	public PlainFloat(float num) {
		this.num = num;
	}
	@JsonProperty
	public float getNum(){
		return num;
	}
	
	public void setNum(float num) {
		this.num = num;
	}
}
