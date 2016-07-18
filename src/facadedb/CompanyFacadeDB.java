package facadedb;

import java.sql.Timestamp;
import java.util.Collection;
import dbdao.CouponDBDAO;
import dbdao.CustomerDBDAO;
import exceptions.DAOException;
import exceptions.ErrorType;
import exceptions.FacadeException;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import servicebeans.Company;
import servicebeans.Coupon;
import servicebeans.CouponType;
import dao.CompanyDAO;
import dao.CouponDAO;
import dao.CustomerDAO;
import dbdao.CompanyDBDAO;

/**
 * <ul>
 * <li>This is CompanyFacadeDB class implementation of CompanyFacade is the
 * logic behind company operations uses the DAO layers to execute the operations
 * <li>CompanyFacadeDB is a singleton if not done login '
 * <li>Implements the CouponClientFacade through CompanyFacade gives Login
 * method to get CompanyFacade full operations</ul>
 *
 * @see CouponClientFacade
 * @see CompanyFacade
 * @see CompanyDAO
 * @see CouponDAO
 * @see CustomerDAO
 * @see Company
 * @author ilya shusterman
 * @since version 1.00
 */
public class CompanyFacadeDB implements CompanyFacade {
/**
 * <li>Company facade client to use Login method
 */
    private volatile static CompanyFacadeDB facade;
    /**
     * <li>DAO layer use - CompanyDAO
     * @see CompanyDAO
     */
    private CompanyDAO companyDAO;
        /**
     * <li>DAO layer use - CouponDAO
     * @see CouponDAO
     */
    private CouponDAO couponDAO;
        /**
     * <li>DAO layer use - CustomerDAO
     * @see CustomerDAO
     */
    private CustomerDAO customerDAO;
       /**
     * <li>bean use - Company
     * @see Company
     */
    private Company theCompany;
/**
 * <ul><li>No argument constructor for full use of operations when login commit successfully</ul>
 */
    private CompanyFacadeDB() {
        this.companyDAO = new CompanyDBDAO();
        this.couponDAO = new CouponDBDAO();
        this.customerDAO = new CustomerDBDAO();
    }
/**
 * <ul><li>constructor for use of CompanyFacadeDB.getInstance() to use login(..) operation </ul>
 * @param system 
 * @see CompanyFacadeDB.getInstance
 */
    private CompanyFacadeDB(boolean system) {}
/**
 * <ul><li>Getting an instance and creates only 1 static instance</ul>
 * @return CompanyFacadeDB facade to use only login
 */
    public static CompanyFacadeDB getInstance() {
        if (facade == null) {
            synchronized (CompanyFacadeDB.class) {
                if (facade == null) {
                    facade = new CompanyFacadeDB(true);
                }
            }
        }
        return facade;
    }
/**
 * <ul><li>If the client commit authorized login will get Id of the company<ul><li>if not will get an exception of not logged in</ul></ul>
 * @return
 * @throws FacadeException 
 */
    private long getId() throws FacadeException {
        if (theCompany == null) {
            throw new FacadeException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        return theCompany.getId();
    }
/**
 * <ul><li>This Sets the company bean to class </ul>
 * @param company company
 * @see Company
 */
    private void setTheCompany(Company company) {
        this.theCompany = company;
    }
/**
 *  <ul><li>This get the company bean from the company facade </ul>
 * @return Company
 * @see Company
 * @throws FacadeException  FacadeException
 */
    @Override
    public Company getTheCompany() throws FacadeException {
        try {
            return this.companyDAO.getCompany(getId());
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
/**
 *  <ul><li>Creates coupon to the DB
 * <ul><li>creates if title is not found through the constraint of 'Unique'
 * <li>will throw exception if title already used</ul></ul>
 * @param coupon coupon
 * @throws FacadeException  FacadeException
 */
    @Override
    public void creatCoupon(Coupon coupon) throws FacadeException {
    	try {
            couponDAO.creatCoupon(coupon);
            Coupon theCoupon = couponDAO.getCouponbyTitle(coupon.getTitle());
            companyDAO.createCouponToCompanyandCoupons(theCoupon.getId(), getId());
    	} catch (DAOException e) {
    		throw new FacadeException(e);
    	}
    }
/**
 * <ul><li>Removes a coupon from the db with specific id
 * <ul><li>Coupon will be deleted from the company 
 * <li>Coupon will be deleted from purchased customers</ul></ul>
 * @param couponId couponId
 * @throws FacadeException  FacadeException
 */
    @Override
    public void removeCoupon(long couponId) throws FacadeException {
        try {
            companyDAO.removeCouponFromCompanyCoupons(couponId);
            customerDAO.removeCouponfromCustomersCoupon(couponId);
            couponDAO.removeCoupon(couponId);
        } catch (DAOException e) {
            throw new FacadeException(ErrorType.UNABLE_TO_REMOVE_COUPON,e);
        }
    }
/**
 *<ul><li>This method updates coupon with specific id fields:
 * <ul><li>End Date
 * <li>Price</ul></ul>
 * @param couponId couponId
 * @param endDate endDate
 * @param price price
 * @throws FacadeException  FacadeException
 */
    @Override
    public void updateCoupon(long couponId, Timestamp endDate, double price) throws FacadeException {
        try {
            couponDAO.updateCoupon(couponId, endDate, price);
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
/**
 * <ul><li>This method returns a collection of Coupon from the specific company</ul>
 * @return Collection of Coupons
 * @throws FacadeException  FacadeException
 * @see Coupon
 */
    @Override
    public Collection<Coupon> getCouponsOfCompany() throws FacadeException {
        try {
            return (Collection<Coupon>) companyDAO.getCouponsByCompanyID(getId());
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
/**
 * <ul><li>Login method to get a full operational CompanyFacade if done successfully</ul>
 * @param compName name
 * @param password password
 * @return CouponClientFacade
 * @throws FacadeException  FacadeException
 */
    @Override
    public CouponClientFacade login(String compName, String password) throws FacadeException {
        CompanyFacadeDB facade = null;
        CompanyDAO CheckCompanyDAO = new CompanyDBDAO();
        Company company;
        try {
            if ((company = CheckCompanyDAO.getCompanyByNamePassword(compName, password)) != null) {
                facade = new CompanyFacadeDB();
                facade.setTheCompany(company);
            } else {
                throw new FacadeException(ErrorType.USERNAME_OR_PASSWORD_DOES_NOT_MATCH);
            }
        } catch (DAOException e) {
            throw new FacadeException(
                    ErrorType.UNABLE_TO_CONFIRM_LOGIN, e);
        }
        return facade;
    }
/**
 * <ul><li>This method returns a collection of Coupon from the specific company up to Date</ul>
 * @param date date
 * @return collection of coupons up to date
 * @throws FacadeException FacadeException
 * @see Coupon
 */
    @Override
    public Collection<Coupon> getCouponByDate(Timestamp date) throws FacadeException {
        try {
            return companyDAO.getCouponsByCompanyIDbyDate(date, getId());
        } catch (DAOException e) {
            throw new FacadeException(e);
        }

    }
/**
 *  <ul><li>This method returns a collection of Coupon from the specific company of the type</ul>
 * @param couponType type
 * @return collection of coupons by type
 * @throws FacadeException  FacadeException
 * @see Coupon
 */
    @Override
    public Collection<Coupon> getCouponByType(CouponType couponType) throws FacadeException {
        try {
            return companyDAO.getCouponsByCompanyIDbyType(couponType, getId());
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
/**
 *  <ul><li>This method returns a collection of Coupon from the specific company up to Price</ul>
 * @param price price
 * @return Collection of Coupons up to price 
 * @throws FacadeException  FacadeException
 * @see Coupon
 */
    @Override
    public Collection<Coupon> getCouponUpToPrice(double price) throws FacadeException {
        try {
            return companyDAO.getCouponsByCompanyIDbyPrice(price, getId());
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }

}
