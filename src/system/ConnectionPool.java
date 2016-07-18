package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import exceptions.CouponSystemException;
import exceptions.ErrorType;
/**
 * 
 *This ConnectionPool class gives Max connection number each time a connection is available
 * Will take back a connection and notify any other Object that is waiting 
 * Aggressive closing connections instances through the back up connections
 * using derby apache client database
 * @version version 01
 * @see Connection
 * 
 * @author ilya shusterman
 */
public class ConnectionPool {
/**
 * class field connection stream to enter derby apache client
 */
    private static String connectionString = "jdbc:derby://localhost:1527/CouponsDB;create=true";
    /**
     * Max connections
     */
    private final int MAX_CONNECTIONS;
    /**
     * free connections
     */
    private static int free_Connection;
    /**
     * current connections pool Linked list
     */
    private static LinkedList<Connection> connectionPools;
    /**
     * set of collections of back up connections (all instances of connections inside the system)
     */
    private static Set<Connection> connectionPoolsBackUp;
    /*
    class instance singleton pool 
    */
    private volatile static ConnectionPool pool;
/**
 * <ul><li>This constructor instantiate an max number of connections to the pool only once inside connection pool class </ul>
 * @throws CouponSystemException 
 * <li> No argument constructor , used only once inside an getInstance method
 * @see ConnectionPool.getInstance
 */
    private ConnectionPool() throws CouponSystemException { // the constructor creates a
        this.MAX_CONNECTIONS = free_Connection = 10;
        connectionPools = new LinkedList<>();
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection connection = DriverManager.getConnection(connectionString);
                connectionPools.add(connection);
            } catch (SQLException | ClassNotFoundException e) {
                throw new CouponSystemException(ErrorType.UNABLE_TO_CREATE_CONNECTIONS, e);
            }
        }
        connectionPoolsBackUp = new HashSet<>();
        connectionPoolsBackUp.addAll(connectionPools);
    }
/**
 * <ul><li>This method is checking if there are connections available in the pool
 * <li>checks if connections pool is empty </ul>
 * @return 
 */
    private boolean connectionAvailable() {
        return free_Connection > 0 && connectionPools.size() > 0 || !connectionPools.isEmpty();
    }

    /**
     *This method invoked from DAO layer to use connections, return and get connections
     * @return ConnectionPool pool 
     * @throws CouponSystemException CouponSystemException
     */
    public static ConnectionPool getInstance() throws CouponSystemException { // synchronized
        if (pool == null) {
            synchronized (ConnectionPool.class) {
                if (pool == null) {
                    pool = new ConnectionPool();
                }
            }
        }
        return pool;
    }

    /**
     * <ul><li>This method waiting until connection is available then when it is available
     * it releasing a connection from the pool to the object that requests it</ul>
     *@see Connection 
     * @return Connection
     * @throws CouponSystemException CouponSystemException
     */
    public synchronized Connection getConnection() throws CouponSystemException {
        while (!connectionAvailable()) { // While connection is not available -
            // wait
            try {
                wait();
            } catch (InterruptedException e) {
                throw new CouponSystemException(ErrorType.GET_CONNECTION_ERROR, e);
            }
        }
        free_Connection--;
        Connection connection = connectionPools.getFirst();
        connectionPools.removeFirst();
        return connection;
    }

    /**
     *<ul><li>This method adding back a connection to the pool and notifying another object that is waiting</ul>
     * @param connection connection
     * @throws CouponSystemException CouponSystemException
     */
    public synchronized void returnConnection(Connection connection) throws CouponSystemException {

        try {
            connectionPools.addFirst(connection);
            free_Connection++;
        } catch (NullPointerException e) {
            throw new CouponSystemException(ErrorType.CONNECTION_NOT_VALID, e);
        }
        notify();
    }

    /**
     *<ul><li>This method goes through all connections instances in the pool back up and close them one by one</ul>
     * @return boolean value if all connection closed successfully
     * @throws CouponSystemException CouponSystemException
     */
    public boolean closeAllConnections() throws CouponSystemException {
        for (Connection connection : connectionPoolsBackUp) {
            if (pool != null) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new CouponSystemException(ErrorType.FAILED_TO_CLOSE_CONNECTION, e);
                    }
                }

            }
        }
        connectionPools.clear();
        return true;
    }
/**
 * pool to String real time connections number and max connections 
 * @return String value of pool
 */
    @Override
    public String toString() {
        return "There are " + free_Connection + " free connections " + " Is pool empty : "
                + connectionPools.isEmpty() + " Max conection allowed " + MAX_CONNECTIONS;
    }
}
