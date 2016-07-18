package facadedb;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import dao.CouponDAO;
import dao.CustomerDAO;
import dbdao.CouponDBDAO;
import dbdao.CustomerDBDAO;
import exceptions.DAOException;
import exceptions.ErrorType;
import exceptions.FacadeException;
import facade.CouponClientFacade;
import facade.CustomerFacade;
import servicebeans.Coupon;
import servicebeans.CouponType;
import servicebeans.Customer;

/**
 * <ul>
 * <li>This is CustomerFacadeDB class implementation of CustomerFacade is the
 * logic behind customer operations uses the DAO layers to execute the
 * operations
 * <li>CustomerFacadeDB is a singleton if not done login '
 * <li>Implements the CouponClientFacade through CustomerFacade gives Login
 * method to get CustomerFacade full operations</ul>
 *
 * @see CouponClientFacade
 * @see CustomerFacade
 * @see CouponDAO
 * @see CustomerDAO
 * @see Customer
 * @see Coupon
 * @author ilya shusterman
 * @since version 1.00
 */
public class CustomerFacadeDB implements CustomerFacade {

    /**
     * <li>Customer facade client to use Login method
     */
    private volatile static CustomerFacadeDB facade;
    /**
     * <li>DAO layer use - CustomerDAO
     *
     * @see CustomerDAO
     */
    private CustomerDAO customerDAO;
    /**
     * <li>DAO layer use - CouponDAO
     *
     * @see CouponDAO
     */
    private CouponDAO couponDAO;
    /**
     * <li>bean use - Customer
     *
     * @see Customer
     */
    private Customer theCustomer;

    /**
 * <ul><li>No argument constructor for full use of operations when login commit successfully</ul>
 */
    private CustomerFacadeDB() throws FacadeException {
        this.customerDAO = new CustomerDBDAO();
        this.couponDAO = new CouponDBDAO();
    }
/**
 * <ul><li>constructor for use of CustomerFacadeDB.getInstance() to use login(..) operation </ul>
 * @param system 
 * @see CustomerFacadeDB.getInstance
 */
    private CustomerFacadeDB(boolean system) {}
/**
 * <ul><li>Getting an instance and creates only 1 static instance</ul>
 * @return CustomerFacadeDB facade to use only login
 */
    public static CustomerFacadeDB getInstance() {
        if (facade == null) {
            synchronized (CustomerFacadeDB.class) {
                if (facade == null) {
                    facade = new CustomerFacadeDB(true);
                }
            }
        }
        return facade;
    }
/**
 *  <ul><li>This get the Customer bean from the Customer facade </ul>
 * @return Customer
 * @see Customer
 * @throws FacadeException FacadeException
 */
    @Override
    public Customer getTheCustomer() throws FacadeException {
        try {
            return customerDAO.getCustomer(getId());
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
/**
 * <ul><li>If the client commit authorized login will get Id of the Customer<ul><li>if not will get an exception of not logged in</ul></ul>
 * @return
 * @throws FacadeException 
 */
    private long getId() throws FacadeException {
        if (theCustomer == null) {
            throw new FacadeException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        return theCustomer.getId();
    }
/**
 * <ul><li>This Sets the Customer bean to class </ul>
 * @param theCustomer 
 * @see Customer
 */
    private void setTheCustomer(Customer theCustomer) {
        this.theCustomer = theCustomer;
    }
/**
 * <ul><li>Login method to get a full operational CustomerFacade if done successfully</ul>
 * @param custname name
 * @param password password
 * @return  CouponClientFacade
 * @throws FacadeException  FacadeException
 */
    @Override
    public CouponClientFacade login(String custname, String password) throws FacadeException {
        CustomerFacadeDB facade = null;
        CustomerDAO checkCustomerDAO = new CustomerDBDAO();
        try {
            if (checkCustomerDAO.login(custname, password)) {
                facade = new CustomerFacadeDB();
                facade.setTheCustomer(checkCustomerDAO.getCustomerByNamePassword(custname, password));
            } else {
                throw new FacadeException(ErrorType.USERNAME_OR_PASSWORD_DOES_NOT_MATCH);
            }
        } catch (DAOException e) {
            throw new FacadeException(ErrorType.UNABLE_TO_CONFIRM_LOGIN, e);
        }
        return facade;
    }
    /**
     * <ul><li>This method return all the coupons that a customer can see to purchase </ul>
     * @return Collection of coupons
     * @throws FacadeException  FacadeException
     * @see Coupon
     */

    @Override
    public Collection<Coupon> showAllCoupons() throws FacadeException {
        try {
            return couponDAO.getAllCoupon();
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
/**
 * This method is purchasing coupon to the Customer in the CustoemrFacade class 
 * Only if NOT already purchased
 * Only if NOT out of stock
 * Only if NOT Coupon expired
 * @param couponId coupon id to purchase
 * @return true or throws exception
 * @throws FacadeException  FacadeException
 */
    @Override
    public boolean purchaseCoupon(long couponId) throws FacadeException {
        Coupon theCoupon = null;
        try {
            theCoupon = couponDAO.getCoupon(couponId);
        } catch (DAOException e) {
            throw new FacadeException(ErrorType.UNABLE_TO_GET_COUPON+" with id :" + couponId, e);
        }
        if (theCoupon == null) {
            throw new FacadeException(ErrorType.COUPON_DOES_NOT_EXISTS+" with id of :" + couponId);
        }
        if (theCoupon.getAmount() <= 0) {
            throw new FacadeException(ErrorType.COUPON_OUT_OF_STOCK);
        }
        if ((new Timestamp((new Date()).getTime())).after(theCoupon.getEndDate())) {
            throw new FacadeException(ErrorType.COUPON_DATE_HAS_EXPIRED);
        }
        try {
            customerDAO.purchaseCoupon(getId(), couponId);
            couponDAO.updateCouponAmount(couponId);//FIXME exception
            return true;
        } catch (DAOException e) {
            throw new FacadeException(ErrorType.UNABLE_TO_PURCHASE_COUPON_BY_CUSTOMER+
            		" Coupon id : " + couponId+ " , to Customer " + getTheCustomer().getCustName(), e);
        }
    }
/**
 * This method return all purchased coupons by the Customer in customer facade
 * @return Collection of Coupons
 * @throws FacadeException  FacadeException
 * @see Coupon
 */
    @Override
    public Collection<Coupon> getAllPurchasedCoupons() throws FacadeException {
        try {
            return (Collection<Coupon>) customerDAO.getCouponsByCustomerID(getId());
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
/**
 * <ul><li>This method return all purchased coupons by the Customer in customer facade up to price</ul>
 * @param price price
 * @return Collection of Coupons up to price
 * @throws FacadeException  FacadeException
 */
    @Override
    public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws FacadeException {
        try {
            return customerDAO.getCouponsByCompanyIDbyPrice(price, getId());
        } catch (DAOException e) {
            throw new FacadeException(e);
        }

    }
/**
 * <ul><li>This method return all purchased coupons by the Customer in customer facade with type</ul>
 * @param couponType type
 * @return Collection of Coupons by type
 * @throws FacadeException FacadeException
 */
    @Override
    public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws FacadeException {
        try {
            return customerDAO.getCouponsByCustomerIDbyType(couponType, getId());
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
    
    @Override
    public Collection<Coupon> getAllPurchasedCouponsByDate(Timestamp date) throws FacadeException {
        try {
            return customerDAO.getCouponsByCustomerIDbyDate(date, getId());
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }

}
