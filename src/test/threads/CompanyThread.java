/**
 * 
 */
package test.threads;

import exceptions.CouponSystemException;
import facade.ClientType;
import facade.CompanyFacade;
import servicebeans.Company;
import system.CouponSystem;
import test.ThreadTests;

/**
 * @author ilya shusterman
 *
 */
public class CompanyThread extends Thread {
	
	public static int compCounter=0;
	private int couponsNumber;
	private Company theCompany;
	private int id;
	private final long sleepTime;
	
	public CompanyThread(Company company,int coupons,long sleepTime){
		this.couponsNumber=coupons;
		this.theCompany=company;
		setName("Company thread :"+compCounter);
		this.id=compCounter;
		compCounter++;
		this.sleepTime=sleepTime;
	}
	@Override
	public void run(){
		CompanyFacade facade = null;
		try {
			facade = (CompanyFacade) CouponSystem.getInstance().login(
					theCompany.getCompName(), theCompany.getPassword(),
					ClientType.COMPANY);
			if (facade!=null) {
				System.out.println("logged in successfully!! Company "+theCompany.getCompName());
			}
			int min= id*couponsNumber;
			int max=min+couponsNumber;
			for (int i =min; i <max&&
					i<ThreadTests.coupons.length; i++) {
					facade.creatCoupon(ThreadTests.coupons[i]);
					sleep(sleepTime);
			}
		} catch (CouponSystemException | InterruptedException e) {
			System.out.println(Thread.currentThread().getName());
			e.printStackTrace();
		}


	}
	
}
