package kmsg.sms.mst.model;

public class DocTypeModel {
	private int docTypeId;
	private String docType;
	public int getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(int docTypeId) {
		this.docTypeId = docTypeId;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public DocTypeModel(int docTypeId, String docType) {
		super();
		this.docTypeId = docTypeId;
		this.docType = docType;
	}
	
	
}
