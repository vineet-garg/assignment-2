package crypto.webservice.api;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlainFloat {
	@NotEmpty
	private float num;
	
	public PlainFloat(){
		
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
