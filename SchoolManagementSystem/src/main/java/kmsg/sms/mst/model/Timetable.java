package kmsg.sms.mst.model;

import java.util.List;

public class Timetable 
{
	private int wingId;
	private int classSectionId;
	private int timetableId;
	private String timetable;
	private boolean published;
	private List<TimetablePeriods> listTimetablePeriods;
	
	public String getTimetable() {
		return timetable;
	}
	public void setTimetable(String timetable) {
		this.timetable = timetable;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public int getWingId() {
		return wingId;
	}
	public void setWingId(int wingId) {
		this.wingId = wingId;
	}
	public int getClassSectionId() {
		return classSectionId;
	}
	public void setClassSectionId(int classSectionId) {
		this.classSectionId = classSectionId;
	}
	public int getTimetableId() {
		return timetableId;
	}
	public void setTimetableId(int timetableId) {
		this.timetableId = timetableId;
	}
	public List<TimetablePeriods> getListTimetablePeriods() {
		return listTimetablePeriods;
	}
	public void setListTimetablePeriods(List<TimetablePeriods> listTimetablePeriods) {
		this.listTimetablePeriods = listTimetablePeriods;
	}
}
