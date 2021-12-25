package crypto.webservice.api;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlainInt {
	@NotEmpty
	private int num; 
	
	public PlainInt(){
		
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
