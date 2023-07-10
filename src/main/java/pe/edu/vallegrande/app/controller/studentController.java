package pe.edu.vallegrande.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

@WebServlet({ "/StudentProcesar", "/StudentEliminar", "/StudentBuscar","/StudentActivar" })
public class studentController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private CrudStudentService service = new CrudStudentService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/StudentProcesar":
//			buscar(request, response);
			procesar(request, response);
			break;
		case "/StudentEliminar":
			eliminar(request, response);
			break;
		case "/StudentBuscar":
			buscar(request, response);
			break;
		case "/StudentActivar":
			activar(request, response);
			break;
		}
	}

	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		// Datos
		String accion = request.getParameter("accion");
		Student bean = new Student();
		
		if(request.getParameter("id") != ControllerUtil.STRING_EMPTY) {
			bean.setId(Integer.parseInt(request.getParameter("id")));
		}

		bean.setNames(request.getParameter("names"));
		bean.setLastname(request.getParameter("lastname"));
		bean.setEmail(request.getParameter("email"));
		bean.setCellphone(request.getParameter("cellphone"));
		bean.setCareer(request.getParameter("career"));
		bean.setSemester(request.getParameter("semester"));
		bean.setDocument_type(request.getParameter("document_type"));
		bean.setDocument_number(request.getParameter("document_number"));
		// proceso
		try {
			switch (accion) {
			case ControllerUtil.CRUD_NUEVO:
				service.insert(bean);
			case ControllerUtil.CRUD_EDITAR:
				service.update(bean);	
				break;
			}
			ControllerUtil.responseJson(response, "Estudiante procesado con exito");
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
		String document_type = request.getParameter("document_type");
		
		List<Student> listar = service.getByFilter(active, filter, document_type);
		// preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(listar);
		// reporte
		ControllerUtil.responseJson(response, data);

	}
	
	private void eliminar(HttpServletRequest request, HttpServletResponse response) {

		Integer studentId = Integer.parseInt(request.getParameter("id"));
		
		service.delete(studentId);
	}
	private void activar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer activarStudentId = Integer.parseInt(request.getParameter("id"));
		service.active(activarStudentId);
	}
}
