package pl.polsl.zti.db1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pl.polsl.zti.db1.domain.Client;
import pl.polsl.zti.db1.util.DatabaseAccessException;
import pl.polsl.zti.db1.util.JdbcUtils;

public class ClientDaoImplJdbc implements ClientDao {

	@Override
	public Client getClient(int id) {
		Client client = null;
		Connection con = null;
		PreparedStatement preparedStatement = null; // polecenie prekompilowane
		try {
			con = JdbcUtils.getConnection();
			preparedStatement = con.prepareStatement("SELECT * FROM CLIENTS WHERE id = ?");
			preparedStatement.setInt(1, id); // ustawia wartość parametru

			final ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				client = readClient(resultSet);
			}
		} catch (SQLException ex) {
			JdbcUtils.handleSqlException(ex);
		} finally {
			JdbcUtils.closeSilently(preparedStatement, con);
		}

		return client;
	}

	@Override
	public List<Client> getClients() {
		// lista klientów do zwrócenia
		final List<Client> clients = new ArrayList<Client>();

		Connection con = null;
		Statement stmt = null;
		try {
			con = JdbcUtils.getConnection();
			stmt = con.createStatement();
			final ResultSet resultSet = stmt.executeQuery("SELECT ID, CLIENT_NO, NAME, SSN, ADDRESS FROM CLIENTS");
			while (resultSet.next()) {
				// utwórz nowy obiekt typu Client
				final Client client = readClient(resultSet);
				// dodaj klienta do listy
				clients.add(client);
			}
		} catch (SQLException ex) {
			JdbcUtils.handleSqlException(ex);
		} finally {
			JdbcUtils.closeSilently(stmt, con);
		}

		return clients;
	}

	@Override
	public List<Client> getClients(String name) {
		if (name == null) {
			return getClients();
		}
		final List<Client> clients = new ArrayList<Client>();
		String query = "SELECT ID, CLIENT_NO, NAME, SSN, ADDRESS FROM CLIENTS WHERE name LIKE \'" + name + "\'";
		Connection con = null;
		Statement stmt = null;
		try {
			con = JdbcUtils.getConnection();
			stmt = con.createStatement();
			final ResultSet resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {
				clients.add(readClient(resultSet));
			}
		} catch (Exception e) {
			throw new DatabaseAccessException("Failed to execute query: " + query, e);
		} finally {
			JdbcUtils.closeSilently(stmt, con);
		}
		return clients;
	}

	private Client readClient(final ResultSet rs) throws SQLException {
		// utwórz nowy obiekt typu Client
		final Client client = new Client();
		// przypisz wartości do poszczególnych pól klienta
		client.setId(rs.getInt("ID"));
		client.setNo(rs.getInt("CLIENT_NO"));
		client.setName(rs.getString("NAME"));
		client.setSsn(rs.getString("SSN"));
		client.setAddress(rs.getString("ADDRESS"));
		return client;
	}

	@Override
	public void insertClients(List<Client> clients) {
		Connection con = null;
		Statement stmt = null;
		try {
			con = JdbcUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement();
			for (Client client : clients) {
				PreparedStatement preparedStatement = con
						.prepareStatement("INSERT INTO clients(ID, CLIENT_NO, NAME, SSN, ADDRESS) VALUES (?, ?, ?, ?, ?)");
				preparedStatement.setInt(1, client.getId());
				preparedStatement.setInt(2, client.getNo());
				preparedStatement.setString(3, client.getName());
				preparedStatement.setString(4, client.getSsn());
				preparedStatement.setString(5, client.getAddress());
				preparedStatement.execute();
			}
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// welcome to JDBC reality :)
				throw new DatabaseAccessException(e);
			}
			throw new DatabaseAccessException(e);
		} finally {
			JdbcUtils.closeSilently(stmt, con);
		}
	}

	@Override
	public void updateClient(Client client) {
		Connection con = null;
		Statement stmt = null;
		try {
			con = JdbcUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement();
			PreparedStatement preparedStatement = con
					.prepareStatement("UPDATE clients SET ID=?, CLIENT_NO=?, NAME=?, SSN=?, ADDRESS=? WHERE ID=?");
			preparedStatement.setInt(1, client.getId());
			preparedStatement.setInt(2, client.getNo());
			preparedStatement.setString(3, client.getName());
			preparedStatement.setString(4, client.getSsn());
			preparedStatement.setString(5, client.getAddress());
			preparedStatement.setInt(6, client.getId());
			preparedStatement.executeUpdate();
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// welcome to JDBC reality :)
				throw new DatabaseAccessException(e);
			}
			throw new DatabaseAccessException(e);
		} finally {
			JdbcUtils.closeSilently(stmt, con);
		}
	}

	@Override
	public void deleteClient(int id) {
		Connection con = null;
		Statement stmt = null;
		try {
			con = JdbcUtils.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM CLIENTS WHERE id = " + id);
		} catch (SQLException ex) {
			JdbcUtils.handleSqlException(ex);
		} finally {
			JdbcUtils.closeSilently(stmt, con);
		}
	}

	@Override
	public void deleteClient(Client client) {
		deleteClient(client.getId());
	}
}
