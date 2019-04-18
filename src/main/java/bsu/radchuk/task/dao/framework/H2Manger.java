package bsu.radchuk.task.dao.framework;


import bsu.radchuk.task.dao.DaoException;
import bsu.radchuk.task.dao.UserDao;
import bsu.radchuk.task.model.User;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * H2 database connection manager. Singleton.
 * It wraps the <code>Connection</code> objects creation.
 * Provides a simple connection pooling.
 *
 * @see FixedConnectionPool
 *
 * @author Dmitry Radchuk
 */
@Slf4j
public final class H2Manger implements ConnectionManager {
    /**
     * <code>FixedConnectionPool</code> object size.
     */
    private static final int JDBC_CONNECTION_POOL_SIZE = 10;
    /**
     * Database init sql script file.
     * This file stores Tables creation queries, and other
     * database initialization queries.
     */
    private static final String SQL_INIT_SCRIPT
            = "sql/script/script.sql";

    /**
     * URL to database.
     */
    private static final String URL = "jdbc:h2:./h2db;DATABASE_TO_UPPER=false";
    /**
     * Database user login.
     */
    private static final String LOGIN = "tully";
    /**
     * Database user password.
     */
    private static final String PASSWORD = "tully";

    /**
     * Singleton instance.
     */
    private static final H2Manger INSTANCE = new H2Manger();

    /**
     * Singleton instance getter.
     * @return <code>H2Manger</code> object instance.
     */
    static H2Manger getInstance() {
        return INSTANCE;
    }

    /**
     * Simple connection pool.
     * @see FixedConnectionPool
     */
    private FixedConnectionPool connectionPool;

    /**
     * H2 connection manager default constructor.
     * set ups the <code>Connection</code> objects pool.
     */
    private H2Manger() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(URL);
        dataSource.setUser(LOGIN);
        dataSource.setPassword(PASSWORD);
        connectionPool = FixedConnectionPool.create(dataSource,
                                      JDBC_CONNECTION_POOL_SIZE);
        try {
            initDatabase();
        } catch (Exception exception) {
            log.error("FATAL!!!!!!!");
        }

    }

    /**
     * Gets JDBC connection from the connection pool.
     * @return new <code>Connection</code> object.
     * @throws SQLException if a new connection could not be established,
     * or a loginTimeout occurred
     */
    @Override
    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    /**
     * Runs database init script file.
     * @throws SQLException if script file can't be read.
     */
    private void initDatabase() throws SQLException, UnsupportedEncodingException {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        try {
            RunScript.execute(getConnection(),
                              new FileReader(fullPath + SQL_INIT_SCRIPT));
        } catch (FileNotFoundException exception) {
            log.error("Failed to read SQL script file:"
                      + " File not found!", exception);
        }
    }

    /**
     * Runs database script files.
     * @param args console parameters.
     */
    public static void main(final String[] args) throws DaoException {
//        try {
            //getInstance().initDatabase();
            @Cleanup UserDao executor = new UserDao();
        System.out.println(executor.find("login"));
//        } catch (SQLException exception) {
//            log.error("Failed to init Database: SQL exception!", exception);
//        }

    }
}
