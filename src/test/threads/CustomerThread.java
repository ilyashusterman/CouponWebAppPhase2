/**
 * 
 */
package test.threads;


import exceptions.CouponSystemException;
import facade.ClientType;
import facade.CustomerFacade;
import servicebeans.Customer;
import system.CouponSystem;
import test.TestDB;

/**
 * @author ilya shusterman
 *
 */
public class CustomerThread extends Thread {
	
	public static int custCounter=0;
	private int couponsNumber;
	private Customer theCustomer;
	private int id;
	private final long sleepTime;
	
	public CustomerThread(Customer customer,int coupons,long sleepTime){
		this.couponsNumber=coupons;
		this.theCustomer=customer;
		setName("Customer thread :"+custCounter);
		this.id=custCounter;
		custCounter++;
		this.sleepTime=sleepTime;
	}
	
	@Override
	public void run(){
		CustomerFacade facade = null;
		try {
			facade = (CustomerFacade) CouponSystem.getInstance().login(
					theCustomer.getCustName(), theCustomer.getPassword(),
					ClientType.CUSTOMER);
			if(facade!=null){System.out.println("Logged in sucessfully customer :"+theCustomer.getCustName());}
                        int min=id*couponsNumber+TestDB.totalCoupons+7;
                        int max=min+couponsNumber;
//                        System.out.println("max= "+max+"min="+min);
			for (int i =min;i!=0&&i<max; i++) {
					facade.purchaseCoupon(i);
					sleep(sleepTime);
			}
			
		} catch (CouponSystemException | InterruptedException e) {
			System.out.println(Thread.currentThread().getName());
			e.printStackTrace();
		}


	}
}
