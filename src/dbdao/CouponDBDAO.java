package dbdao;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import dao.CouponDAO;
import exceptions.DAOException;
import exceptions.ErrorType;
import servicebeans.Coupon;

/**
 *This is CouponDBDAO class implementation of CouponDAO is creating queries 
 * and execute them in DB and requesting for Data from DB
 * @see DataBaseDBDAO
 * @see CouponDAO
 * @author ilya shusterman
 * @since version 1.00
 */
@SuppressWarnings("unchecked")//getting ===> Coupon objects
public class CouponDBDAO extends DataBaseDBDAO implements CouponDAO {

    /**
     * Creating Coupon Table SQL statement using constraints
     */
    public static String creatCouponTable ="CREATE TABLE COUPONS "
            + "(ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY"
            + "(START WITH 1, INCREMENT BY 1),"
            + " TITLE VARCHAR(30) UNIQUE NOT NULL, " + " "
            + "START_DATE TIMESTAMP NOT NULL, "
            + " END_DATE TIMESTAMP NOT NULL, " + " AMOUNT INTEGER NOT NULL, "
            + " TYPE VARCHAR(30) NOT NULL, " + " MASSAGE VARCHAR(149) NOT NULL, "
            + "" + "PRICE DOUBLE PRECISION NOT NULL," + " IMGPATH VARCHAR(100) NOT NULL)";

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
     * No argument constructor for using the the class implementations
     */
    public CouponDBDAO() {
        super();
    }

    /**
     *  <ul><li>This constructor approves creating table</ul>
     *Instantiating the Tables from the Class only once! Creating Tables
     * operations must not be invoked by the user
     * @param init instantiating if create a table or not
     * @throws DAOException DAOException
     * @see DataBaseDBDAO constructor
     */
    public CouponDBDAO(boolean init) throws DAOException {
        super(init, "Coupon", creatCouponTable, null, new CouponDBDAO());
    }
   /**
     * <ul>
     * <li>The method Creates a received Coupon in the DB
     * <li>using String prepared statement and executing  
     * with connection more on
     * </ul>
     * @param coupon Company to be created
     * @see DataBaseDBDAO
     * @see CouponDAO
     * @see Coupon
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void creatCoupon(Coupon coupon) throws DAOException {
        String addCouponSQLstatement = "INSERT INTO COUPONS"
                + " (TITLE,START_DATE,END_DATE,AMOUNT,TYPE,"
                + "MASSAGE,PRICE,IMGPATH) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        
//        String uniqueCase="INSERT INTO COUPONS"
//                + " (TITLE,START_DATE,END_DATE,AMOUNT,TYPE,"
//                + "MASSAGE,PRICE,IMGPATH) "
//                + "VALUES (? ("
//                + ", ?, ?, ?, ?, ?, ?, ?)";
        String MassageAction = "Coupon";
        try{
        super.actionOnDataBase(addCouponSQLstatement, MassageAction,coupon.getTitle(), coupon.getStartDate(),
                coupon.getEndDate(), coupon.getAmount(),
                coupon.getType().toString(),
                coupon.getMassage(), coupon.getPrice(),
                coupon.getImgPath());
        }catch(NullPointerException e){
        	throw new DAOException(ErrorType.COMPANY_VALUES_ARE_NOT_VALID,e);
        }
    }
    /**<ul>
     * <li>The method removes a Coupon from a received Coupon id in the DB
     * <li>using String prepared statement and executing  
     * with connection
     * </ul>
     *@param couponId  coupon id
     * @see DataBaseDBDAO
     * @see CouponDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void removeCoupon(long couponId) throws DAOException {
        String removeCouponSQLstatement = "DELETE FROM COUPONS WHERE ID=?";
        String MassageAction = ErrorType.UNABLE_TO_UPDATE_COUPON.toString();
        super.actionOnDataBase(removeCouponSQLstatement, MassageAction, couponId);
    }
    /**<ul>
     *<li> The method updates a received Coupon id in the DB using fields of date and price
     * <li>Using String prepared statement and executing  
     * with connection more on
     * </ul>
     *@param couponId  where to update
     * @param endDate  what to update
     * @param price  what to update
     * @see DataBaseDBDAO
     * @see CouponDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void updateCoupon(long couponId, Timestamp endDate, double price) throws DAOException {
        String updateCouponSQLstatement = "UPDATE COUPONS"
                + " SET " + "END_DATE=? , " + "PRICE=? WHERE " + "ID=?";
        String MassageAction = ErrorType.UNABLE_TO_UPDATE_COUPON.toString();
        super.actionOnDataBase(updateCouponSQLstatement, MassageAction,
                endDate, price, couponId);
    }
   /**
     * <ul>
     *<li> The method Gets a Coupon from a received Coupon id in the DB
     * <li>Using String prepared statement and executing  and getting result set of Coupon
     * with connection more on
     * </ul>
     *@param id the Coupon id to get Coupon Bean
     * @see DataBaseDBDAO
     * @see CouponDAO
     * @see Coupon
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Coupon getCoupon(long id) throws DAOException {
        String getCouponByIDSQLstatement = "SELECT * FROM COUPONS"
                + " WHERE COUPONS.ID=?";
        String MassageAction = ErrorType.UNABLE_TO_GET_COUPON.toString();
        List<Coupon> queryCoupons = (List<Coupon>) super.readInDataBase(getCouponByIDSQLstatement, MassageAction, "Coupon", id);
        return queryCoupons.get(0);
    }

        /**
     * <ul>
     *<li> The method Gets a collection of All the Coupons from the DB
     *<li> using String prepared statement and executing  and getting result set of Coupon
     * with connection more on
     * </ul>
     * @see DataBaseDBDAO
     * @see CouponDAO
     * @see Coupon
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Collection<Coupon> getAllCoupon() throws DAOException {
        String getAllCouponSQLstatement = "SELECT * FROM COUPONS ";
        String MassageAction = ErrorType.UNABLE_TO_GET_COUPONS.toString();
        List<Coupon> queryCoupons = (List<Coupon>) super.readInDataBase(getAllCouponSQLstatement, MassageAction, "Coupon");
        return (Collection<Coupon>) queryCoupons;
    }
    /**
     * <ul>
     * <li>The method Gets a Coupon where a specific title is located
     *<li> Using String prepared statement and executing and getting result set of Coupon
     * with connection more on
     * </ul>
     * @param title  get coupon by title
     * @see DataBaseDBDAO
     * @see CouponDAO
     * @see Coupon
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public Coupon getCouponbyTitle(String title) throws DAOException {
        String getLoginCustomerSQLstatement = "SELECT * FROM COUPONS"
                + " WHERE" + " TITLE =?";
        String MassageAction = ErrorType.UNABLE_TO_GET_COUPON.toString()+" By Title";
        List<Coupon> allCoupons = (List<Coupon>) super.readInDataBase(getLoginCustomerSQLstatement, MassageAction, "Coupon", title);
        return allCoupons.get(0);
    }
    /**<ul>
     *<li> The method updates a received Coupon id in the DB setting amount -1
     * <li>Using String prepared statement and executing  
     * with connection more
     * </ul>
     *@param couponId  where to update
     * @see DataBaseDBDAO
     * @see CouponDAO
     * @throws DAOException DAOException
     * @since version 1.00
     */
    @Override
    public void updateCouponAmount(long couponId) throws DAOException {
        String updateCouponSQLstatement
                = "UPDATE COUPONS SET AMOUNT=AMOUNT-1 WHERE ID=?";
        String MassageAction =ErrorType.UNABLE_TO_UPDATE_COUPON+" Coupon Amount";
        super.actionOnDataBase(updateCouponSQLstatement, MassageAction,
                couponId);

    }

}
