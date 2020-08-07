package kmsg.sms.fees.model;

import java.util.List;

public class ClassFeeHead 
{
	private int feeClassId;
	private String feeFrequency;
	private String collectionFrequency;
	private int feeHeadId;
	private String feeHeadCode;
	private String label;
	private int amount;
	private List<FeeCollections> feeCollections;
	
	public List<FeeCollections> getFeeCollections() {
		return feeCollections;
	}
	public void setFeeCollections(List<FeeCollections> feeCollections) {
		this.feeCollections = feeCollections;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getFeeClassId() {
		return feeClassId;
	}
	public void setFeeClassId(int feeClassId) {
		this.feeClassId = feeClassId;
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
	public String getFeeHeadCode() {
		return feeHeadCode;
	}
	public void setFeeHeadCode(String feeHeadCode) {
		this.feeHeadCode = feeHeadCode;
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
}
