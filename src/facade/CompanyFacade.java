package facade;

import java.sql.Timestamp;
import java.util.Collection;
import exceptions.FacadeException;
import servicebeans.Company;
import servicebeans.Coupon;
import servicebeans.CouponType;

/**
 *<ul><li>This class CompanyFacade gives all operations that Company can do in the system</ul>
 * @since version 1.00
 * @author ilya
 */
public interface CompanyFacade extends CouponClientFacade {

    /**
     *
     * @return Collection of coupons
     * @throws FacadeException FacadeException
     */
    Collection<Coupon> getCouponsOfCompany() throws FacadeException;

    /**
     *
     * @param date date 
     * @return Collection of coupons up to date
     * @throws FacadeException FacadeException
     */
    Collection<Coupon> getCouponByDate(Timestamp date) throws FacadeException;

    /**
     *
     * @param couponType couponType
     * @return  Collection of coupons by type
     * @throws FacadeException FacadeException
     */
    Collection<Coupon> getCouponByType(CouponType couponType) throws FacadeException;
	
    /**
     *
     * @param price price 
     * @return Collection of coupons up to price
     * @throws FacadeException FacadeException
     */
    Collection<Coupon>getCouponUpToPrice(double price) throws FacadeException;

    /**
     *
     * @param coupon coupon to create
     * @throws FacadeException FacadeException
     */
    void creatCoupon(Coupon coupon) throws FacadeException;

    /**
     *
     * @param couponId couponId
     * @throws FacadeException FacadeException
     */
    void removeCoupon(long couponId) throws FacadeException;
	
    /**
     *
     * @param couponId couponId
     * @param endDate endDate
     * @param price  price
     * @throws FacadeException FacadeException
     */
    void updateCoupon(long couponId, Timestamp endDate, double price) throws FacadeException;

    /**
     *
     * @return Company
     * @throws FacadeException FacadeException
     */
    Company getTheCompany() throws FacadeException;
}
