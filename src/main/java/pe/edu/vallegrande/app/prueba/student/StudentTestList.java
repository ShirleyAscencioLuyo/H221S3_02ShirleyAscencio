package pe.edu.vallegrande.app.prueba.student;

import java.util.List;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;



public class StudentTestList {
    public static void main(String[] args) {
        try {
        	CrudStudentService studentService = new CrudStudentService();
            List<Student> lista = studentService.getAll();
            if (!lista.isEmpty()) {
                System.out.println("Lista de estudiantes:");
                for (Student student : lista) {
                    System.out.println(student);
                }
            } else {
                System.out.println("No se encontraron estudiantes.");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de estudiantes: " + e.getMessage());
        }
    }
}
