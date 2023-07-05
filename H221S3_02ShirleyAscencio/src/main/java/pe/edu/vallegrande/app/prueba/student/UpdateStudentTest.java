package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class UpdateStudentTest {
    public static void main(String[] args) {
        try {
            // Crear objeto de estudiante
            Student student = new Student();
            student.setStudent_id(6); // ID del estudiante a actualizar
            student.setNames("Shirley Editado");
            student.setLastname("Ascencio");
            student.setEmail("shirley.ascencio@gmail.com");
            student.setCareer("Analisis de sistemas");
            student.setSemester("Tercero");
            student.setDocument_type("DNI");
            student.setDocument_number("73931448");
            student.setActive("A");

            // Instanciar el servicio de estudiantes
            CrudStudentService studentService = new CrudStudentService();

            // Actualizar estudiante
            studentService.update(student);

            // Mostrar mensaje de éxito
            System.out.println("Estudiante actualizado correctamente.");
        } catch (Exception e) {
            System.err.println("No se pudo actualizar el estudiante: " + e.getMessage());
        }
    }
}
