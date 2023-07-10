package pe.edu.vallegrande.app.prueba.administrative;

import pe.edu.vallegrande.app.model.Administrative;
import pe.edu.vallegrande.app.service.CrudAdministrativeService;

public class AdministrativeTestCreate {
		
	public static void main(String[] args) {
		try {
			Administrative administrative = new Administrative(20, "DNI", "84759364", "Julio", "ocares matos","julio.ocares@vallegrande.edu.pe","954648364", "Tesorero");
			
			CrudAdministrativeService crudAdministrativeService = new CrudAdministrativeService();
			crudAdministrativeService.insert(administrative);
			System.out.println("Se Creo correctamente");
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
