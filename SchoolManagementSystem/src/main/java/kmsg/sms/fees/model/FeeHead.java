package kmsg.sms.fees.model;

import java.util.List;

public class FeeHead {

	private int feeHeadId;
	private String feeHeadCode;
	private String label;
	private String feeFrequency;
	private String collectionFrequency;
	private int defaultAmt;
	private boolean refundable;
	private int lateFeeFixAmt;
	private int lateFeeAmtPerDay;	
	private int lateFeePctPerDay;
	private int lateFeeLimit;
	private int collectionTypeId;
	private String collectionType;
	private String collectionDueDay;
	private String collectionDueMonth;
	private int collectionEventId;
	private int discountSiblingAmt;
	private int discountSiblingPct;
	private int discountScholarAmt;
	private int discountScholarPct;
	private String discountOtherLabel;
	private int discountOtherAmt;
	private int discountOtherPct;
	private List<FeeCollections> lstCollection;
	
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	public int getFeeHeadId() {
		return feeHeadId;
	}
	public void setFeeHeadId(int feeHeadId) {
		this.feeHeadId = feeHeadId;
	}
	public String getFeeHeadCode() {
		return feeHeadCode;
	}
	public void setFeeHeadCode(String feeHeadCode) {
		this.feeHeadCode = feeHeadCode;
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
	public List<FeeCollections> getLstCollection() {
		return lstCollection;
	}
	public void setLstCollection(List<FeeCollections> lstCollection) {
		this.lstCollection = lstCollection;
	}
	public int getDefaultAmt() {
		return defaultAmt;
	}
	public void setDefaultAmt(int defaultAmt) {
		this.defaultAmt = defaultAmt;
	}
}
