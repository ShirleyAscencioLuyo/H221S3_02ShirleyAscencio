package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class InsertStudentTest {
    public static void main(String[] args) {
        try {
            // Crear objeto de estudiante
            Student student = new Student();
            student.setNames("Shirley");
            student.setLastname("Ascencio");
            student.setEmail("shirley.ascencio@gmail.com");
            student.setCareer("Analisis de sistemas");
            student.setSemester("Tercero");
            student.setDocument_type("DNI");
            student.setDocument_number("73931448");
            student.setActive("A");

            // Instanciar el servicio de estudiantes
            CrudStudentService studentService = new CrudStudentService();

            // Insertar estudiante
            studentService.insert(student);

            // Mostrar mensaje de éxito
            System.out.println("Estudiante insertado correctamente. ID: " + student.getStudent_id());
        } catch (Exception e) {
            System.err.println("No se pudo insertar el estudiante: " + e.getMessage());
        }
    }
}
