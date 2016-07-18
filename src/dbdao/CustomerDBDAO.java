package dbdao;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import dao.CustomerDAO;
import exceptions.DAOException;
import exceptions.ErrorType;

import java.util.HashSet;
import java.util.Set;
import servicebeans.Coupon;
import servicebeans.CouponType;
import servicebeans.Customer;

/**
 * <ul><li>
 *This is CustomerDBDAO class implementation of
 * CustomerDAO is creating queries and execute them in DB and requesting for
 * Data from DB</ul>
 * @see DataBaseDBDAO
 * @see CustomerDAO
 * @author ilya shusterman
 * @since version 1.00
 */
@SuppressWarnings("unchecked")//getting ===> Customer/Coupon objects
public class CustomerDBDAO extends DataBaseDBDAO implements CustomerDAO {

        //Class Fields
    /**
     * Creating Customer Table SQL statement using constraints
     */
    private static String creatCustTable = "CREATE TABLE CUSTOMERS "
            + "(ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + " CUST_NAME VARCHAR(30) UNIQUE  NOT NULL, " + "  PASSWORD VARCHAR(28)  NOT NULL" + ")";
/**
 * Creating Join table that holds 2 primary keys Customer=>Coupon OneToMany!
 */
    private static String joinCustomerCouponTable ="CREATE TABLE CUSTOMERS_COUPONS"
            + " (" + "CUSTOMER_ID INTEGER, "
            + " COUPON_ID INTEGER," + " PRIMARY KEY ( CUSTOMER_ID,COUPON_ID))";

    /**
     *Instantiating the Tables from the Class only once! Creating Tables
     * operations must not be invoked by the user
     */
    public static boolean isTableCreated = false;
    
   /**
    * <ul><li>This method approves creating table</ul>
     *Instantiating the Tables from the Class only once! Creating Tables
     * operations must not be invoked by the user
     * @return boolean value
     */
    public boolean isTableCreated() {
        if (!isTableCreated) {
            isTableCreated = true;
            return true;
        } else {
            return false;
        }
    }


    /**
     * <ul><li>This constructor approves creating table</ul>
     *Instantiating the Tables from the Class only once! Creating Tables
     * operations must not be invoked by the user
     * @param init instantiating if create a table or not
     * @throws DAOException DAOException
     * @see DataBaseDBDAO constructor
     */
    public CustomerDBDAO(boolean init) throws DAOException {
        super(init, "Customer", creatCustTable, joinCustomerCouponTable, new CustomerDBDAO());
    }

    /**
     *No argument constructor for using the the class implementations
     */
    public CustomerDBDAO() {
        super();
    }

    ;
    /**
     * <ul>
     * <li>The method Creates a received Customer to the DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     * @param customer Company to be created
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
	@Override
    public void creatCustomer(Customer customer) throws DAOException {
        String addCustomerSQLstatement = "INSERT INTO CUSTOMERS"
                + " (CUST_NAME, PASSWORD) "
                + "VALUES (?, ?)";
        String MassageAction = "Customer";
        try{
        super.actionOnDataBase(addCustomerSQLstatement, MassageAction, customer.getCustName(), customer.getPassword());
        }catch(NullPointerException e){
        	throw new DAOException(ErrorType.CUSTOMER_VALUES_ARE_NOT_VALID,e);
        }
    }
   /**
     * <ul>
     * <li>The method removes a Customer from a received Customer id in the DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param customerId id to remove customer
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void removeCustomer(long customerId) throws DAOException {
        String removeCustomerSQLstatement = "DELETE FROM CUSTOMERS" + " WHERE " + "ID=?";
        String MassageAction =ErrorType.UNABLE_TO_REMOVE_CUSTOMER.toString();
        super.actionOnDataBase(removeCustomerSQLstatement, MassageAction,
                customerId);
    }
   /**
     * <ul>
     * <li>The method updates a Customer from a received Customer id in the DB
     * with password field
     * <li>Using String prepared statement and executing  
     * with connection more on 
     * </ul>
     *@param customerId id to update customer
     * @param password  password to update
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void updateCustomer(long customerId, String password) throws DAOException {
        String updateCustomerSQLstatement = "UPDATE CUSTOMERS" + " SET " + "PASSWORD=?"
                + " WHERE " + "ID=?";
        String MassageAction =ErrorType.UNABLE_TO_UPDATE_CUSTOMER.toString();
        super.actionOnDataBase(updateCustomerSQLstatement, MassageAction,
                password, customerId);
    }
   /**
     * <ul>
     * <li>The method gets a Customer from a received Customer id in the DB
     *<li> Using String prepared statement and executing  
     * with connection more on 
     * </ul>
     *@param customerId id to get customer
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Customer getCustomer(long customerId) throws DAOException {
        String getCustomerByIDSQLstatement = "SELECT * FROM CUSTOMERS WHERE ID=?";
        String MassageAction =ErrorType.UNABLE_TO_GET_CUSTOMER.toString();
        List<Customer> queryCustomer = (List<Customer>) super.readInDataBase(getCustomerByIDSQLstatement, MassageAction,
                "Customer", customerId);
        return queryCustomer.get(0);
    }
  /**
     * <ul>
     * <li>The method gets a Collection of all Customers from the DB
     *<li> Using String prepared statement and executing  
     * with connection more on 
     * </ul>
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Customer> getAllCustomers() throws DAOException {
        String getAllCustomerSQLstatement = "SELECT * FROM CUSTOMERS ";
        String MassageAction = ErrorType.UNABLE_TO_GET_CUSTOMERS.toString();
        List<Customer> queryCustomer = (List<Customer>) super.readInDataBase(getAllCustomerSQLstatement, MassageAction,
                "Customer");
        return (Collection<Customer>) queryCustomer;
    }

     /**
     * <ul>
     * <li>The method gets a Collection of all Coupons that purchased by the customers from the DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Coupon> getAllpurchasedCouponsByAllCust() throws DAOException {
        String getAllCouponCustomersSQLstatement = "SELECT * FROM COUPONS  "
                + "INNER JOIN CUSTOMERS_COUPONS ON CUSTOMERS_COUPONS.COUPON_ID=COUPONS.ID ";
        String MassageAction = ErrorType.UNABLE_TO_GET_COUPONS+" That has been purchased overall";
        List<Coupon> queryCustomerCoupon = (List<Coupon>) super.readInDataBase(getAllCouponCustomersSQLstatement, MassageAction,
                "Coupon");
        Set<Coupon> QueryPurchasedCoupons = new HashSet<>();
        QueryPurchasedCoupons.addAll(queryCustomerCoupon);
        queryCustomerCoupon.clear();
        queryCustomerCoupon.addAll(QueryPurchasedCoupons);
        //removes all dups in array ! 
        return queryCustomerCoupon;
    }
   /**
     * <ul>
     * <li>The method gets a Customer from a received Customer Name in the DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param custName  name to get customer
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Customer getCustomerByName(String custName) throws DAOException {
        String getLoginCustomerSQLstatement = "SELECT * FROM CUSTOMERS WHERE" + " CUST_NAME =?";
        String MassageAction = ErrorType.UNABLE_TO_GET_CUSTOMER+" By name "+custName;
        List<Customer> queryCustomer = (List<Customer>) super.readInDataBase(getLoginCustomerSQLstatement, MassageAction,
                "Customer", custName);
        return queryCustomer.get(0);
    }
   /**
     * <ul>
     * <li>The method gets a Customer from a received Customer Name and password in the DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param custName  name to get customer
     * @param password password to get customer
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Customer getCustomerByNamePassword(String custName, String password) throws DAOException {
        String getLoginCustomerSQLstatement = "SELECT * FROM CUSTOMERS WHERE" + " CUST_NAME =?"
                + " AND PASSWORD=?";
        String MassageAction = ErrorType.UNABLE_TO_GET_CUSTOMER+" By name : "+custName+" and password : "+password;
        List<Customer> queryCustomer = (List<Customer>) super.readInDataBase(getLoginCustomerSQLstatement, MassageAction,
                "Customer", custName, password);
        return queryCustomer.get(0);
    }
  /**
     * <ul>
     * <li>The method gets a boolean value whether a Customer from a received Customer Name and password in the DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param custName  id to get customer
     * @see DataBaseDBDAO
     * @see CustomerDAO         
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public boolean login(String custName, String password) throws DAOException {
        return getCustomerByNamePassword(custName, password) != null;
    }
   /**
     * <ul>
     * <li>The method gets a Collection of coupons where they belong to a received customer id from  DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     * @param id id of company
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @see Coupon
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Coupon> getCouponsByCustomerID(long id) throws DAOException {
        String getAllCouponSQLstatement = "SELECT * FROM COUPONS WHERE  "
                + "COUPONS.ID IN ( SELECT COUPON_ID FROM CUSTOMERS_COUPONS "
                + "WHERE CUSTOMERS_COUPONS.CUSTOMER_ID=?)";
        String MassageAction =ErrorType.UNABLE_TO_GET_COUPONS+" By Customer id : "+id;
        List<Coupon> queryCustomerCoupon = (List<Coupon>) super.readInDataBase(getAllCouponSQLstatement, MassageAction,
                "Coupon", id);
        return queryCustomerCoupon;
    }
   /**
     * <ul>
     * <li>The method gets a Collection of coupons where they belong to a received customer id and a type from   DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param customerId customer id
     * @param type  type
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @see Coupon
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Coupon> getCouponsByCustomerIDbyType(CouponType type, long customerId) throws DAOException {
        String getAllCouponCompaniesSQLstatement = "SELECT * FROM COUPONS WHERE  "
                + "COUPONS.ID IN ( SELECT COUPON_ID FROM CUSTOMERS_COUPONS WHERE "
                + "CUSTOMERS_COUPONS.CUSTOMER_ID=?) AND TYPE =?";
        String MassageAction = ErrorType.UNABLE_TO_GET_COUPONS_BY_TYPE+" and by Customer id : "+customerId;
        List<Coupon> queryCustomerCoupon = (List<Coupon>) super.readInDataBase(getAllCouponCompaniesSQLstatement, MassageAction,
                "Coupon", customerId, type.toString());
        return queryCustomerCoupon;
    }
    
    @Override
    public Collection<Coupon> getCouponsByCustomerIDbyDate(Timestamp date, long customerId) throws DAOException {
        String getAllCouponCompaniesSQLstatement = "SELECT * FROM COUPONS WHERE  "
                + "COUPONS.ID IN ( SELECT COUPON_ID FROM CUSTOMERS_COUPONS WHERE "
                + "CUSTOMERS_COUPONS.CUSTOMER_ID=?) AND END_DATE <=?";
        String MassageAction =ErrorType.UNABLE_TO_GET_COUPONS_BY_DATE+"and by Customer id : "+customerId;
        List<Coupon> queryCustomerCoupon = (List<Coupon>) super.readInDataBase(getAllCouponCompaniesSQLstatement, MassageAction,
                "Coupon", customerId, date);
        return queryCustomerCoupon;
    }
   /**
     * <ul>
     * <li>The method gets a Collection of coupons where they belong to a received customer id and a price from   DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param customerId customer id
     * @param price  price
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @see Coupon
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Coupon> getCouponsByCompanyIDbyPrice(double price, long customerId) throws DAOException {
        String getAllCouponCompaniesSQLstatement = "SELECT * FROM COUPONS WHERE  "
                + "COUPONS.ID IN ( SELECT COUPON_ID FROM CUSTOMERS_COUPONS WHERE "
                + "CUSTOMERS_COUPONS.CUSTOMER_ID=?) AND PRICE <=?";
        String MassageAction =ErrorType.UNABLE_TO_GET_COUPONS_BY_PRICE+" and Customer id : "+customerId;
        List<Coupon> queryCustomerCoupon = (List<Coupon>) super.readInDataBase(getAllCouponCompaniesSQLstatement, MassageAction,
                "Coupon", customerId, price);
        return queryCustomerCoupon;
    }
  /**
     * <ul>
     * <li>The method puts a specific coupon in a specific customer in a Join Table in  DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param customerId customer id
     * @param couponId   coupon id
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @see Coupon
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void purchaseCoupon(long customerId, long couponId) throws DAOException {
        String purchaseCoupon
                = "INSERT INTO CUSTOMERS_COUPONS (CUSTOMER_ID,COUPON_ID) "
                + "VALUES(?,?) ";
        String MassageAction = ErrorType.UNABLE_TO_PURCHASE_COUPON_BY_CUSTOMER
        		+"customer id : "+customerId+" and coupon id : "+couponId;
        super.actionOnDataBase(purchaseCoupon, MassageAction, customerId, couponId);
    }
    
       /**
     * <ul>
     * <li>The method removes a coupon id from the join table from all customers in the DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param couponId  id to remove coupon id
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void removeCouponfromCustomersCoupon(long couponId) throws DAOException {
        String removeCustomerJoinSQLstatement
                = "DELETE  FROM CUSTOMERS_COUPONS" + " WHERE " + "COUPON_ID=?";
        String MassageAction = ErrorType.UNABLE_TO_REMOVE_COUPON+"from all customers";
        super.actionOnDataBase(removeCustomerJoinSQLstatement, MassageAction + " From join", couponId);
    }
   /**
     * <ul>
     * <li>The method removes all the coupons from a received Customer id in the Join table in DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param customerId id to remove customer
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void removeCouponsfromCustomersCoupon(long customerId) throws DAOException {
        String removeCustomerJoinSQLstatement
                = "DELETE FROM CUSTOMERS_COUPONS WHERE CUSTOMER_ID=?";
        String MassageAction =ErrorType.UNABLE_TO_REMOVE_COUPON+" From customer id : "+customerId;
        super.actionOnDataBase(removeCustomerJoinSQLstatement, MassageAction + "From join", customerId);
    }
   /**
     * <ul>
     * <li>The method removes a specific customer from a specific coupon in the DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param customerId id to remove coupon from customer
     * @param couponId the coupon to be removed
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void removeCustomerCouponConnection(long customerId, long couponId) throws DAOException {
        String removeCustomerJoinSQLstatement = "DELETE FROM CUSTOMERS_COUPONS "
                + "WHERE CUSTOMER_ID=? AND COUPON_ID=?";
        String MassageAction =ErrorType.UNABLE_TO_REMOVE_COUPON+
        		" From customer id : "+customerId+"a coupon with id"+couponId;
        super.actionOnDataBase(removeCustomerJoinSQLstatement, MassageAction, customerId, couponId);
    }
   /**
     * <ul>
     * <li>The method removes all the coupons of specific company from all customers in the DB
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param companyId  id to remove coupons
     * @see DataBaseDBDAO
     * @see CustomerDAO
     * @see Customer
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void removeCouponsOfCompanyFromCouponsOfCustomer(long companyId) throws DAOException {
        String removeCompanySQLstatement = "DELETE FROM CUSTOMERS_COUPONS WHERE  "
                + "CUSTOMERS_COUPONS.COUPON_ID IN ( SELECT COUPON_ID FROM COMPANIES_COUPONS WHERE "
                + "COMPANIES_COUPONS.COMPANY_ID=?)";
        String MassageAction = ErrorType.UNABLE_TO_REMOVE_COUPON
        		+" : Coupons of Company id : "+companyId+" from all customers that has been purchased";
        super.actionOnDataBase(removeCompanySQLstatement, MassageAction, companyId);
    }

}
