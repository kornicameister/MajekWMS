package wms.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class allows to either open or kill connection to specific database engine.
 * @author kornicameister
 *
 */
public class WMSDatabaseConnectionManager {
	private static WMSConnectionData credentials = null;
	private static Connection connection = null;
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static Logger log = Logger.getLogger(WMSDatabaseConnectionManager.class.getName());
	
	/**
	 * Use this method to open the connection. 
	 * <p style="color: red"> {@link WMSDatabaseConnectionManager.credentials} must be initialized before
	 * calling this method</p>
	 * 
	 * @throws Exception if credentials had not been initialized 
	 */
	public static void openConnection() throws Exception {
		if(WMSDatabaseConnectionManager.credentials == null){
			throw new Exception("Unitialized credentials, connection wont open");
		}
		
		try {
			log.log(Level.CONFIG, "Loading {0} database driver.",WMSDatabaseConnectionManager.DRIVER);
			Class.forName(DRIVER).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			log.log(Level.SEVERE, "Failed to load com.mysql.jdbc.Driver driver");
			e1.printStackTrace();
		}

		String url = "jdbc:mysql://!:!/!";

		url = url.replaceFirst("!", credentials.getHost());
		url = url.replaceFirst("!", credentials.getPort().toString());
		url = url.replaceFirst("!", credentials.getDatabaseName());

		try {
			WMSDatabaseConnectionManager.connection = DriverManager.getConnection(url, credentials.getUserName(),credentials.getUserPassword());
			log.logp(Level.INFO, "Connected to {0} engine using {1} user name", WMSDatabaseConnectionManager.DRIVER, credentials.getUserName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Encapsulates safe way to currently alive connection established to {@link WMSDatabaseConnectionManager.DRIVER} engine.
	 */
	public static void killConnection(){
		if (WMSDatabaseConnectionManager.connection != null) {
			try {
				WMSDatabaseConnectionManager.connection.close();
				if (WMSDatabaseConnectionManager.connection.isClosed()) {
					log.log(Level.INFO, "Connection terminated...");
				}
				WMSDatabaseConnectionManager.connection = null;
			} catch (SQLException e) {
				log.log(Level.WARNING, "Connection couldn't be terminated and it still active...");
				e.printStackTrace();
			}
		}
	}

	public static WMSConnectionData getCredentials() {
		return WMSDatabaseConnectionManager.credentials;
	}

	public static void setCredentials(WMSConnectionData credentials) {
		WMSDatabaseConnectionManager.credentials = credentials;
	}

	public static Connection getConnection() throws Exception {
		if(WMSDatabaseConnectionManager.connection == null){
			WMSDatabaseConnectionManager.openConnection();
		}
		return WMSDatabaseConnectionManager.connection;
	}

	public static void setConnection(Connection connection) {
		WMSDatabaseConnectionManager.connection = connection;
	}
}
