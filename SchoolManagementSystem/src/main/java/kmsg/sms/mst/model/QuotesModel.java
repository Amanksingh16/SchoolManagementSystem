package kmsg.sms.mst.model;

public class QuotesModel 
{
	private int quoteId;
	private int poId;
	private String quoteNo;
	private String quoteDt;
	private String quoteExpiryDt;
	private String expDelDt;
	private int sentCount;
	private int responseCount;
	
	public int getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(int quoteId) {
		this.quoteId = quoteId;
	}
	public int getPoId() {
		return poId;
	}
	public void setPoId(int poId) {
		this.poId = poId;
	}
	public String getQuoteNo() {
		return quoteNo;
	}
	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}
	public String getQuoteDt() {
		return quoteDt;
	}
	public void setQuoteDt(String quoteDt) {
		this.quoteDt = quoteDt;
	}
	public String getQuoteExpiryDt() {
		return quoteExpiryDt;
	}
	public void setQuoteExpiryDt(String quoteExpiryDt) {
		this.quoteExpiryDt = quoteExpiryDt;
	}
	public String getExpDelDt() {
		return expDelDt;
	}
	public void setExpDelDt(String expDelDt) {
		this.expDelDt = expDelDt;
	}
	public int getSentCount() {
		return sentCount;
	}
	public void setSentCount(int sentCount) {
		this.sentCount = sentCount;
	}
	public int getResponseCount() {
		return responseCount;
	}
	public void setResponseCount(int responseCount) {
		this.responseCount = responseCount;
	}
}
