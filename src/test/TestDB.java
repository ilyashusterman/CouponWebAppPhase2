package test;

import java.sql.Timestamp;

import exceptions.CouponSystemException;
import exceptions.DAOException;
import exceptions.ErrorType;
import facade.ClientType;
import facadedb.AdminFacadeDB;
import facadedb.CompanyFacadeDB;
import facadedb.CustomerFacadeDB;
import servicebeans.Company;
import servicebeans.Coupon;
import servicebeans.CouponType;
import servicebeans.Customer;
import system.CouponSystem;

/**
 *
 * @author ilya shusterman
 */
public class TestDB {

    /**
     *
     */
	public static int totalCoupons;
    public static CouponSystem couponSys;


//    public static void main(String[] args) {
//        try {
//            new TestDB(CouponSystem.getInstance());
//        } catch (CouponSystemException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();//FIXME handle exception
//        }
//    }

    /**
     *
     * @param couponSys Coupon System
     * @throws CouponSystemException CouponSystemException
     */
    @SuppressWarnings("static-access")
    public TestDB(CouponSystem couponSys) throws CouponSystemException {
        this.couponSys = couponSys;
        try {
        	System.out.println("***Testing CompanyDAO***");
            InitCompanyDAO();
            System.out.println("***Testing CouponDAO***");
            InitCouponDAO();
            System.out.println("***Testing CustomerDAO*** ");
            InitCustomerDAO();
            System.out.println("***Testing facade's***");
            initFacades(couponSys);
            /**
             * facade tests simutanislly 
             */
            System.out.println("total Coupons"+CouponSystem.getInstance().couponDAO.getAllCoupon());
            System.out.println("***Testing Facades in threads simultaniaclly***");
            new ThreadTests();
        } catch (CouponSystemException e) {
        	e.printStackTrace();
          throw new CouponSystemException(ErrorType.TEST_ERROR_HAS_BEEN_INVOKED+e.getMessage());
        }
    }

    /**
     *
     * @param couponSys Coupon System
     * @throws CouponSystemException CouponSystemException
     */
    public static void initFacades(CouponSystem couponSys) throws CouponSystemException {
    	  //*************************************
    	RandomGenerator random=new RandomGenerator();
        System.out.println("*********************************"
        		+ "******************************************"
        		+ "**************************");
    	System.out.println("***Testing Company facade******"+"\n");
    	System.out.println("***Executing Company login : ***"+"\n");
        System.out.println(couponSys.companyDAO.getCompanyByNamePassword("MaxInnovations", "tyrone")+ " Successfully logged");
        CompanyFacadeDB companyTest = (CompanyFacadeDB) couponSys.login("MaxInnovations", "tyrone", ClientType.COMPANY);
 
        System.out.println("*****Creating standard Coupons*****"+"\n");
        companyTest.creatCoupon(new Coupon(5, "bestCoupon", generateDate(2016, 2017), generateDate(2017, 2018), 20, CouponType.TRAVELING,
                " hawai", 100, "this is the img path/directory/idfmg.jdspg"));
        companyTest.creatCoupon(new Coupon(6, "omgCoupon", generateDate(2016, 2017), generateDate(2017, 2018), 20, CouponType.ELECTRICITY,
                " honalulu", 50, "this is the img path/directory/rty.jpg"));
        Coupon yoyo = new Coupon(7, "purim", generateDate(2016, 2017), generateDate(2017, 2018), 23, CouponType.FOOD, "hag purim",
                89.54, "path/directory/rty.jpg");
        Coupon hunny = new Coupon(8, "hanuuka", generateDate(2016, 2017), generateDate(2017, 2018), 20, CouponType.ELECTRICITY,
                "hag hunuuka", 60, "path/directory/rty.jpg");
        Coupon bear = new Coupon(9, "bearbuu", generateDate(2016, 2017), generateDate(2017, 2018), 20, CouponType.ELECTRICITY,
                "hag bear", 27, "path/directory/rty.jpg");
        Coupon expired = new Coupon(10, "expiredCoupon", generateDate(2014, 2015), generateDate(2014, 2015), 20, CouponType.ELECTRICITY,
                "hag bear", 27, "path/directory/rty.jpg");
        Coupon outOfAmountCoupon = new Coupon(11, "outofAmount", generateDate(2014, 2015), generateDate(2017, 2018), 0, CouponType.ELECTRICITY,
                "hag bear", 0, "path/directory/rty.jpg");
        
        companyTest.creatCoupon(yoyo);
        System.out.println("\n"+"***Getting all companies Coupons***"+"\n");
        System.out.println(companyTest.getCouponsOfCompany());
        System.out.println(companyTest.getCouponsOfCompany());
        System.out.println("\n");
        System.out.println("\n"+"***Creating Coupons***"+"\n");
        companyTest.creatCoupon(hunny);
        companyTest.creatCoupon(bear);
        companyTest.creatCoupon(expired);
        companyTest.creatCoupon(outOfAmountCoupon);
        System.out.println("\n"+"***Getting all companies Coupons***"+"\n");
        System.out.println(companyTest.getCouponsOfCompany());
        System.out.println("\n"+"***Updating Coupon***");
        companyTest.updateCoupon(7, generateDate(2018, 2019), 43.45);
        
        System.out.println("\n"+"***Getting Coupons of Company***"+"\n");
        System.out.println(companyTest.getCouponsOfCompany());
        System.out.println(couponSys.couponDAO.getAllCoupon());
        System.out.println("\n"+"***Getting Coupons of Company by Date***"+"\n");
        System.out.println(companyTest.getCouponByDate(generateDate(2016, 2018)));
        System.out.println("\n"+"***Getting Coupons of Company up to price 91***"+"\n");
        System.out.println(companyTest.getCouponUpToPrice(91));
        System.out.println("\n"+"***Getting Coupons of Company by type TRAVELING***"+"\n");
        System.out.println(companyTest.getCouponByType(CouponType.TRAVELING));
        System.out.println("*********************************"
        		+ "******************************************"
        		+ "**************************");
        //*************************************
        System.out.println("\n"+"***Testing Customer facade***"+"\n");
        System.out.println("\n"+"***Executing Company login : ***"+"\n");
        CustomerFacadeDB ilyacustomer = (CustomerFacadeDB) couponSys.login("ilya", "5556", ClientType.CUSTOMER);
        System.out.println("login Sucessfully");
        System.out.println("\n"+"***Purchasing coupons ***");
        try{
        System.out.println("\n"+"is coupons with id of 7 and 8 purchasing done successfully?"
        + ilyacustomer.purchaseCoupon(7)+ilyacustomer.purchaseCoupon(8)+"\n");
        }catch(CouponSystemException e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        	
        }
        System.out.println("\n"+" Coupons "+ilyacustomer.getAllPurchasedCoupons());
        System.out.println("\n"+"***Trying to purchase coupon that out of amount***"+"\n");
        try {
        	System.out.println(ilyacustomer.purchaseCoupon(11));
        	System.out.println(ilyacustomer.getAllPurchasedCoupons());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
        companyTest.removeCoupon(11);
        System.out.println("\n"+"***Trying to purchase coupon that has expired***"+"\n");
        try {
        	System.out.println(ilyacustomer.purchaseCoupon(10));
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
        companyTest.removeCoupon(10);
        System.out.println("\n"+"***Trying to purchase coupon that already exsist : ***"+"\n");
        try {
			System.out.println(ilyacustomer.purchaseCoupon(8));
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
        totalCoupons=couponSys.couponDAO.getAllCoupon().size()+1;
        System.out.println(ilyacustomer.purchaseCoupon(9));
        System.out.println("\n"+"***Getting all purchased coupons ***"+"\n");
        System.out.println(ilyacustomer.getAllPurchasedCoupons());
        System.out.println("\n"+"***Getting all purchased coupons by type ELECTRICITY***"+"\n");
        System.out.println(ilyacustomer.getAllPurchasedCouponsByType(CouponType.ELECTRICITY));
        System.out.println("\n"+"***Getting all coupons ***"+"\n");
        System.out.println(companyTest.getCouponsOfCompany());
        System.out.println("\n"+"***Getting all coupons of the company by price ***"+"\n");
        System.out.println(ilyacustomer.getAllPurchasedCouponsByPrice(41));    
        System.out.println("\n"+"***Getting all companies Coupons***"+"\n");
        System.out.println("*********************************"
        		+ "******************************************"
        		+ "**************************");
        //***************************************
        System.out.println("\n"+"***Testing Admin facade***"+"\n");
        AdminFacadeDB admin = (AdminFacadeDB) couponSys.login("admin", "1234", ClientType.ADMIN);
        admin.creatCompany(new Company(0, "companyTest2", "1234", "bgtgg@Walla.co.il"));
        CompanyFacadeDB companyTest2 = (CompanyFacadeDB) couponSys.login("companyTest2", "1234", ClientType.COMPANY);
        System.out.println(companyTest.getCouponsOfCompany());
        System.out.println(companyTest.getCouponsOfCompany());
        System.out.println(admin.getAllCompanies());
        companyTest2.creatCoupon(random.getRandomCoupon());
        companyTest2.creatCoupon(random.getRandomCoupon());
        companyTest2.creatCoupon(random.getRandomCoupon());
        System.out.println(couponSys.couponDAO.getAllCoupon());
        ilyacustomer.purchaseCoupon(12);
        ilyacustomer.purchaseCoupon(13);
        ilyacustomer.purchaseCoupon(14);
        System.out.println("\n"+"***Trying to create coupon with title that already exists***"+"\n");
        try {
        	companyTest.creatCoupon(yoyo);
        } catch (CouponSystemException e) {
        	System.out.println(e.getMessage());
        }
        System.out.println("\n"+"***Tyring to delete Company that has Coupons that customer purchased the Coupons***"+"\n");
        try{
        admin.removeCompany(10);       	
        }catch(CouponSystemException e){
        	System.out.println(e.getMessage());
        }
        System.out.println("\n"+"***Show all coupons***"+"\n");
        System.out.println(couponSys.couponDAO.getAllCoupon());
        System.out.println(ilyacustomer.getAllPurchasedCoupons());
        System.out.println(companyTest.getCouponsOfCompany());
        admin.creatCompany(new Company(9, "asusASSHOLES", "12345", "bgt@Walla.co.il"));
        System.out.println("\n"+"***Tyring to add Company that has the same name***");
        try{
        admin.creatCompany(new Company(10, "asusASSHOLES", "25235", "bgrtert@Walla.co.il"));
        }catch(CouponSystemException e){
        	System.out.println(e.getMessage());
        }
        admin.creatCustomer(new Customer(8, "custName", "1235"));
        System.out.println("\n"+"***Tyring to add Customer that has the same name ***"+"\n");
        try{
        admin.creatCustomer(new Customer(11, "custName", "12352"));
        }catch(CouponSystemException e){
        	System.out.println(e.getMessage());
        }
        System.out.println(admin.getAllCompanies());
        System.out.println(admin.getAllCustomer());
        admin.removeCompany(9);
        CustomerFacadeDB customer2 = (CustomerFacadeDB) couponSys.login("Gilbertu", "12345", ClientType.CUSTOMER);
        customer2.purchaseCoupon(7);
        customer2.purchaseCoupon(8);
        customer2.purchaseCoupon(9);
        System.out.println("\n"+"***Trying to remove customer that has coupon purchased***"+"\n");
        try {		
        	admin.removeCustomer(8);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
        System.out.println("\n"+"***Showing all Companies and Customers***"+"\n");
        System.out.println(admin.getAllCompanies());
        System.out.println(admin.getAllCustomer());
    }

    /**
     *
     * @throws DAOException DAOException
     * @throws CouponSystemException CouponSystemException
     */
    public static void InitCouponDAO() throws DAOException, CouponSystemException {
        Coupon Holiday = new Coupon(1, "Holiday", generateDate(2016, 2017), generateDate(2017, 2018), 3, CouponType.TRAVELING,
                "liza vacation", 54, "this is the img path/directory/img.jpg");
        couponSys.couponDAO.creatCoupon(Holiday);
        couponSys.couponDAO.creatCoupon(new Coupon(2, "sweet!", generateDate(2016, 2017), generateDate(2017, 2018), 3,
                CouponType.HEALTH, "sissy vacation", 36, "this is the img path/directory/idfmg.jdspg"));
        couponSys.couponDAO.creatCoupon(new Coupon(3, "88dd", generateDate(2016, 2017), generateDate(2017, 2018), 3,
                CouponType.TRAVELING, "john vacation", 108, "this is the img path/directory/idfmg.jdspg"));
        couponSys.couponDAO.creatCoupon(new Coupon(4, "hahaha", generateDate(2016, 2017), generateDate(2017, 2018), 3,
                CouponType.ELECTRICITY, "oliver vacation", 7, "this is the img path/directory/idfmg.jdspg"));
        couponSys.couponDAO.removeCoupon(1);
    }

    /**
     *
     * @throws DAOException DAOException
     */
    public static void InitCustomerDAO() throws DAOException {
        Customer ilya = new Customer(1, "ilya", "1234");
        couponSys.customerDAO.creatCustomer(ilya);
        couponSys.customerDAO.creatCustomer(new Customer(2, "Gilbert", "12345"));
        couponSys.customerDAO.creatCustomer(new Customer(3, "Rose", "12345"));
        couponSys.customerDAO.creatCustomer(new Customer(4, "Shelley", "12345"));
        couponSys.customerDAO.creatCustomer(new Customer(5, "Oxford", "12345"));
        couponSys.customerDAO.creatCustomer(new Customer(6, "Regina", "12345"));
        couponSys.customerDAO.creatCustomer(new Customer(7, "Saavedra", "12345"));
        couponSys.customerDAO.creatCustomer(new Customer(8, "Gilbertu", "12345"));
        couponSys.customerDAO.updateCustomer(1, "5556");
        couponSys.customerDAO.removeCustomer(3);		
    }

    /**
     *
     * @throws DAOException DAOException
     * @throws CouponSystemException CouponSystemException
     */
    public static void InitCompanyDAO() throws DAOException, CouponSystemException {
        couponSys.companyDAO.creatCompany(new Company(1, "Intel", "intelforyou", "intel@intel.com"));
        couponSys.companyDAO.creatCompany(new Company(2, "Exotic Liquid", "Charlotte", "Melbourne@liquid.com"));
        couponSys.companyDAO.creatCompany(new Company(3, "ExoeticG", "Charl8otte3", "Mel34@liquid.com"));
        Company google = new Company(4, "Google", "1235", "googleCorps@gsdgs.com");
        couponSys.companyDAO.creatCompany(google);
        google.setEmail("blabla@Goo.com");
        couponSys.companyDAO.updateCompany(google.getId(), "1235", "sfsdf3");
	System.out.println(couponSys.companyDAO.getCompanyByNamePassword(google.getCompName(), google.getPassword()));
        couponSys.companyDAO.removeCompany(google.getId());
        couponSys.companyDAO.creatCompany(new Company(4, "MaxInnovations", "tyrone", "man@oop.com"));
        couponSys.companyDAO.creatCompany(new Company(5, "lizacorps", "123", "jjj@kkk.com"));
        couponSys.companyDAO.creatCompany(new Company(6, "ilyacorps", "123", "jjj@66hy.com"));
        couponSys.companyDAO.creatCompany(new Company(7, "yossicorps", "123", "jjj@cvbft.com"));
        couponSys.companyDAO.creatCompany(new Company(8, "davidcorps", "123", "jjj@eryrt.com"));
    }

    private static Timestamp generateDate(long startYear, long endYear) {
        long offset = Timestamp.valueOf(startYear + "-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf(endYear + "-01-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
        return rand;
    }

}
