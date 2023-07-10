package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;
import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpec;

public class CrudStudentService implements CrudServiceSpec<Student>{

	// VARIABLES GLOBALES
		private Connection connection;
		private PreparedStatement statement;
	
	//LISTA DE ESTUDIANTES
	@Override
	public List<Student> getAll() {
		List<Student> listaStudent = new ArrayList<>();

		String sql = "SELECT * FROM student WHERE active = 'A'";

		try {
			connection = AccesoDB.getConnection();
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Student student = new Student();
				student.setId(resultSet.getInt("id"));
				student.setNames(resultSet.getString("names"));
				student.setLastname(resultSet.getString("lastname"));
				student.setEmail(resultSet.getString("email"));
				student.setCellphone(resultSet.getString("cellphone"));
				student.setCareer(resultSet.getString("career"));
				student.setSemester(resultSet.getString("semester"));
				student.setDocument_type(resultSet.getString("document_type"));
				student.setDocument_number(resultSet.getString("document_number"));

				listaStudent.add(student);
			}

			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return listaStudent;
	}

	private void closeResources() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//FILTRAR ESTUDIANTE
	public List<Student> getByFilter(String active, String filter, String document_type) {
		List<Student> studentList = new ArrayList<>();
		String sql = "SELECT * FROM student "
				+ "WHERE active = ? "
				+ "AND document_type LIKE ? "
				+ "AND (document_number LIKE ? "
				+ "OR LOWER(names) LIKE LOWER(?) "
				+ "OR LOWER(lastname) LIKE LOWER(?)) "
				+ "ORDER BY names, lastname";

		try {
			connection = AccesoDB.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, active);
			statement.setString(2, "%" + document_type + "%");
			statement.setString(3, "%" + filter + "%");
			statement.setString(4, "%" + filter + "%");
			statement.setString(5, "%" + filter + "%");
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Student student = new Student();
				student.setId(resultSet.getInt("id"));
				student.setNames(resultSet.getString("names"));
				student.setLastname(resultSet.getString("lastname"));
				student.setEmail(resultSet.getString("email"));
				student.setCellphone(resultSet.getString("cellphone"));
				student.setCareer(resultSet.getString("career"));
				student.setSemester(resultSet.getString("semester"));
				student.setDocument_type(resultSet.getString("document_type"));
				student.setDocument_number(resultSet.getString("document_number"));
				student.setActive(resultSet.getString("active"));
				studentList.add(student);
			}

			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return studentList;
	}

	
	@Override
	public Student getForId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> get(Student bean) {
		// TODO Auto-generated method stub
		return null;
	}

	//INSERTAR ESTUDIANTE
	@Override
	public void insert(Student bean) {

		String sql = null;

		try {

			connection = AccesoDB.getConnection();

			sql = "INSERT INTO student (names, lastname, email, cellphone, career, semester, document_type, document_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);

			statement.setString(1, bean.getNames());
			statement.setString(2, bean.getLastname());
			statement.setString(3, bean.getEmail());
			statement.setString(4, bean.getCellphone());
			statement.setString(5, bean.getCareer());
			statement.setString(6, bean.getSemester());
			statement.setString(7, bean.getDocument_type());
			statement.setString(8, bean.getDocument_number());
			
			statement.executeUpdate();

			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	//EDITAR ESTUDIANTE
	@Override
	public void update(Student bean) {
		String sql = null;

		try {

			connection = AccesoDB.getConnection();
			sql = "UPDATE student SET names=?, lastname=?, email=?, cellphone=?, career=?, semester=?, document_type=?, document_number=? WHERE id=?";
			statement = connection.prepareStatement(sql);

			statement.setString(1, bean.getNames());
			statement.setString(2, bean.getLastname());
			statement.setString(3, bean.getEmail());
			statement.setString(4, bean.getCellphone());
			statement.setString(5, bean.getCareer());
			statement.setString(6, bean.getSemester());
			statement.setString(7, bean.getDocument_type());
			statement.setString(8, bean.getDocument_number());
			statement.setInt(9, bean.getId());

			statement.executeUpdate();

			statement.close();
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		
	}

	//ELIMINAR ESTUDIANTE
	@Override
	public void delete(Integer id) {

		String sql = null;

		try {
			connection = AccesoDB.getConnection();

			sql = "UPDATE student SET active = 'I' WHERE id=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();

			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	//REACTIVAR ESTUDIANTE
	@Override
	public void active(Integer id) {
	    String sql = null;

	    try {
	        connection = AccesoDB.getConnection();

	        sql = "UPDATE student SET active = 'A' WHERE id=?";
	        statement = connection.prepareStatement(sql);
	        statement.setInt(1, id);
	        statement.executeUpdate();

	        statement.close();
	        connection.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}

}
