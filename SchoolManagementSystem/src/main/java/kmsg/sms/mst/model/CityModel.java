package kmsg.sms.mst.model;

public class CityModel {
	private int cityId;
	private int stateId;
	private String city;
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public CityModel(int cityId, int stateId, String city) {
		super();
		this.cityId = cityId;
		this.stateId = stateId;
		this.city = city;
	}
	
}
