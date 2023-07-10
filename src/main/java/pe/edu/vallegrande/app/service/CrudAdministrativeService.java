package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;
import pe.edu.vallegrande.app.model.Administrative;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpec;

public class CrudAdministrativeService implements CrudServiceSpec<Administrative> {
	// variables generales
	private Connection connection;
	private PreparedStatement statement;

	// Listar a la lista de los docentes
	@Override
	public List<Administrative> getAll() {
		List<Administrative> listaAdministrative = new ArrayList<>();

		String sql = "SELECT * FROM administrative WHERE active = 'A'";

		try {
			connection = AccesoDB.getConnection();
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Administrative administrative = new Administrative();
				administrative.setId(resultSet.getInt("id"));
				administrative.setDocumentType(resultSet.getString("document_type"));
				administrative.setDocumentNumber(resultSet.getString("document_number"));
				administrative.setNames(resultSet.getString("names"));
				administrative.setLastnames(resultSet.getString("lastnames"));
				administrative.setEmail(resultSet.getString("email"));
				administrative.setCellphone(resultSet.getString("cellphone"));
				administrative.setTypeCharge(resultSet.getString("type_charge"));

				listaAdministrative.add(administrative);
			}

			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return listaAdministrative;
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

	
	// Consultar por Fecha de cumpleaños
		public List<Administrative> getByFilter(String active, String filter, String documentType) {
			List<Administrative> administrativeList = new ArrayList<>();
			String sql = "SELECT * FROM administrative "
					+ "WHERE active = ? "
					+ "AND document_type LIKE ? "
					+ "AND (document_number LIKE ? "
					+ "OR LOWER(names) LIKE LOWER(?) "
					+ "OR LOWER(lastnames) LIKE LOWER(?) "
					+ "OR LOWER(type_charge) LIKE LOWER(?)) "
					+ "ORDER BY names, lastnames";

			try {
				connection = AccesoDB.getConnection();
				statement = connection.prepareStatement(sql);
				statement.setString(1, active);
				statement.setString(2, "%" + documentType + "%");
				statement.setString(3, "%" + filter + "%");
				statement.setString(4, "%" + filter + "%");
				statement.setString(5, "%" + filter + "%");
				statement.setString(6, "%" + filter + "%");
				
				ResultSet resultSet = statement.executeQuery();

				while (resultSet.next()) {
					Administrative administrative = new Administrative();
					administrative.setId(resultSet.getInt("id"));
					administrative.setDocumentType(resultSet.getString("document_type"));
					administrative.setDocumentNumber(resultSet.getString("document_number"));
					administrative.setNames(resultSet.getString("names"));
					administrative.setLastnames(resultSet.getString("lastnames"));
					administrative.setEmail(resultSet.getString("email"));
					administrative.setCellphone(resultSet.getString("cellphone"));
					administrative.setTypeCharge(resultSet.getString("type_charge"));
					administrative.setActive(resultSet.getString("active"));

					administrativeList.add(administrative);
				}

				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeResources();
			}

			return administrativeList;
		}

	// Guardar teacher
	@Override
	public void insert(Administrative bean) {

		String sql = null;

		try {

			connection = AccesoDB.getConnection();

			sql = "INSERT INTO administrative (document_type, document_number, names, lastnames, email, cellphone, type_charge) VALUES (?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);

			statement.setString(1, bean.getDocumentType());
			statement.setString(2, bean.getDocumentNumber());
			statement.setString(3, bean.getNames());
			statement.setString(4, bean.getlastnames());
			statement.setString(5, bean.getEmail());
			statement.setString(6, bean.getCellphone());
			statement.setString(7, bean.getTypeCharge());

			statement.executeUpdate();

			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// editar teacher
	@Override
	public void update(Administrative bean) {
		String sql = null;

		try {

			connection = AccesoDB.getConnection();
			sql = "UPDATE administrative SET document_type=?, document_number=?, names=?, lastnames=?, email=?, cellphone=?, type_charge=? WHERE id=?";
			statement = connection.prepareStatement(sql);

			statement.setString(1, bean.getDocumentType());
			statement.setString(2, bean.getDocumentNumber());
			statement.setString(3, bean.getNames());
			statement.setString(4, bean.getlastnames());
			statement.setString(5, bean.getEmail());
			statement.setString(6, bean.getCellphone());
			statement.setString(7, bean.getTypeCharge());
			statement.setInt(8, bean.getId());

			statement.executeUpdate();

			statement.close();
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	// eliminar Teacher
	@Override
	public void delete(Integer id) {

		String sql = null;

		try {
			connection = AccesoDB.getConnection();

			sql = "UPDATE administrative SET active = 'I' WHERE id=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();

			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//Reactivar Profesor 
		@Override
		public void active(Integer id) {
		    String sql = null;

		    try {
		        connection = AccesoDB.getConnection();

		        sql = "UPDATE administrative SET active = 'A' WHERE id=?";
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
	public List<Administrative> get(Administrative bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Administrative getForId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
