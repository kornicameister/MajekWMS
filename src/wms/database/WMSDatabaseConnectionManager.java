package wms.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * Class allows to either open or kill connection to specific database engine.
 * 
 * @author kornicameister
 * 
 */
public class WMSDatabaseConnectionManager {
	private Connection connection = null;
	private final DataSource dataSource;
	private static Logger log = Logger.getLogger(WMSDatabaseConnectionManager.class.getName());
	
	public WMSDatabaseConnectionManager(DataSource dataSource) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.dataSource = dataSource;
	}

	/**
	 * Use this method to open the connection.
	 * <p style="color: red">
	 * {@link WMSDatabaseConnectionManager.credentials} must be initialized
	 * before calling this method
	 * </p>
	 * 
	 */
	public void openConnection() {
		try {
			this.connection = this.dataSource.getConnection();
			log.log(Level.INFO, "Connected to database {0}", this.connection.getCatalog());
		} catch (SQLException e) {
			log.log(Level.SEVERE, "Failed to connect to database", e);
		}
	}

	/**
	 * Encapsulates safe way to currently alive connection established to
	 * {@link WMSDatabaseConnectionManager.DRIVER} engine.
	 */
	public void killConnection() {
		if (this.connection != null) {
			try {
				this.connection.close();
				if (this.connection.isClosed()) {
					log.log(Level.INFO, "Connection terminated...");
				}
				this.connection = null;
			} catch (SQLException e) {
				log.log(Level.WARNING, "Connection couldn't be terminated and it still active...", e);
			}
		}
	}

	public Connection getConnection() throws Exception {
		if (this.connection == null) {
			this.openConnection();
		}
		return this.connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
