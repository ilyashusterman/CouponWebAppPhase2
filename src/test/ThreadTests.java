package test;

import exceptions.CouponSystemException;
import servicebeans.Company;
import servicebeans.Coupon;
import servicebeans.Customer;
import system.CouponSystem;
import test.threads.AdminThread;
import test.threads.CompanyThread;
import test.threads.CustomerThread;

/**
 *
 * @author ilya
 */
public class ThreadTests {

    /**
     *
     */
    public static RandomGenerator rand;

    /**
     *
     */
    public static CouponSystem couponSys;

    /**
     *
     */
    public static Coupon[] coupons;

    /**
     *
     */
    public static Company[] companies;

    /**
     *
     */
    public static Customer[] customers;

    /**
     *
     * @throws CouponSystemException CouponSystemException
     */
    public ThreadTests() throws CouponSystemException {
        couponSys = CouponSystem.getInstance();
        rand = new RandomGenerator();
        coupons = rand.getRandomCouponArray(40);
        companies = rand.getRandomCompanyArray(10);
        customers = rand.getRandomCustomerArray(10);
        adminThread();
        try {
            Thread.sleep(5000);
            System.out.println("***Waiting complete***");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("***Starting company***");
        companyThread();
        try {
            Thread.sleep(5000);
            System.out.println("***Waiting complete***");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("***Starting customers***");
        customerThread();
        System.out.println("***Customers complete***");
    }

    /**
     *
     * @return long sleep time
     */
    public static long getRandomSleep() {
        return (long) (Math.random() * 600) + 50;
    }

    private void adminThread() {
        AdminThread admin = new AdminThread();
        admin.start();
    }

    private void companyThread() {
        CompanyThread[] compThreads = new CompanyThread[companies.length];
        for (int i = 0; i < companies.length; i++) {
            compThreads[i] = new CompanyThread(companies[i], 4, getRandomSleep());
            compThreads[i].start();
        }
    }

    private void customerThread() {
        CustomerThread[] custThreads = new CustomerThread[customers.length];
        for (int i = 0; i < customers.length; i++) {
            custThreads[i] = new CustomerThread(customers[i], 4, getRandomSleep());
            custThreads[i].start();
        }
    }

}
