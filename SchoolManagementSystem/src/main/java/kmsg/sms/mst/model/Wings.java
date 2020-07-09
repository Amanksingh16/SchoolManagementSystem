package kmsg.sms.mst.model;

public class Wings 
{
	private int wingId;
	private String wing;
	private int fromClassId;
	private int toClassId;
	private String fromClass;
	private String toClass;
	private String day;
	private boolean mon;
	private boolean tue;
	private boolean wed;
	private boolean thu;
	private boolean fri;
	private boolean sat;
	private boolean sun;
	
	public int getWingId() {
		return wingId;
	}
	public void setWingId(int wingId) {
		this.wingId = wingId;
	}
	public String getWing() {
		return wing;
	}
	public void setWing(String wing) {
		this.wing = wing;
	}
	public int getFromClassId() {
		return fromClassId;
	}
	public void setFromClassId(int fromClassId) {
		this.fromClassId = fromClassId;
	}
	public int getToClassId() {
		return toClassId;
	}
	public void setToClassId(int toClassId) {
		this.toClassId = toClassId;
	}
	public String getFromClass() {
		return fromClass;
	}
	public void setFromClass(String fromClass) {
		this.fromClass = fromClass;
	}
	public String getToClass() {
		return toClass;
	}
	public void setToClass(String toClass) {
		this.toClass = toClass;
	}
	public boolean isMon() {
		return mon;
	}
	public void setMon(boolean mon) {
		this.mon = mon;
	}
	public boolean isTue() {
		return tue;
	}
	public void setTue(boolean tue) {
		this.tue = tue;
	}
	public boolean isWed() {
		return wed;
	}
	public void setWed(boolean wed) {
		this.wed = wed;
	}
	public boolean isThu() {
		return thu;
	}
	public void setThu(boolean thu) {
		this.thu = thu;
	}
	public boolean isFri() {
		return fri;
	}
	public void setFri(boolean fri) {
		this.fri = fri;
	}
	public boolean isSat() {
		return sat;
	}
	public void setSat(boolean sat) {
		this.sat = sat;
	}
	public boolean isSun() {
		return sun;
	}
	public void setSun(boolean sun) {
		this.sun = sun;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
}
