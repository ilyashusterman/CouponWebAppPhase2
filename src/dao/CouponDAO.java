package dao;

import java.sql.Timestamp;
import java.util.Collection;
import exceptions.DAOException;
import servicebeans.Coupon;

/**
 *
 * @author ilya shusterman
 * @since version 1.00
 */
public interface CouponDAO {

    /**
     * Creating Coupon(String title, Timestamp startDate, Timestamp endDate,
     * integer amount, CouponType type, String massage, double price, String
     * imgPath ) with the fields to the Coupons Table
     *
     * @param coupon  coupon
     * @throws DAOException DAOException
     */
    void creatCoupon(Coupon coupon) throws DAOException;

    /**
     * Removing a Coupon from the database with a specific ID
     *
     * @param couponId couponId
     * @throws DAOException DAOException
     */
    void removeCoupon(long couponId) throws DAOException;

    /**
     *Updating Coupon with Fields (can get an Entire Coupon end use its fields)
     *
     * @param couponId couponId
     * @param endDate end date 
     * @param price price
     * @throws DAOException DAOException
     */
    void updateCoupon(long couponId, Timestamp endDate, double price) throws DAOException;

    /**
     * Getting Coupon from DataBase with id of
     *
     * @param id coupon id
     * @return Coupon
     * @throws DAOException DAOException
     */
    Coupon getCoupon(long id) throws DAOException;

    /**
     * Getting All Coupons registered/Created in the DataBase
     *
     * @return Collection of Coupon
     * @throws DAOException DAOException
     */
    Collection<Coupon> getAllCoupon() throws DAOException;

    /**
     * Getting Coupon with specific title only 1 coupon allowed!
     *
     * @param title title 
     * @return Coupon
     * @throws DAOException DAOException
     */
    Coupon getCouponbyTitle(String title) throws DAOException;

    /**
     * Updating the Coupon amount where the id is
     *
     * @param couponId couponId
     * @throws DAOException DAOException
     */
    void updateCouponAmount(long couponId) throws DAOException;

}
