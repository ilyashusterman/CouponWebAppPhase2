package dao;

import java.sql.Timestamp;
import java.util.Collection;
import exceptions.DAOException;
import servicebeans.Company;
import servicebeans.Coupon;
import servicebeans.CouponType;

/**
 *
 * @author ilya shusterman
 * @since version 1.00
 */
public interface CompanyDAO {

    /**
     *
     * @param company Creates Company with Company(String compName, String
     * password, String email) fields using SQL query to DataBase;
     * @throws DAOException DAOException
     */
    void creatCompany(Company company) throws DAOException;

    /**
     *deletes Company from database with specific ID in DB
     *
     * @param companyId company id
     * @throws DAOException DAOException
     */
    void removeCompany(long companyId) throws DAOException;

    /**
     *  updates using fields where id is companyId in DB (can get an Entire
     * Company end use its fields)
     *
     * @param companyId company id
     * @param password company password
     * @param email company email
     * @throws DAOException DAOException
     */
    void updateCompany(long companyId, String password, String email) throws DAOException;

    /**
     * finding In DB company of ID
     *
     * @param id of company
     * @return Company
     * @throws DAOException DAOException
     */
    Company getCompany(long id) throws DAOException;

    /**
     * Getting all companies in DB in COMPANIES table each row specifies a
     * company
     *
     * @return Collection(Company)
     * @throws DAOException DAOException
     */
    Collection<Company> getAllCompanies() throws DAOException;

    /**
     * getting all Coupons created that created from Companies coupons that
     * created out side the system are not allowed to be created for other
     * purposes except testing! IMPORTANT NOTE !!! THIS METHOD IS
     * NOT USED AT ALL MIGHT JUST FOR PURPOSE OF TESTING THIS
     * METHODE IS NOT CRUCIAL!!!
     *
     * @return Collection(Coupon)
     * @throws DAOException DAOException
     */
    Collection<Coupon> getAllCouponsOfCompanies() throws DAOException;

    /**
     * Getting from COMPANIES table specific Company (in a row) with id of
     *
     * @param id of company
     * @return Collection(Coupon)
     * @throws DAOException DAOException
     */
    Collection<Coupon> getCouponsByCompanyID(long id) throws DAOException;

    /**
     * Getting from COMPANIES table specific Company (in a row) with name of
     *
     * @param name company name
     * @return Company
     * @throws DAOException DAOException
     */
    Company getCompanyByName(String name) throws DAOException;

    /**
     * Getting from COMPANIES table specific Company (in a row) with name and
     * password of
     *
     * @param compName company name
     * @param password company password
     * @return Company
     * @throws DAOException DAOException
     */
    Company getCompanyByNamePassword(String compName, String password) throws DAOException;

    /**
     * Putting an ID of Coupon inside a JOIN table of COMPANIES_COUPONS when a
     * company decides to create a coupon this MUST be invoked each time a
     * coupon is created
     *
     * @param couponId coupon id
     * @param companyId company id
     * @throws DAOException DAOException
     */
    void createCouponToCompanyandCoupons(long couponId, long companyId) throws DAOException;

    /**
     * Remove the coupon ID from the JOIN table of COMPANIES_COUPONS when a
     * company decides to remove a coupon this MUST be invoked each time a
     * coupon is removed
     *
     * @param couponId coupon id
     * @throws DAOException DAOException
     */
    void removeCouponFromCompanyCoupons(long couponId) throws DAOException;

    /**
     * This method is for removing Company if id of company and id of coupon is
     * located in DB DID NOT USED THIS METHOD YET BUT MIGHT BE
     * USED IN UI SECOND PHASE FEATURES
     *
     **********
     * @param companyId company id 
     * @param couponId coupon id
     * @throws DAOException DAOException
     */
    void removeCompanyFromCompanyCoupons(long companyId, long couponId) throws DAOException;

    /**
     * Removing all Coupons ID's from JOIN table COMPANIES_COUPONS with the id
     * of company
     *
     * @param companyId company id
     * @throws DAOException DAOException
     */
    void removeCouponsFromCompanyCoupons(long companyId) throws DAOException;

    /**
     * Login method with Company Name and company password gives an boolean
     * value ONLY IF located in database or not
     *
     * @param compName company name 
     * @param password company password
     * @return boolean value
     * @throws DAOException DAOException
     */
    boolean login(String compName, String password) throws DAOException;

    /**
     * Getting Coupons of Company with id and by filter of Type
     *
     * @param type type
     * @param companyId company id
     * @return Collection(Coupon) by type
     * @throws DAOException DAOException
     */
    Collection<Coupon> getCouponsByCompanyIDbyType(CouponType type, long companyId) throws DAOException;

    /**
     *Getting Coupons of Company with id and by filter of Price
     *
     * @param price price   
     * @param companyId company id
     * @return Collection(Coupon) up to price
     * @throws DAOException  DAOException
     */
    Collection<Coupon> getCouponsByCompanyIDbyPrice(double price, long companyId) throws DAOException;

    /**
     * Getting Coupons of Company with id and by filter of Date in Timestamp
     *
     * @param date date 
     * @param companyId companyId
     * @return Collection(Coupon) up to date
     * @throws DAOException DAOException
     */
    Collection<Coupon> getCouponsByCompanyIDbyDate(Timestamp date, long companyId) throws DAOException;

    /**
     * Removing all Coupons in COUPONS Table which have the same id located in
     * the Join table with the ID of specific company
     *
     * @param companyId companyId
     * @throws DAOException DAOException
     */
    void removeCouponsOfCompanyFromCoupons(long companyId) throws DAOException;

    /**
     * Getting All Coupons in COUPONS table with the ID's located in JOIN table
     * with the company id all the Coupons created by the Company
     *
     * @param companyId companyId
     * @return Collection(long)
     * @throws DAOException DAOException
     */
    Collection<Long> getCouponIdByCompanyID(long companyId) throws DAOException;

}
