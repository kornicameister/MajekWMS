package wms.database;

/**
 * This small class is a information storage about credentials used when accessing database.
 * @author kornicameister
 *
 */
public class WMSConnectionData {
	private String databaseName = "";
	private String userName = "";
	private String userPassword = "";
	private Integer port = 3306;
	private String host = "localhost";

	public WMSConnectionData(String dbName, String user, String password) {
		super();
		this.databaseName = dbName;
		this.userName = user;
		this.userPassword = password;
	}

	public WMSConnectionData(String databaseName, String userName,
			String userPassword, Integer port, String host) {
		super();
		this.databaseName = databaseName;
		this.userName = userName;
		this.userPassword = userPassword;
		this.port = port;
		this.host = host;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
