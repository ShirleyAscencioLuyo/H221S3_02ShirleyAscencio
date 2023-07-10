package pe.edu.vallegrande.app.prueba.administrative;


import pe.edu.vallegrande.app.service.CrudAdministrativeService;

public class AdministrativeTestDelete {
	public static void main(String[] args) {
		try {
			 Integer administrativeId = 18;
			 
			 CrudAdministrativeService crudAdministrativeService = new CrudAdministrativeService();
			 crudAdministrativeService.delete(administrativeId);
			 System.err.println("Elimino correctamente");
		 }catch(Exception e){
			 System.err.println("error al eliminar");
		 }
	}
	 
}
