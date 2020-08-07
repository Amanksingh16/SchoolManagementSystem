package kmsg.sms.mst.model;

public class SubItem {
	
	private int subItemId;
	private int itemId;
	private String item;
	private int childItemId;
	private String itemCode;
	private int qty;
	private float rate;
	private String unit;
	
	public int getSubItemId() {
		return subItemId;
	}
	public void setSubItemId(int subItemId) {
		this.subItemId = subItemId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getChildItemId() {
		return childItemId;
	}
	public void setChildItemId(int childItemId) {
		this.childItemId = childItemId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
