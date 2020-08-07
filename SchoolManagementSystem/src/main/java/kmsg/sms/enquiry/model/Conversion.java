package kmsg.sms.enquiry.model;

import java.util.List;

public class Conversion 
{
	private List<Integer> weekLabels;
	private List<Integer> enquiryData;
	private List<Integer> enquiryRegister;
	
	public List<Integer> getWeekLabels() {
		return weekLabels;
	}
	public void setWeekLabels(List<Integer> weekLabels) {
		this.weekLabels = weekLabels;
	}
	public List<Integer> getEnquiryData() {
		return enquiryData;
	}
	public void setEnquiryData(List<Integer> enquiryData) {
		this.enquiryData = enquiryData;
	}
	public List<Integer> getEnquiryRegister() {
		return enquiryRegister;
	}
	public void setEnquiryRegister(List<Integer> enquiryRegister) {
		this.enquiryRegister = enquiryRegister;
	}
}
