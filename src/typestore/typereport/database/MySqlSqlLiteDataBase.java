package typestore.typereport.database;

import java.io.File;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import typestore.typereport.Main;

public class MySqlSqlLiteDataBase {

	private CredentialsDataBase credentials;
	private Main m;
	
	
	private Connection connection;
	public boolean active;

	public MySqlSqlLiteDataBase(CredentialsDataBase credentials, Main m) {
		this.credentials = credentials;
		this.m = m;
	}

	public void connect() {
		if (m.getConfigLoader().ativar) {
			try {

				String driver = "com.mysql.jdbc.Driver";
				Class.forName(driver);

				String url = "jdbc:mysql://<ip>:<port>/<database>";

				this.connection = DriverManager.getConnection(
						url.replaceAll("<ip>", credentials.getIp())
								.replaceAll("<port>", String.valueOf(credentials.getPort()))
								.replaceAll("<database>", credentials.getDatabase()),
								credentials.getUser(), credentials.getPassword());
				this.active = true;

			} catch (Exception e) {
				e.printStackTrace();
				Bukkit.getConsoleSender().sendMessage(m.getPrefix() + " §cErro ao tentar conectar com §eMySQL!");
				m.getPluginLoader().disablePlugin((Plugin) m);
			}
		} else {
			connectsqlite();
		}
	}

	private void connectsqlite() {
		try {
			File file = new File(m.getDataFolder(), "database.db");
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + file);
			active = true;

		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(m.getPrefix() + " §cErro ao tentar conectar com §eSqlLite!");
			m.getPluginLoader().disablePlugin((Plugin) m);
		}
	}

	public Connection getConnection() {
		if (connection == null || !active)
			this.connect();

		return connection;
	}

	public void disconnect() {
		if (connection != null && active) {
			try {
				connection.close();
				active = false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
