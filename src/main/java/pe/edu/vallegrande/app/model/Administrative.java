package pe.edu.vallegrande.app.model;

public class Administrative {

	private Integer id;
	private String documentType;
	private String documentNumber;
	private String names;
	private String lastnames;
	private String email;
	private String cellphone;
	private String typeCharge;
	private String active;

	public Administrative() {

	}

	public Administrative(Integer id, String documentType, String documentNumber, String names, String lastnames, String email, String cellphone,String typeCharge) {
		super();
		this.id = id;
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.names = names;
		this.lastnames = lastnames;
		this.email = email;
		this.cellphone = cellphone;
		this.typeCharge = typeCharge;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getlastnames() {
		return lastnames;
	}

	public void setLastnames(String lastnames) {
		this.lastnames = lastnames;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getTypeCharge() {
		return typeCharge;
	}

	public void setTypeCharge(String typeCharge) {
		this.typeCharge = typeCharge;
	}

	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", documentType=" + documentType + ", documentNumber=" + documentNumber
				+ ", names=" + names + ", surnames=" + lastnames + ",email=" + email + ",cellphone=" + cellphone + " typeCharge="
				+ typeCharge + "]";
	}

	
	
}

