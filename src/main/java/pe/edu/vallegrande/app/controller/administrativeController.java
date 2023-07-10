package pe.edu.vallegrande.app.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pe.edu.vallegrande.app.model.Administrative;
import pe.edu.vallegrande.app.service.CrudAdministrativeService;

@WebServlet({ "/AdministrativeProcesar", "/AdministrativeEliminar", "/AdministrativeBuscar","/AdministrativeActivar" })
public class administrativeController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private CrudAdministrativeService service = new CrudAdministrativeService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/AdministrativeProcesar":
//			buscar(request, response);
			procesar(request, response);
			break;
		case "/AdministrativeEliminar":
			eliminar(request, response);
			break;
		case "/AdministrativeBuscar":
			buscar(request, response);
			break;
		case "/AdministrativeActivar":
			activar(request, response);
			break;
		}
	}

	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		// Datos
		String accion = request.getParameter("accion");
		Administrative bean = new Administrative();
		
		if(request.getParameter("id") != ControllerUtil.STRING_EMPTY) {
			bean.setId(Integer.parseInt(request.getParameter("id")));
		}

		bean.setDocumentType(request.getParameter("document_type"));
		bean.setDocumentNumber(request.getParameter("document_number"));
		bean.setNames(request.getParameter("names"));
		bean.setLastnames(request.getParameter("lastnames"));
		bean.setEmail(request.getParameter("email"));
		bean.setCellphone(request.getParameter("cellphone"));
		bean.setTypeCharge(request.getParameter("type_charge"));
		// proceso
		try {
			switch (accion) {
			case ControllerUtil.CRUD_NUEVO:
				service.insert(bean);
			case ControllerUtil.CRUD_EDITAR:
				service.update(bean);	
				break;
			}
			ControllerUtil.responseJson(response, "Administrador procesado con exito");
		} catch (Exception e) {
			ControllerUtil.responseJson(response, e.getMessage());
		}

	}
	
	private void buscar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Datos		
		String active = "A";
		if(request.getParameter("active") != ControllerUtil.STRING_EMPTY) {
			active = request.getParameter("active");
		}
		String filter = request.getParameter("filter");
		String documentType = request.getParameter("documentType");
		
		List<Administrative> listar = service.getByFilter(active, filter, documentType);
		// preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(listar);
		// reporte
		ControllerUtil.responseJson(response, data);

	}
	
	private void eliminar(HttpServletRequest request, HttpServletResponse response) {

		Integer AdministrativeId = Integer.parseInt(request.getParameter("id"));
		
		service.delete(AdministrativeId);
	}
	private void activar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer ActivaradministrativeId = Integer.parseInt(request.getParameter("id"));
		service.active(ActivaradministrativeId);
	}
}
