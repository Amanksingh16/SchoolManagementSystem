package kmsg.sms.enquiry.model;

import kmsg.sms.mst.model.Classes;

public class Enquiry {
	private int enquiryId;
	private String enquiryNo;
	private String enquiry ;
	private String gender ;
	private int age;
	private String contactNo;
	private String eMail;
	private int sourceId;
	private String source ;
	private Classes classes ;
	private int assignedToId;
	private String assignedTo;
	private String assignedOn;
	private int statusId;
	private String status;
	private String query;
	private String regsNo;
	
	public int getEnquiryId() {
		return enquiryId;
	}
	public void setEnquiryId(int enquiryId) {
		this.enquiryId = enquiryId;
	}
	public String getEnquiryNo() {
		return enquiryNo;
	}
	public void setEnquiryNo(String enquiryNo) {
		this.enquiryNo = enquiryNo;
	}
	public String getEnquiry() {
		return enquiry;
	}
	public void setEnquiry(String enquiry) {
		this.enquiry = enquiry;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public int getAssignedToId() {
		return assignedToId;
	}
	public void setAssignedToId(int assignedToId) {
		this.assignedToId = assignedToId;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getAssignedOn() {
		return assignedOn;
	}
	public void setAssignedOn(String assignedOn) {
		this.assignedOn = assignedOn;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getRegsNo() {
		return regsNo;
	}
	public void setRegsNo(String regsNo) {
		this.regsNo = regsNo;
	}

}
