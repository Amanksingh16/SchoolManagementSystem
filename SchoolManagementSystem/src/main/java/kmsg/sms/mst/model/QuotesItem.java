package kmsg.sms.mst.model;

public class QuotesItem 
{
	private int quoteItemId;
	private int quoteId;
	private int itemId;
	private int qty;
	private String unit;
	private String itemCode;
	private String item;
	private float expRate;
	private String requirement1;
	private String requirement2;
	private String requirement3;
	private String requirement4;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getQuoteItemId() {
		return quoteItemId;
	}
	public void setQuoteItemId(int quoteItemId) {
		this.quoteItemId = quoteItemId;
	}
	public int getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(int quoteId) {
		this.quoteId = quoteId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public float getExpRate() {
		return expRate;
	}
	public void setExpRate(float expRate) {
		this.expRate = expRate;
	}
	public String getRequirement1() {
		return requirement1;
	}
	public void setRequirement1(String requirement1) {
		this.requirement1 = requirement1;
	}
	public String getRequirement2() {
		return requirement2;
	}
	public void setRequirement2(String requirement2) {
		this.requirement2 = requirement2;
	}
	public String getRequirement3() {
		return requirement3;
	}
	public void setRequirement3(String requirement3) {
		this.requirement3 = requirement3;
	}
	public String getRequirement4() {
		return requirement4;
	}
	public void setRequirement4(String requirement4) {
		this.requirement4 = requirement4;
	}
}
