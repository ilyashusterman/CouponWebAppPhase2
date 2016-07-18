package system;

import dao.CompanyDAO;
import dao.CouponDAO;
import dao.CustomerDAO;
import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import dbdao.CustomerDBDAO;
import dbdao.DataBaseDBDAO;
import exceptions.CouponSystemException;
import facade.AdminFacade;
import facade.ClientType;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;
import facadedb.AdminFacadeDB;
import facadedb.CompanyFacadeDB;
import facadedb.CustomerFacadeDB;

/**
 * This class represents the System of the Coupons using the facades on
 * login method and returns them as CouponClientFacade
 * Using AdminFacade,CompanyFacade,CustomerFacade
 * CouponSystem class Contains all DAO layer with full operational methods
 * Consist also DailyCouponExpirationTask
 * Coupon system is a singleton which only one instance can be use at a
 * time
 *
 * @author ilya shusterman
 * @see DailyCouponExpirationTask
 * @see CustomerDAO
 * @see CompanyDAO
 * @see CouponDAO
 * @see DataBaseDBDAO
 */
public class CouponSystem {
	/**
	 * the connection pool instance to check connections
	 */
    public ConnectionPool pool;
    /**
     * Admin Facade Client type
     */
	private AdminFacade adminFacade;
	/**
	 * Company Facade Client type
	 */
	private CompanyFacade companyFacade;
	/**
	 * Custoemr Facade Client type
	 */
	private CustomerFacade customerFacade;
    /**
     * Coupon system instance can only instanced once
     */
    private volatile static CouponSystem couponSys = null;
    /**
     * DailyCouponExpirationTask operations daily basis
     * @see DailyCouponExpirationTask
     */
    private DailyCouponExpirationTask task;
    /**
     * DAO layer - CustomerDAO
     * @see CustomerDAO
     */
    public CustomerDAO customerDAO;
        /**
     * DAO layer - CompanyDAO
     * @see CompanyDAO
     */
    public CompanyDAO companyDAO;
        /**
     * DAO layer - CouponDAO
     * @see CouponDAO
     */
    public CouponDAO couponDAO;
           /**
     *DAO layer - DataBaseDBDAO
     * @see DataBaseDBDAO
     */
    public DataBaseDBDAO dbActions;
/**
 * <ul><li>No argument Constructor used to get fully functional system with DAO layer
 * <li>getting Daily task also fully operational</ul>
 * @throws CouponSystemException throws coupon system exception
 * @see CouponSystem
 */
    private CouponSystem() throws CouponSystemException {
        System.out.println("Connecting to database...please wait...");
        pool=ConnectionPool.getInstance();
        couponDAO = new CouponDBDAO(true);
        companyDAO = new CompanyDBDAO(true);
        customerDAO = new CustomerDBDAO(true);
        adminFacade=AdminFacadeDB.getInstance();
        companyFacade=CompanyFacadeDB.getInstance();
        customerFacade=CustomerFacadeDB.getInstance();
        dbActions = new DataBaseDBDAO();
        this.task = new DailyCouponExpirationTask();
        task.start();
    }
/**
 * 
 * The login returns an CouponClientFacade by the clientType request
 * Might return null if login was NOT successfully
 * @param name user name
 * @param password password 
 * @param clientType client type
 * @return CouponClientFacade
 * @throws CouponSystemException  CouponSystemException
 * @see CouponClientFacade
 */
    public CouponClientFacade login(String name, String password, ClientType clientType) throws CouponSystemException {
        CouponClientFacade client = null;
        CouponClientFacade theClient = null;
        switch (clientType) {
            case ADMIN:
                client = (adminFacade);
                break;
            case COMPANY:
                client = (companyFacade);
                break;
            case CUSTOMER:
                client = (customerFacade);
                break;
            default:
                break;
        }
        theClient = client.login(name, password);//FIXME handle exception
        return theClient;
    }
/**
 * *<ul><li>this method returns an CouponClientFacade only if the login was successfully from the first client that approved it
 * *<li>By the order AdminCompanyCustomer
 * *<li> If Login was not successful in all of the CouponSystem DataBase , it will throw exception based on username and password are not match
 * *</ul>
 * @param name name 
 * @param password password 
 * @return CouponClientFacade
 * @throws CouponSystemException  CouponSystemException
 * @see CouponClientFacade
 */
    public CouponClientFacade login(String name, String password) throws CouponSystemException {
        CouponClientFacade facade = null;
        int notValid = 0;
        String errorMessage = "";
        try {
            if ((facade = login(name, password, ClientType.ADMIN)) == null) {
                notValid++;
            } else if ((facade = login(name, password, ClientType.COMPANY)) == null) {
                notValid++;
            } else if ((facade = login(name, password, ClientType.CUSTOMER)) == null) {
                notValid++;
            }

        } catch (CouponSystemException e) {
            errorMessage = e.getMessage();
        }
        if (notValid == 3) {
            throw new CouponSystemException(errorMessage);
        }
        return facade;
    }
/**
 * <ul><li>This method gets the same instance that created onceCouponSystem use purposes</ul>
 * @return CouponSystem instance
 * @throws CouponSystemException  CouponSystemException
 * @see CouponSystem
 */
    public static CouponSystem getInstance() throws CouponSystemException {
        if (couponSys == null) {
            synchronized (CouponSystem.class) {
                if (couponSys == null) {
                    couponSys = new CouponSystem();
                }
            }
        }

        return couponSys;
    }
/**
 * <ul><li>Shuts down all Systems aggressively</ul>
 * @throws CouponSystemException  CouponSystemException
 */
    public void shutdown() throws CouponSystemException {
        task.stopTask();
        ConnectionPool.getInstance().closeAllConnections();
        System.exit(0);
    }

}
