package typestore.typereport.database;

public class CredentialsDataBase {
	
	 private  String ip;
     private  int port;
     private  String user;
     private  String password;
     private  String database;
     
	public CredentialsDataBase(String ip, int port, String user, String password, String database) {
		
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
		this.database = database;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}

}
