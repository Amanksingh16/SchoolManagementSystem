package kmsg.sms.student.model;

public class StudentDtls {
	private Personal 			personal ;
	private StudentHistory 		history;
	private StudentMedicalDtls 	medical;

	public Personal getPersonal() {
		return personal;
	}
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}
	public StudentHistory getHistory() {
		return history;
	}
	public void setHistory(StudentHistory history) {
		this.history = history;
	}
	public StudentMedicalDtls getMedical() {
		return medical;
	}
	public void setMedical(StudentMedicalDtls medical ) {
		this.medical= medical;
	}

}
