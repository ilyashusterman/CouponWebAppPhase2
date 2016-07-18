package dbdao;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import dao.CompanyDAO;
import exceptions.DAOException;
import exceptions.ErrorType;
import servicebeans.Company;
import servicebeans.Coupon;
import servicebeans.CouponType;

/**
 *This is CompanyDBDAO class implementation of CompanyDAO is creating queries 
 * and execute them in DB and requesting for Data from DB
 * @see DataBaseDBDAO
 * @see CompanyDAO
 * @author ilya shusterman
 * @since version 1.00
 */
@SuppressWarnings("unchecked")//getting ===> Company/Coupon objects
public class CompanyDBDAO extends DataBaseDBDAO implements CompanyDAO {

    
    //Class Fields
    /**
     * Creating Company Table SQL statement using constraints
     */
    public static String createCompTable ="CREATE TABLE COMPANIES "
            + "(ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY"
            + "(START WITH 1, INCREMENT BY 1),"
            + " COMP_NAME VARCHAR(30) UNIQUE NOT NULL, "
            + " PASSWORD VARCHAR(28) NOT NULL, "
            + " EMAIL VARCHAR(70) NOT NULL)";
/**
 * Creating Join table that holds 2 primary keys Company=>Coupon OneToMany!
 */
    private static String joinCompanyCouponTable
            ="CREATE TABLE COMPANIES_COUPONS("
            + "COUPON_ID INTEGER, "
            + " COMPANY_ID INTEGER , " + " PRIMARY KEY "
            + "( COMPANY_ID,COUPON_ID))";
    /**
     * Instantiating the Tables from the Class only once! Creating Tables
     * operations must not be invoked by the user
     */
    public static boolean isTableCreated = false;

    /** <ul><li>This method approves creating table</ul>
     *Instantiating the Tables from the Class only once! Creating Tables
     * operations must not be invoked by the user
     * @return boolean value
     */
    @Override
    public boolean isTableCreated() {
        if (!isTableCreated) {
            isTableCreated = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     *  <ul><li>This constructor approves creating table</ul>
     *Instantiating the Tables from the Class only once! Creating Tables
     * operations must not be invoked by the user
     * @param init instantiating if create a table or not
     * @throws DAOException DAOException
     * @see DataBaseDBDAO constructor
     */
    public CompanyDBDAO(boolean init) throws DAOException {
        super(init, "Company", createCompTable, joinCompanyCouponTable, new CompanyDBDAO());
    }

    /**
     * No argument constructor for using the the class implementations
     */
    public CompanyDBDAO() {
        super();
    }

    /**
     * <ul>
     * <li>The method Creates a received Company in the DB
     * <li>using String prepared statement and executing  
     * with connection more on
     * </ul>
     * @param company Company to be created
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void creatCompany(Company company) throws DAOException {
        String addCompanySQLstatement = "INSERT INTO COMPANIES "
                + "(COMP_NAME, PASSWORD, EMAIL) " + "VALUES (?, ?, ?)";
        String MassageAction = "Company";
        try{
        super.actionOnDataBase(addCompanySQLstatement, MassageAction,
                company.getCompName(), company.getPassword(), company.getEmail());
    	}catch(NullPointerException e){
    		throw new DAOException(ErrorType.COMPANY_VALUES_ARE_NOT_VALID,e);
    	}
    }

    /**<ul>
     * <li>The method removes a Company from a received Company id in the DB
     * <li>using String prepared statement and executing  
     * with connection
     * </ul>
     *@param companyId company id
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void removeCompany(long companyId) throws DAOException {
        String removeCompanySQLstatement = "DELETE FROM COMPANIES"
                + " WHERE " + "ID=?";
        String MassageAction = ErrorType.UNABLE_TO_REMOVE_COMPANY.toString();
        super.actionOnDataBase(removeCompanySQLstatement, MassageAction, companyId);
    }

    /**<ul>
     *<li> The method updates a received Company id in the DB using fields of email and password
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param companyId where to update
     * @param email what to update
     * @param password what to update
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void updateCompany(long companyId, String password, String email) throws DAOException {
        String updateCompanySQLstatement
                = "UPDATE COMPANIES SET PASSWORD=? , EMAIL=? WHERE ID=?";
        String MassageAction = "Update company";
        System.out.println(MassageAction + " approved");
        super.actionOnDataBase(updateCompanySQLstatement, MassageAction,
                password, email, companyId);
    }

    
    /**
     * <ul>
     *<li> The method Gets a Company from a received Company id in the DB
     * <li>Using String prepared statement and executing  and getting result set of company
     * with connection more on
     * </ul>
     *@param id the company id to get Company Bean
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @see Company
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Company getCompany(long id) throws DAOException {
        String getCompanyByIDSQLstatement = "SELECT * FROM COMPANIES"
                + " WHERE ID=?";
        String MassageAction =ErrorType.UNABLE_TO_GET_COMPANY.toString();
        List<Company> queryCompany
                = (List<Company>) super.readInDataBase(getCompanyByIDSQLstatement, MassageAction, "Company", id);
        return (Company) queryCompany.get(0);
    }

    /**
     * <ul>
     *<li> The method Gets a collection of All the Companies from the DB
     *<li> using String prepared statement and executing  and getting result set of company
     * with connection more on
     * </ul>
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @see Company
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Company> getAllCompanies() throws DAOException {
        String getAllCompanySQLstatement = "SELECT * FROM COMPANIES ";
        String MassageAction = ErrorType.UNABLE_TO_GET_COMPANIES.toString();
        List<Company> queryCompany
                = (List<Company>) super.readInDataBase(getAllCompanySQLstatement, MassageAction, "Company");
        return queryCompany;

    }

    /**
     * <ul>
     *<li> The method Gets a collection of Coupons created by all companies in DB
     * <li>Using String prepared statement and executing  and getting result set of coupon
     * with connection more on
     * </ul>
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @see Coupon
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Coupon> getAllCouponsOfCompanies() throws DAOException {
        String getCouponCompanySQLstatement
                = "SELECT * FROM COUPONS  "
                + "INNER JOIN COMPANIES_COUPONS "
                + "ON COMPANIES_COUPONS.COUPON_ID=COUPONS.ID ";
        String MassageAction =ErrorType.UNABLE_TO_GET_COUPONS.toString()+"Created by Companies";
        List<Coupon> queryCompanyCoupon = (List<Coupon>) 
                super.readInDataBase(getCouponCompanySQLstatement, MassageAction, "Coupon");
        return (Collection<Coupon>) queryCompanyCoupon;
    }

    /**
     * <ul>
     * <li>The method returns boolean of name and password of company
     *<li> using String prepared statement and executing and getting result set of company
     * with connection more on
     * </ul>
     *@param compName company name to check
     * @param password company password to check
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public boolean login(String compName, String password) throws DAOException {
        return getCompanyByNamePassword(compName, password) != null;
    }

    /**
     * <ul>
     * <li>The method Gets a Company where a specific name is located
     *<li> Using String prepared statement and executing and getting result set of company
     * with connection more on
     * </ul>
     * @param compName get company by name
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @see Company
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Company getCompanyByName(String compName) throws DAOException {
        String getLoginCompanySQLstatement
                = "SELECT * FROM COMPANIES WHERE" + " COMP_NAME =?";
        String message =ErrorType.UNABLE_TO_GET_COMPANY.toString();
        List<Company> queryCompany = (List<Company>) super.readInDataBase(getLoginCompanySQLstatement, message, "Company", compName);
        return queryCompany.get(0);
    }
/**
 * <ul>
     * <li>The method Gets a Company where a specific name and password is located
     * <li>Using String prepared statement and executing and getting result set  
     * with connection more on
     * </ul>
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @see Company
     * @throws DAOException DAOException
     * @since version 1.00 
     */
    @Override
    public Company getCompanyByNamePassword(String compName, String password) throws DAOException {
        String getLoginCompanySQLstatement
                = "SELECT * FROM COMPANIES WHERE"
                + " COMP_NAME =? AND PASSWORD=?";
        String MassageAction =ErrorType.UNABLE_TO_GET_COMPANY.toString();
        List<Company> queryCompany = (List<Company>) super.readInDataBase(getLoginCompanySQLstatement, MassageAction, "Company", compName, password);
        return queryCompany.get(0);
    }
/**
 * <ul>
     * <li>The method Gets a Company where a specific id is located
     * <li>Using String prepared statement and executing and getting result set  
     * with connection more on
     * </ul>
     *@param companyId to get Collection of coupons
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Coupon> getCouponsByCompanyID(long companyId) throws DAOException {
        String getAllCouponCompaniesSQLstatement = "SELECT * FROM COUPONS WHERE  "
                + "COUPONS.ID IN ( SELECT COUPON_ID FROM COMPANIES_COUPONS WHERE "
                + "COMPANIES_COUPONS.COMPANY_ID=?)";
        String MassageAction = ErrorType.UNABLE_TO_GET_COUPONS+"By Company ID "+companyId;
        List<Coupon> queryCompanyCoupon = (List<Coupon>) super.readInDataBase(getAllCouponCompaniesSQLstatement, MassageAction,
                "Coupon", companyId);
        return queryCompanyCoupon;
    }
/**<ul>
     * <li>The method Gets a Collection of Coupons id's where a specific company id is located
     * using String prepared statement and executing and getting result set  
     * with connection more on
     * </ul>
     *@param companyId to get Collection of Coupons Id's
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Long> getCouponIdByCompanyID(long companyId) throws DAOException {
        String getAllCouponCompaniesSQLstatement = "SELECT COUPON_ID "
                + "FROM COMPANIES_COUPONS WHERE "
                + "COMPANIES_COUPONS.COMPANY_ID=?";
        String MassageAction = "Get Coupons from Company";
        List<Long> queryCompanyCouponId = (List<Long>) super.readInDataBase(getAllCouponCompaniesSQLstatement, MassageAction,
                "Long", companyId);
        return queryCompanyCouponId;
    }
/**
 * <ul>
     * <li>The method Creates a Coupon Id where a specific company id is located
     * <li>Using String prepared statement and executing 
     * with connection more on
     * </ul>
     *@param companyId to get location to set coupon id
     * @param couponId  the coupon id to be set
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void createCouponToCompanyandCoupons(long couponId, long companyId) throws DAOException {
        String createCoupontoCompanySQL = "INSERT INTO COMPANIES_COUPONS"
                + " (COMPANY_ID,COUPON_ID) VALUES(?,?)";
        String massageAction =ErrorType.UNABLE_TO_CREATE_COUPON_BY_COMPANY+" company id"+companyId;
        super.actionOnDataBase(createCoupontoCompanySQL, massageAction, companyId, couponId);
    }
/**
 * <ul>
     * <li>The method Removes a Coupon Id 
     * <li>Using String prepared statement and executing 
     * with connection more on
     * </ul>
     * @param couponId  the coupon id to be remove
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void removeCouponFromCompanyCoupons(long couponId) throws DAOException {
        String removeCouponCouponSQLstatement
                = "DELETE  FROM "
                + " COMPANIES_COUPONS" + " WHERE "
                + "COUPON_ID=?";
        String massageAction = ErrorType.UNABLE_TO_REMOVE_COUPON+"from Company";
        super.actionOnDataBase(removeCouponCouponSQLstatement, massageAction, couponId);
    }
/**
 * <ul>
     * <li>The method Removes a all Coupons where id of company and coupon id 
     * <li>using String prepared statement and executing 
     * with connection more onS
     * </ul>
     * @param couponId  the coupon id 
     * @param companyId the company id
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void removeCompanyFromCompanyCoupons(long companyId, long couponId) throws DAOException {
        String removeCompanyCouponSQLstatement = "DELETE FROM COMPANIES_COUPONS "
                + "WHERE COMPANY_ID=? AND COUPON_ID=?";
        String MassageAction = ErrorType.UNABLE_TO_REMOVE_COMPANY+"Company-Coupons with coupon if of "+couponId;
        super.actionOnDataBase(removeCompanyCouponSQLstatement, MassageAction + " from join table", companyId, couponId);
    }
/**<ul>
     * <li>The method Removes a all Coupons from the company
     * <li>Using String prepared statement and executing 
     * with connection more on
     * </ul>
     * @param companyId   the company id to remove the coupons from the company
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void removeCouponsFromCompanyCoupons(long companyId) throws DAOException {
        String removeCompanyCouponSQLstatement
                = "DELETE FROM COMPANIES_COUPONS"
                + " WHERE " + "COMPANY_ID=?";
        String MassageAction = ErrorType.UNABLE_TO_REMOVE_COMPANY+"Company-Coupons";
        super.actionOnDataBase(removeCompanyCouponSQLstatement, MassageAction + " from join table", companyId);
    }
/**
 * <ul>
     * <li>The method Filter Gets a Collection of Coupons id's where a price is up to and from a company id
     * <li>Using String prepared statement and executing and getting result set  
     * with connection more on
     * </ul>
     *@param companyId to get Collection of Coupons from
     * @param price up to price
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Coupon> getCouponsByCompanyIDbyPrice(double price, long companyId) throws DAOException {
        String getAllCouponCompaniesSQLstatement = "SELECT * FROM COUPONS WHERE  "
                + "COUPONS.ID IN ( SELECT COUPON_ID FROM COMPANIES_COUPONS WHERE "
                + "COMPANIES_COUPONS.COMPANY_ID=?) AND PRICE <=?";
        String MassageAction = ErrorType.UNABLE_TO_GET_COUPONS_BY_PRICE+"";
        List<Coupon> queryCompanyCoupon = (List<Coupon>) super.readInDataBase(getAllCouponCompaniesSQLstatement, MassageAction,
                "Coupon", companyId, price);
        return queryCompanyCoupon;
    }
/**<ul>
     *<li> The method Filter Gets a Collection of Coupons id's where a date is up to and from a company id
     *<li> using String prepared statement and executing and getting result set  
     * with connection more on
     * </ul>
     *@param companyId to get Collection of Coupons from
     * @param date  up to date
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Coupon> getCouponsByCompanyIDbyDate(Timestamp date, long companyId) throws DAOException {
        String getAllCouponCompaniesSQLstatement = "SELECT * FROM COUPONS WHERE  "
                + "COUPONS.ID IN ( SELECT COUPON_ID FROM COMPANIES_COUPONS WHERE "
                + "COMPANIES_COUPONS.COMPANY_ID=?) AND END_DATE <=?";
        String MassageAction = ErrorType.UNABLE_TO_GET_COUPONS_BY_DATE+"";
        List<Coupon> queryCompanyCoupon = (List<Coupon>) super.readInDataBase(getAllCouponCompaniesSQLstatement, MassageAction,
                "Coupon", companyId, date);
        return queryCompanyCoupon;
    }
/**<ul>
     *<li> The method Filter Gets a Collection of Coupons id's where a type is, and from a company id
     * <li>Using String prepared statement and executing and getting result set  
     * with connection more on
     * </ul>
     *@param companyId to get Collection of Coupons from
     * @param type where a type is
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Coupon> getCouponsByCompanyIDbyType(CouponType type, long companyId) throws DAOException {
        String getAllCouponCompaniesSQLstatement = "SELECT * FROM COUPONS WHERE  "
                + "COUPONS.ID IN ( SELECT COUPON_ID FROM COMPANIES_COUPONS WHERE "
                + "COMPANIES_COUPONS.COMPANY_ID=?) AND TYPE =?";
        String MassageAction = ErrorType.UNABLE_TO_GET_COUPONS_BY_TYPE.toString();
        List<Coupon> queryCompanyCoupon = (List<Coupon>) super.readInDataBase(getAllCouponCompaniesSQLstatement, MassageAction,
                "Coupon", companyId, type.toString());
        return queryCompanyCoupon;
    }
/**
 * <ul>
     * <li>The method Removes a all Coupons from Coupons table of specific company
     * <li>Using String prepared statement and executing 
     * with connection more 
     * </ul>
     * @param companyId   the company id to remove the coupons from the company
     * @see DataBaseDBDAO
     * @see CompanyDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void removeCouponsOfCompanyFromCoupons(long companyId) throws DAOException {
        String removeCompanySQLstatement = "DELETE FROM COUPONS WHERE  "
                + "COUPONS.ID IN ( SELECT COUPON_ID FROM COMPANIES_COUPONS WHERE "
                + "COMPANIES_COUPONS.COMPANY_ID=?)";
        String MassageAction =ErrorType.UNABLE_TO_REMOVE_COUPON+" from Company";
        super.actionOnDataBase(removeCompanySQLstatement, MassageAction, companyId);
    }

}
