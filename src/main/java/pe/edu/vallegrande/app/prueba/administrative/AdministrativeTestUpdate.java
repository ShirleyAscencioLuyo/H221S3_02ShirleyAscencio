package pe.edu.vallegrande.app.prueba.administrative;

import pe.edu.vallegrande.app.model.Administrative;
import pe.edu.vallegrande.app.service.CrudAdministrativeService;

public class AdministrativeTestUpdate {
	public static void main(String[] args) {
		try {
			Administrative administrative = new Administrative(19, "DNI", "22345678", "Marleny shirley", "Luyo Vicente","marleny.luyo@gmail.com","934527362", "Tesorero");

			CrudAdministrativeService CcrudAdministrativeService = new CrudAdministrativeService();
			CcrudAdministrativeService.update(administrative);
			System.out.println("Se Actualizo Correctamente");
		} catch (Exception e) {

		}

	}
}
