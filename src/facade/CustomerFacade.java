package facade;

import java.sql.Timestamp;
import java.util.Collection;

import exceptions.FacadeException;
import servicebeans.Coupon;
import servicebeans.CouponType;
import servicebeans.Customer;

/**
 *<ul><li>This class CustomerFacade gives all operations that Customer can do in the system</ul>
 * @since version 1.00
 * @author ilya
 *
 */
public interface CustomerFacade extends CouponClientFacade {
	
    /**
     *
     * @return Collection(Coupon) all to purchase
     * @throws FacadeException FacadeException
     */
    Collection<Coupon> showAllCoupons() throws FacadeException;
	
    /**
     *
     * @param couponId couponId
     * @return boolean value
     * @throws FacadeException FacadeException
     */
    boolean purchaseCoupon(long couponId) throws FacadeException ;

    /**
     *
     * @return Collection(Coupon) that purchased by customer
     * @throws FacadeException FacadeException
     */
    Collection<Coupon> getAllPurchasedCoupons() throws FacadeException;

    /**
     *
     * @param  price  price
     * @return Collection(Coupon) by price 
     * @throws FacadeException FacadeException
     */
    Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws FacadeException;
		
    /**
     *
     * @param couponType type
     * @return Collection(Coupon) by type
     * @throws FacadeException FacadeException
     */
    Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws FacadeException;
		
    /**
     *
     * @return Customer
     * @throws FacadeException FacadeException
     */
    Customer getTheCustomer() throws FacadeException;
/**
 *  returns the purchased coupons of the customer by the date
 * @param date the date 
 * @return collections of coupons
 * @throws FacadeException  FacadeException
 */
	Collection<Coupon> getAllPurchasedCouponsByDate(Timestamp date) throws FacadeException;
}
