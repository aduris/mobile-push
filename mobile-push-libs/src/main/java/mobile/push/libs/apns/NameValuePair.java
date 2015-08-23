package mobile.push.libs.apns;

/**
 * NameValuePair
 * @since	2015-07-01
 * @author 	최일규
 */
public class NameValuePair {
	public NameValuePair(String _name, String _value) {
		name = _name;
		value = _value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private String name;
	private String value;

}
