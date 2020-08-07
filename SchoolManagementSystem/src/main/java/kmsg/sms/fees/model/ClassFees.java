package kmsg.sms.fees.model;

import java.util.List;

public class ClassFees 
{
	private int classId;
	private String className;
	private int totalFees;
	private List<ClassFeeHead> classFeeHead;
	
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public List<ClassFeeHead> getClassFeeHead() {
		return classFeeHead;
	}
	public void setClassFeeHead(List<ClassFeeHead> classFeeHead) {
		this.classFeeHead = classFeeHead;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getTotalFees() {
		return totalFees;
	}
	public void setTotalFees(int totalFees) {
		this.totalFees = totalFees;
	}
}
