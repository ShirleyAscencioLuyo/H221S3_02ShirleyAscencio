package pe.edu.vallegrande.app.model;

public class Student {
	private Integer id;
	private String names;
	private String lastname;
	private String email;
	private String cellphone;
	private String career;
	private String semester;
	private String document_type;
	private String document_number;
	private String active;

	public Student() {

	}
	public Student(Integer id, String names, String lastname, String email,String cellphone,String career,String semester, String document_type,
			   String document_number) {
	super();
	this.id = id;
	this.names = names;
	this.lastname = lastname;
	this.email = email;
	this.cellphone = cellphone;
	this.career = career;
	this.semester = semester;
	this.document_type = document_type;
	this.document_number = document_number;
}



public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getNames() {
	return names;
}

public void setNames(String names) {
	this.names = names;
}

public String getLastname() {
	return lastname;
}


public void setLastname(String lastname) {
	this.lastname = lastname;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public void setCellphone(String cellphone) {
	this.cellphone = cellphone;
}

public String getCellphone() {
	return cellphone;
}

public String getCareer() {
	return career;
}


public void setCareer(String career) {
	this.career = career;
}

public String getSemester() {
	return semester;
}


public void setSemester(String semester) {
	this.semester = semester;
}

public String getDocument_type() {
	return document_type;
}


public void setDocument_type(String document_type) {
	this.document_type = document_type;
}


public String getDocument_number() {
	return document_number;
}


public void setDocument_number(String document_number) {
	this.document_number = document_number;
}


public String getActive() {
	return active;
}


public void setActive(String active) {
	this.active = active;
}
@Override
public String toString() {
	return "student [id=" + id + ", names=" + names + ", lastname=" + lastname
			+ ", email=" + email + ", cellphone=" + cellphone + ", career=" + career + ", semester=" + semester + ", document_type="
			+ document_type + ", document_number=" + document_number + "]";
}
}
