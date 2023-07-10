package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.service.CrudStudentService;

public class StudentTestDelete {
	public static void main(String[] args) {
		try {
			 Integer studentId = 5;
			 
			 CrudStudentService crudStudentService = new CrudStudentService();
			 crudStudentService.delete(studentId);
			 System.err.println("Elimino correctamente");
		 }catch(Exception e){
			 System.err.println("error al eliminar");
		 }
	}
	 
}
