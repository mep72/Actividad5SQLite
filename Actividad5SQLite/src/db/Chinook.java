package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Chinook {
	private String database;
	private Connection connection;
	private Statement statement;

	public Chinook(String db) throws ClassNotFoundException, SQLException {
		this.database = db;
		// 1. Obtener el Driver correcto para la base de datos
		// Escribe tu código aquí{
		Class.forName("org.sqlite.JDBC");

		// }
		this.connection = DriverManager.getConnection("jdbc:sqlite:" + database);
		this.statement = connection.createStatement();
	}

	public Connection getConnection() {
		return connection;
	}

	public ArrayList<Customer> getCustomers() throws SQLException {
		ResultSet rs = this.statement.executeQuery("select * from Customer;");
		ArrayList<Customer> customers = new ArrayList<Customer>();
		while (rs.next()) {
			Customer temp = new Customer();
			temp.setCustomerId(rs.getInt("CustomerId"));
			temp.setFirstName(rs.getString("FirstName"));
			temp.setLastName(rs.getString("LastName"));
			temp.setCompany(rs.getString("Company"));
			temp.setAddress(rs.getString("Address"));
			temp.setCity(rs.getString("City"));
			temp.setState(rs.getString("State"));
			temp.setCountry(rs.getString("Country"));
			temp.setPostalCode(rs.getString("PostalCode"));
			temp.setPhone(rs.getString("Phone"));
			temp.setFax(rs.getString("Fax"));
			temp.setEmail(rs.getString("Email"));
			temp.setSupportRepId(rs.getInt("SupportRepId"));
			customers.add(temp);
		}
		return customers;
	}

	public boolean addCustomer(Customer customer) throws SQLException {
		String sql = "insert into Customer(FirstName, LastName, Company, Address, "
				+ "City, State, Country, PostalCode, Phone, Fax, Email, SupportRepId) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement prepStmt = this.connection.prepareStatement(sql);
		// 2. Agregar los atributos del objeto customer al PreparedStatement
		// Escribe tu código aquí{
		prepStmt.setString(1, customer.getFirstName());
		prepStmt.setString(2, customer.getLastName());
		prepStmt.setString(3, customer.getCompany());
		prepStmt.setString(4, customer.getAddress());
		prepStmt.setString(5, customer.getCity());
		prepStmt.setString(6, customer.getState());
		prepStmt.setString(7, customer.getCountry());
		prepStmt.setString(8, customer.getPostalCode());
		prepStmt.setString(9, customer.getPhone());
		prepStmt.setString(10, customer.getFax());
		prepStmt.setString(11, customer.getEmail());
		prepStmt.setInt(12, customer.getSupportRepId());

		// }
		return prepStmt.execute();
	}

	public int deleteCustomer(Customer customer) throws SQLException {
		String sql = "delete from Customer where CustomerId = ? ";
		PreparedStatement prepStmt = this.connection.prepareStatement(sql);
		prepStmt.setInt(1, customer.getCustomerId());
		prepStmt.execute();
		return prepStmt.getUpdateCount();
	}

	public int updateCustomer(Customer customer) throws SQLException {
		String sql = "update customer set FirstName=?, LastName=?, Company=?, Address=?, City=?, "
				+ "State=?, Country=?, PostalCode=?, Phone=?, Fax=?, Email=?, SupportRepId=?" + "where CustomerId = ?";
		PreparedStatement prepStmt = this.connection.prepareStatement(sql);
		// 3. Agregar los atributos del objeto customer al PreparedStatement
		// Escribe tu código aquí{
		prepStmt.setString(1, customer.getFirstName());
		prepStmt.setString(2, customer.getLastName());
		prepStmt.setString(3, customer.getCompany());
		prepStmt.setString(4, customer.getAddress());
		prepStmt.setString(5, customer.getCity());
		prepStmt.setString(6, customer.getState());
		prepStmt.setString(7, customer.getCountry());
		prepStmt.setString(8, customer.getPostalCode());
		prepStmt.setString(9, customer.getPhone());
		prepStmt.setString(10, customer.getFax());
		prepStmt.setString(11, customer.getEmail());
		prepStmt.setInt(12, customer.getSupportRepId());
		prepStmt.setInt(13, customer.getCustomerId());

		// }
		prepStmt.execute();
		return prepStmt.getUpdateCount();
	}
}
