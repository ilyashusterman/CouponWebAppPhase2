/**
 * 
 */
package test.threads;

import exceptions.CouponSystemException;
import facade.AdminFacade;
import facade.ClientType;
import servicebeans.Company;
import servicebeans.Customer;
import system.CouponSystem;
import test.ThreadTests;

/**
 * @author ilya shusterman
 *
 */
public class AdminThread extends Thread {

	@Override
	public void run(){
	AdminFacade admin = null;
			try {
				admin = (AdminFacade) CouponSystem.getInstance().login(
						"admin", "1234", ClientType.ADMIN);
				if(admin!=null){
					System.out.println("login successfully!");
				}
				for(Company company : ThreadTests.companies){
					admin.creatCompany(company);
				}
				for(Customer customer : ThreadTests.customers){
					admin.creatCustomer(customer);
				}
				System.out.println("all companies!!! :*******************"+"\n"+admin.getAllCompanies());
				System.out.println("all customers!!! :*******************"+"\n"+admin.getAllCustomer());
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
	}
}
