package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class StudentTestUpdate {
	public static void main(String[] args) {
		try {
			Student student = new Student(5, "Shirley Editado", "ascencio luyo", "shirley.ascencio@gmail.com", "983491034", "analisis de sistemas", "Tercero", "DNI", "73931448");

			CrudStudentService CrudStudentService = new CrudStudentService();
			CrudStudentService.update(student);
			System.out.println("Se Actualizo Correctamente");
		} catch (Exception e) {

		}

	}
}
