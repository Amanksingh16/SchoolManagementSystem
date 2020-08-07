package kmsg.sms.mst.model;

public class QuoteSupplierItem 
{
	private int quoteSupplierItemId;
	private int quoteSupplierId;
	private int quoteId;
	private int itemId;
	private String item;
	private String unit;
	private String itemCode;
	private int qty;
	private float total;
	private float suppRate;
	private String suppDelDt;
	private String requirement1Response;
	private String requirement2Response;
	private String requirement3Response;
	private String requirement4Response;
	private String supplierRemarks;
	
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public int getQuoteSupplierItemId() {
		return quoteSupplierItemId;
	}
	public void setQuoteSupplierItemId(int quoteSupplierItemId) {
		this.quoteSupplierItemId = quoteSupplierItemId;
	}
	public int getQuoteSupplierId() {
		return quoteSupplierId;
	}
	public void setQuoteSupplierId(int quoteSupplierId) {
		this.quoteSupplierId = quoteSupplierId;
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
	public float getSuppRate() {
		return suppRate;
	}
	public void setSuppRate(float suppRate) {
		this.suppRate = suppRate;
	}
	public String getSuppDelDt() {
		return suppDelDt;
	}
	public void setSuppDelDt(String suppDelDt) {
		this.suppDelDt = suppDelDt;
	}
	public String getRequirement1Response() {
		return requirement1Response;
	}
	public void setRequirement1Response(String requirement1Response) {
		this.requirement1Response = requirement1Response;
	}
	public String getRequirement2Response() {
		return requirement2Response;
	}
	public void setRequirement2Response(String requirement2Response) {
		this.requirement2Response = requirement2Response;
	}
	public String getRequirement3Response() {
		return requirement3Response;
	}
	public void setRequirement3Response(String requirement3Response) {
		this.requirement3Response = requirement3Response;
	}
	public String getRequirement4Response() {
		return requirement4Response;
	}
	public void setRequirement4Response(String requirement4Response) {
		this.requirement4Response = requirement4Response;
	}
	public String getSupplierRemarks() {
		return supplierRemarks;
	}
	public void setSupplierRemarks(String supplierRemarks) {
		this.supplierRemarks = supplierRemarks;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
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
}
