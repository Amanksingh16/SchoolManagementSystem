package kmsg.sms.mst.model;

import java.util.List;

public class ClassFeeStudents 
{
	private int classId;
	private int studentId;
	private int age;
	private String student;
	private String enrolmentNo;
	private String name;
	private String gender;
	private String dob;
	private String section;
	private String category;
	private boolean blocked;
	private String sibling;
	private int feeHeadId;
	private int amount;
	private String label;
	private boolean refundable;
	private int lateFeeFixAmt;
	private int lateFeeAmtPerDay;
	private int lateFeePctPerDay;
	private int lateFeeLimit;
	private int collectionTypeId;
	private String collectionType;
	private String collectionDueDay;
	private String collectionDueMonth;
	private String feeFrequency;
	private String collectionFrequency;
	private int collectionEventId;
	private int discountSiblingAmt;
	private int discountSiblingPct;
	private int discountScholarAmt;
	private int discountScholarPct;
	private String discountOtherLabel;
	private int discountOtherAmt;
	private int discountOtherPct;
	private List<FeeCollections> feeCollection;
	
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getEnrolmentNo() {
		return enrolmentNo;
	}
	public void setEnrolmentNo(String enrolmentNo) {
		this.enrolmentNo = enrolmentNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSibling() {
		return sibling;
	}
	public void setSibling(String sibling) {
		this.sibling = sibling;
	}
	public int getFeeHeadId() {
		return feeHeadId;
	}
	public void setFeeHeadId(int feeHeadId) {
		this.feeHeadId = feeHeadId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isRefundable() {
		return refundable;
	}
	public void setRefundable(boolean refundable) {
		this.refundable = refundable;
	}
	public int getLateFeeFixAmt() {
		return lateFeeFixAmt;
	}
	public void setLateFeeFixAmt(int lateFeeFixAmt) {
		this.lateFeeFixAmt = lateFeeFixAmt;
	}
	public int getLateFeeAmtPerDay() {
		return lateFeeAmtPerDay;
	}
	public void setLateFeeAmtPerDay(int lateFeeAmtPerDay) {
		this.lateFeeAmtPerDay = lateFeeAmtPerDay;
	}
	public int getLateFeePctPerDay() {
		return lateFeePctPerDay;
	}
	public void setLateFeePctPerDay(int lateFeePctPerDay) {
		this.lateFeePctPerDay = lateFeePctPerDay;
	}
	public int getLateFeeLimit() {
		return lateFeeLimit;
	}
	public void setLateFeeLimit(int lateFeeLimit) {
		this.lateFeeLimit = lateFeeLimit;
	}
	public int getCollectionTypeId() {
		return collectionTypeId;
	}
	public void setCollectionTypeId(int collectionTypeId) {
		this.collectionTypeId = collectionTypeId;
	}
	public String getCollectionDueDay() {
		return collectionDueDay;
	}
	public void setCollectionDueDay(String collectionDueDay) {
		this.collectionDueDay = collectionDueDay;
	}
	public String getCollectionDueMonth() {
		return collectionDueMonth;
	}
	public void setCollectionDueMonth(String collectionDueMonth) {
		this.collectionDueMonth = collectionDueMonth;
	}
	public int getCollectionEventId() {
		return collectionEventId;
	}
	public void setCollectionEventId(int collectionEventId) {
		this.collectionEventId = collectionEventId;
	}
	public int getDiscountSiblingAmt() {
		return discountSiblingAmt;
	}
	public void setDiscountSiblingAmt(int discountSiblingAmt) {
		this.discountSiblingAmt = discountSiblingAmt;
	}
	public int getDiscountSiblingPct() {
		return discountSiblingPct;
	}
	public void setDiscountSiblingPct(int discountSiblingPct) {
		this.discountSiblingPct = discountSiblingPct;
	}
	public int getDiscountScholarAmt() {
		return discountScholarAmt;
	}
	public void setDiscountScholarAmt(int discountScholarAmt) {
		this.discountScholarAmt = discountScholarAmt;
	}
	public int getDiscountScholarPct() {
		return discountScholarPct;
	}
	public void setDiscountScholarPct(int discountScholarPct) {
		this.discountScholarPct = discountScholarPct;
	}
	public String getDiscountOtherLabel() {
		return discountOtherLabel;
	}
	public void setDiscountOtherLabel(String discountOtherLabel) {
		this.discountOtherLabel = discountOtherLabel;
	}
	public int getDiscountOtherAmt() {
		return discountOtherAmt;
	}
	public void setDiscountOtherAmt(int discountOtherAmt) {
		this.discountOtherAmt = discountOtherAmt;
	}
	public int getDiscountOtherPct() {
		return discountOtherPct;
	}
	public void setDiscountOtherPct(int discountOtherPct) {
		this.discountOtherPct = discountOtherPct;
	}
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getFeeFrequency() {
		return feeFrequency;
	}
	public void setFeeFrequency(String feeFrequency) {
		this.feeFrequency = feeFrequency;
	}
	public String getCollectionFrequency() {
		return collectionFrequency;
	}
	public void setCollectionFrequency(String collectionFrequency) {
		this.collectionFrequency = collectionFrequency;
	}
	public List<FeeCollections> getFeeCollection() {
		return feeCollection;
	}
	public void setFeeCollection(List<FeeCollections> feeCollection) {
		this.feeCollection = feeCollection;
	}
}
