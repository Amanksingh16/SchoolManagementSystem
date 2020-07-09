package kmsg.sms.mst.model;

public class Items {
	
	private int itemId;
	private String itemCode;
	private String item;
	private String des;
	private String unit;
	private float rate;
	private int openingBalance;
	private String openingBalanceDate;
	private String expiryDate;
	private int minQty;
	private boolean hasSubItems;
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
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
	public String getDesc() {
		return des;
	}
	public void setDesc(String des) {
		this.des = des;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public int getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(int openingBalance) {
		this.openingBalance = openingBalance;
	}
	public String getOpeningBalanceDate() {
		return openingBalanceDate;
	}
	public void setOpeningBalanceDate(String openingBalanceDate) {
		this.openingBalanceDate = openingBalanceDate;
	}
	public int getMinQty() {
		return minQty;
	}
	public void setMinQty(int minQty) {
		this.minQty = minQty;
	}
	public boolean isHasSubItems() {
		return hasSubItems;
	}
	public void setHasSubItems(boolean hasSubItems) {
		this.hasSubItems = hasSubItems;
	}
	
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

}
