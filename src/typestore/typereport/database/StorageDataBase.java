package typestore.typereport.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StorageDataBase {

	private MySqlSqlLiteDataBase mysqlsqlitedatabase;

	public StorageDataBase(MySqlSqlLiteDataBase Mysqlsqlitedatabase) {
		mysqlsqlitedatabase = Mysqlsqlitedatabase;
		createTables();
	}

	private void createTables() {

		try (PreparedStatement ps = connection()
				.prepareStatement("CREATE TABLE IF NOT EXISTS `typereport` (`player` TEXT, `reports` TEXT)")) {
			ps.executeUpdate();
			mysqlsqlitedatabase.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
			mysqlsqlitedatabase.disconnect();
		}

	}

	public void setPlayer(String player, String reports) {
		try (PreparedStatement ps = connection()
				.prepareStatement("INSERT INTO `typereport`(`player`, `reports`) VALUES (?,?)")) {
			ps.setString(1, player);
			ps.setString(2, reports);
			ps.executeUpdate();
			mysqlsqlitedatabase.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
			mysqlsqlitedatabase.disconnect();
		}

	}

	public void setreports(String player, String reports) {

		try (PreparedStatement ps = connection()
				.prepareStatement("UPDATE `typereport` SET `reports` = ? WHERE `player` = ?")) {
			ps.setString(1, reports);
			ps.setString(2, player);
			ps.executeUpdate();
			mysqlsqlitedatabase.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
			mysqlsqlitedatabase.disconnect();
		}

	}

	public String getreports(String player) {
		try (PreparedStatement ps = connection().prepareStatement("SELECT * FROM `typereport` WHERE `player` = ?")) {
			ps.setString(1, player);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String v = rs.getString("reports");
				mysqlsqlitedatabase.disconnect();
				return v;
			}
			mysqlsqlitedatabase.disconnect();
			return "";
		} catch (SQLException e) {
			mysqlsqlitedatabase.disconnect();
			return "";

		}

	}

	public boolean verificarPlayer(String player) {

		try (PreparedStatement ps = connection().prepareStatement("SELECT * FROM `typereport` WHERE `player` = ?")) {
			ps.setString(1, player);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				mysqlsqlitedatabase.disconnect();
				return true;
			}
			mysqlsqlitedatabase.disconnect();
			return false;
		} catch (SQLException e) {
			mysqlsqlitedatabase.disconnect();
			return false;
		}
	}

	public void deletePlayer(String player) {
		try (PreparedStatement ps = connection().prepareStatement("DELETE FROM `typereport` WHERE `player` = ?")) {
			ps.setString(1, player);
			ps.executeUpdate();
			mysqlsqlitedatabase.disconnect();
		} catch (SQLException e) {
			mysqlsqlitedatabase.disconnect();
		}
	}

	public List<String> getReports() {
		List<String> users = new ArrayList<>();
		try (PreparedStatement ps = connection().prepareStatement("SELECT * FROM `typereport`")) {
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				users.add(rs.getString("player"));
		} catch (SQLException e) {
			mysqlsqlitedatabase.disconnect();
			e.printStackTrace();
		}
		mysqlsqlitedatabase.disconnect();
		return users;
	}

	private Connection connection() {
		return mysqlsqlitedatabase.getConnection();
	}

}
