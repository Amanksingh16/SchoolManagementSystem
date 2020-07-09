package kmsg.sms.mst.model;

public class StateModel {

	private int stateId;
	private String state;
	
	public StateModel(int stateId, String state) {
		super();
		this.stateId = stateId;
		this.state = state;
	}
	
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
