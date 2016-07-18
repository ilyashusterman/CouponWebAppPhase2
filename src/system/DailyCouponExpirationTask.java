package system;

import java.util.List;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import dao.CompanyDAO;
import dao.CouponDAO;
import dao.CustomerDAO;
import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import dbdao.CustomerDBDAO;
import exceptions.DAOException;
import exceptions.DailyCouponException;
import exceptions.ErrorType;
import servicebeans.Coupon;

/**
 * <ul><li>This Class DailyCouponExpirationTask extends Thread checks every day
 * if Coupon need to be deleted based on expiration date
 * <ul><li>Uses DAO layer to remove the Coupon from all tables</ul></ul>
 *
 * @author ilya shusterman
 * @see CouponDAO
 * @see CustomerDAO
 * @see CompanyDAO
 */
public class DailyCouponExpirationTask extends Thread {

    /**
     * * <li>DAO layer - CouponDAO
     *
     * @see CouponDAO
     */
    private CouponDAO couponDAO;
    /**
     * * <li>DAO layer - CompanyDAO
     * @see CompanyDAO
     */
    private CompanyDAO companyDAO;
    /**
    * <li>DAO layer - CustomerDAO
     * @see CustomerDAO
     */
    private CustomerDAO customerDAO;
    /**
     * <li>static boolean to check if DailyCoupon task should keep run
     */
    private volatile boolean keepRunning;
/**
 * <ul><li>No argument Constructor to get full operational use of CouponDailyTask</ul>
 */
    public DailyCouponExpirationTask() {
        couponDAO = new CouponDBDAO();
        customerDAO = new CustomerDBDAO();
        companyDAO = new CompanyDBDAO();
        keepRunning = true;
    }
/**
 * <ul><li>This method will interrupt the system invoked task and will stop the task from running</ul>
 * @throws DailyCouponException 
 * 
 */
    void stopTask() throws DailyCouponException {
        keepRunning = false;

        interrupt();
    }
/**
 * <ul><li>Run method to delete the coupon while stop task did not invoked </ul>
 * 
 */
    @Override
    public void run() {
        while (keepRunning) {
            couponDeleteExpire();

            try {
                Thread.sleep(calculateSleepTime());
            } catch (InterruptedException e) {
                if (keepRunning) {
                    couponDeleteExpire();
                    Thread.currentThread().interrupt();
                } else {
                    System.out.println("Daily Coupon task has been shut down ,Not running anymore");
                    e.printStackTrace();//FIXME

                }
            }
        }
    }
/**
 * <ul><li>this method removes coupon from all Tables in the DataBase if the coupon end date is expired</ul>
 * @see Timestamp
 * @see Date
 * @see dbdao
 */
    private void couponDeleteExpire() {
        try {
            java.util.Date currentDate = new java.util.Date();
            Timestamp currentDateFM = new Timestamp(currentDate.getTime());
            List<Coupon> queryCoupons = new ArrayList<>(couponDAO.getAllCoupon());
            if (queryCoupons.get(0) != null) {
                for (Coupon coupon : queryCoupons) {
                    if (currentDateFM.after(coupon.getEndDate())) {
                        couponDAO.removeCoupon(coupon.getId());
                        customerDAO.removeCouponfromCustomersCoupon(coupon.getId());
                        companyDAO.removeCouponFromCompanyCoupons(coupon.getId());
                    }
                }
            }
        } catch (DAOException e) {
          System.out.println(ErrorType.DAILY_COUPON_EXPIRE_DATE_TASK_ERROR+e.getMessage());
        }
    }
/**
 * <ul><li>This method calculate the time to sleep for the task , should be every day to delete expired coupons</ul>
 * @return 
 * @see Calendar
 */
    private long calculateSleepTime() {
        Calendar currentTime = new GregorianCalendar();
        Calendar nextUpdateTime = new GregorianCalendar();

        nextUpdateTime.set(Calendar.HOUR_OF_DAY, 1);
        nextUpdateTime.set(Calendar.MINUTE, 0);
        nextUpdateTime.set(Calendar.SECOND, 0);
        nextUpdateTime.set(Calendar.MILLISECOND, 0);

        nextUpdateTime.add(Calendar.DAY_OF_MONTH, 1);

        return nextUpdateTime.getTimeInMillis() - currentTime.getTimeInMillis();
    }

}
