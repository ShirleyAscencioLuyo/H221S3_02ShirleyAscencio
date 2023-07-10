package pe.edu.vallegrande.app.prueba.administrative;

import java.util.List;

import pe.edu.vallegrande.app.model.Administrative;
import pe.edu.vallegrande.app.service.CrudAdministrativeService;

public class AdministrativeTestList {
    public static void main(String[] args) {
        try {
            CrudAdministrativeService administrativeService = new CrudAdministrativeService();
            List<Administrative> lista = administrativeService.getAll();
            if (!lista.isEmpty()) {
                System.out.println("Lista de administradores:");
                for (Administrative administrative : lista) {
                    System.out.println(administrative);
                }
            } else {
                System.out.println("No se encontraron administradores.");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de profesores: " + e.getMessage());
        }
    }
}
