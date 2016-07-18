package facade;

import java.util.Collection;

import exceptions.FacadeException;
import servicebeans.Company;
import servicebeans.Customer;

/**
 * <ul><li>This class AdminFacade gives all operations that admin can do in the system</ul>
 * @since version 1.00
 * @author ilya shusterman
 */
public interface AdminFacade extends CouponClientFacade {

    /**
     *
     * @param customer customer
     * @throws FacadeException FacadeException
     */
    void creatCustomer(Customer customer) throws FacadeException;

    /**
     *
     * @param customerId customerId
     * @throws FacadeException FacadeException
     */
    void removeCustomer(long customerId) throws FacadeException;

    /**
     *
     * @param customerId customerId
     * @param password password
     * @throws FacadeException FacadeException
     */
    void updateCustomer(long customerId, String password) throws FacadeException;

    /**
     *
     * @param id id
     * @return Customer
     * @throws FacadeException FacadeException
     */
    Customer getCustomer(long id) throws FacadeException;

    /**
     *
     * @return Customer
     * @throws FacadeException FacadeException
     */
    Collection<Customer> getAllCustomer() throws FacadeException;

    /**
     *
     * @param company company
     * @throws FacadeException FacadeException
     */
    void creatCompany(Company company) throws FacadeException;

    /**
     *
     * @param companyId companyId
     * @throws FacadeException FacadeException
     */
    void removeCompany(long companyId) throws FacadeException;

    /**
     *
     * @param companyId companyId
     * @param password password
     * @param email email
     * @throws FacadeException FacadeException
     */
    void updateCompany(long companyId, String password, String email) throws FacadeException;

    /**
     * 
     * @param companyId companyId
     * @param fields fields
     * @throws FacadeException FacadeException
     */
    void updateCompanyWithCheck(long companyId, String... fields) throws FacadeException;

    /**
     *
     * @param id id
     * @return Company
     * @throws FacadeException FacadeException
     */
    Company getCompany(long id) throws FacadeException;

    /**
     *
     * @return Collection of companies
     * @throws FacadeException FacadeException
     */
    Collection<Company> getAllCompanies() throws FacadeException;

}
