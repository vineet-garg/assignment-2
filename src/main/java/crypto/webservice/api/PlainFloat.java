package crypto.webservice.api;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Currently a wrapper on float used as json input/output for APIs. More fields
 * can be added in future while keeping it backward compatible
 */
public class PlainFloat {
	@NotNull
	private Float num;

	public PlainFloat() {
		// Jackson deserialization
	}

	public PlainFloat(float num) {
		this.num = num;
	}

	/**
	 * @return float value
	 */
	@JsonProperty
	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}
}
