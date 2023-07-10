package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class StudentTestCreate {
		
	public static void main(String[] args) {
		try {
			Student student = new Student(6, "tracy", "sandoval", "tracy.sandoval@gmail.com","934273913", "Análisis de Sistemas", "Tercero", "DNI", "35273947");
			
			CrudStudentService crudStudentService = new CrudStudentService();
			crudStudentService.insert(student);
			System.out.println("Se Creo correctamente");
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
