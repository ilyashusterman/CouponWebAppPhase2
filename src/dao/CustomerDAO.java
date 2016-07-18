package dao;

import java.sql.Timestamp;
import java.util.Collection;
import exceptions.DAOException;
import servicebeans.Coupon;
import servicebeans.CouponType;
import servicebeans.Customer;

/**
 *
 * @author ilya shusterman
 * @since version 1.00
 */
public interface CustomerDAO {
	
    /**
     *<ul><li> Creates Customer with Customer(String custName, String password)</ul>
     * @param customer customer parameters
     * fields using SQL query to DataBase;
     * @throws  DAOException DAOException
     */
    void creatCustomer(Customer customer)throws DAOException;

    /**
     *deletes Customer from database with specific ID in DB
     * @param customerId customerId
     * @throws DAOException  DAOException
     */
    void removeCustomer(long customerId)throws DAOException;

    /**
     *<ul><li> updates using password field where id is customerId in DB (can get an Entire Customer end use its fields)</ul>
     * @param customerId customerId
     * @param password password
     * @throws DAOException DAOException
     */
    void updateCustomer(long customerId,String password)throws DAOException;

    /**
     *<ul><li> finding In DB Customer of ID</ul>
     * @param id id
     * @return Customer
     * @throws DAOException DAOException
     */
    Customer getCustomer(long id)throws DAOException;

    /**
     *<ul><li> Getting from CUSTOMERS table specific Customer (in a row) with name and password </ul>
     * @param custName custName
     * @param password password
     * @return Customer
     * @throws DAOException DAOException
     */
    Customer getCustomerByNamePassword(String custName, String password)throws DAOException;
	
    /**<ul><li> 
     *Getting from CUSTOMERS table specific Customer (in a row) with name of</ul>
     * @param name name
     * @return Customer
     * @throws DAOException DAOException
     */
    Customer getCustomerByName(String name) throws DAOException;
	
    /**<ul><li> 
     *Getting all customers in DB in CUSTOMERS table each row specifies a customer</ul>
     * @return Collection(Customer) 
     * @throws DAOException DAOException
     */
    Collection<Customer> getAllCustomers()throws DAOException;
	
    /**<ul><li> 
     *Getting all coupons that are purchased (by all customers)in DB in COUPONS table each row specifies a customer</ul>
     * @return Collection(Coupon)
     * @throws DAOException DAOException
     */
    Collection<Coupon> getAllpurchasedCouponsByAllCust()throws DAOException;
	
    /**<ul><li> 
     * Login method with Customer Name and Customer password gives an boolean value
     * ONLY IF located in database  or not</ul>
     * @param custName custName
     * @param password password
     * @return boolean value
     * @throws DAOException DAOException
     */
    boolean login(String custName, String password)throws DAOException;

    /**<ul><li> 
     *Getting All Coupons in COUPONS table with the ID's located in JOIN table with the customer id
     * all the Coupons purchased by the Customer</ul>
     * @param id id
     * @return Collection(Coupon)
     * @throws DAOException DAOException
     */
    Collection<Coupon> getCouponsByCustomerID(long id)throws DAOException;

    /**<ul><li> 
     *Putting an ID of Coupon in the Join table of CUSTOMERS_COUPONS to specific customer
     * with id </ul>
     * @param customerId customerId
     * @param couponId couponId
     * @throws DAOException DAOException
     */
    void purchaseCoupon(long customerId, long couponId) throws DAOException;

    /**<ul><li> 
     * Removing the Coupon From all Customers that purchased it in Join Table</ul>
     * @param couponId couponId
     * @throws DAOException DAOException
     */
    void removeCouponfromCustomersCoupon(long couponId) throws DAOException;
	
    /**<ul><li> 
     *Removing all coupons that purchased in a specific customer with id </ul>
     * @param customerId customerId
     * @throws DAOException DAOException
     */
    void removeCouponsfromCustomersCoupon(long customerId) throws DAOException;
	
    /**<ul><li> 
     *Removing Coupon with id only from a specific customer with id</ul>
     * @param customerId customerId
     * @param couponId couponId
     * @throws DAOException DAOException
     */
    void removeCustomerCouponConnection(long customerId, long couponId) throws DAOException ;

    /**<ul><li> 
     * Getting Coupons of Customer with id and by filter of Type</ul>
     * @param type type
     * @param customerId customerId
     * @return Collection(Coupon) by type
     * @throws DAOException DAOException
     */
    Collection<Coupon> getCouponsByCustomerIDbyType(CouponType type, long customerId) throws DAOException;

    /**<ul><li> 
     *Getting Coupons of customer with id and by filter of Price</ul>
     * @param price price
     * @param customerId customerId
     * @return Collection(Coupon) up to  price
     * @throws DAOException DAOException
     */
    Collection<Coupon> getCouponsByCompanyIDbyPrice(double price, long customerId) throws DAOException;

    /**<ul><li> 
     *removing all Coupons with id's that the Company of id Owns from all Customers </ul>
     * @param customerId customerId
     * @throws DAOException DAOException
     */
    void removeCouponsOfCompanyFromCouponsOfCustomer(long customerId) throws DAOException ;
/**
 * This method returns all the coupons until the date 
 * @param date date of coupon
 * @param customerId if of customer
 * @return Collection of coupons
 * @throws DAOException DAOException
 */
	Collection<Coupon> getCouponsByCustomerIDbyDate(Timestamp date, long customerId) throws DAOException;
	
}
