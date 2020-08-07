package kmsg.sms.mst.model;

public class QuoteSupplier 
{
	private int quoteSupplierId;
	private int quoteId;
	private int supplierId;
	private String sentDt;
	private String supplierCode;
	private String supplier;
	private String businessName;
	private String suppResponse;
	private String supplierResponseDt;
	private String supplierRemarks;
	
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
	public int getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}
	public String getSentDt() {
		return sentDt;
	}
	public void setSentDt(String sentDt) {
		this.sentDt = sentDt;
	}
	public String getSuppResponse() {
		return suppResponse;
	}
	public void setSuppResponse(String suppResponse) {
		this.suppResponse = suppResponse;
	}
	public String getSupplierResponseDt() {
		return supplierResponseDt;
	}
	public void setSupplierResponseDt(String supplierResponseDt) {
		this.supplierResponseDt = supplierResponseDt;
	}
	public String getSupplierRemarks() {
		return supplierRemarks;
	}
	public void setSupplierRemarks(String supplierRemarks) {
		this.supplierRemarks = supplierRemarks;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
}
