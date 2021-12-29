package crypto.webservice.api;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Currently a wrapper on int
 * Used as json input/output for APIs
 * More fields cann be added in future while keeping it backward compatible
 */
public class PlainInt {
	@NotEmpty
	private int num; 
	
	public PlainInt(){
        // Jackson deserialization
	}
	
	@JsonProperty
	public int getNum() {
		return num;
	}
	
	@JsonProperty
	public void setNum(int num){
		this.num = num;
	}
}
