package facadedb;

import java.util.Collection;
import dao.CompanyDAO;
import dao.CustomerDAO;
import dbdao.CompanyDBDAO;
import dbdao.CustomerDBDAO;
import exceptions.DAOException;
import exceptions.ErrorType;
import exceptions.FacadeException;
import facade.AdminFacade;
import facade.CouponClientFacade;
import servicebeans.Company;
import servicebeans.Customer;

/**
 * <ul>
 * <li>This is AdminFacadeDB class implementation of AdminFacade is the logic
 * behind admin operations uses the DAO layers to execute the operations
 * <li>AdminFacade is a singleton if not done login '
 * <li>Implements the CouponClientFacade through AdminFacade gives Login method to get AdminFacade full operations</ul>
 *
 * @see CouponClientFacade
 * @see AdminFacade
 * @see CompanyDAO
 * @see CustomerDAO
 * @author ilya shusterman
 * @since version 1.00
 */
public class AdminFacadeDB implements AdminFacade {

    private volatile static AdminFacadeDB facade;
    private CompanyDAO companyDAO;
    private CustomerDAO customerDAO;

    /**
     * No argument private constructor to get full operations if login has been
     * done successfully
     */
    private AdminFacadeDB() {
        this.companyDAO = new CompanyDBDAO();
        this.customerDAO = new CustomerDBDAO();
    }

    /**
     * constructor to get only Login method operational
     *
     * @param system
     */
    private AdminFacadeDB(boolean system) {
    }

    /**
     * <ul><li>This method gets the same instance for login use purposes
     * only</ul>
     *
     * @return AdminFacadeDB facade to use only login
     */
    public static AdminFacadeDB getInstance() {
        if (facade == null) {
            synchronized (AdminFacadeDB.class) {
                if (facade == null) {
                    facade = new AdminFacadeDB(true);
                }
            }
        }
        return facade;
    }

    /**
     * <ul><li>This method checks if username and password are valid
     * <ul><li>If valid will get a new AdminFacade Instance with fully
     * operational methods
     * <li> If not valid will throw an exception </ul>
     * </ul>
     *
     * @param name name of the client
     * @param password password of the client
     * @return returns a CouponClientFacade
     * @throws FacadeException if username and password not matched 
     */
    @Override
    public CouponClientFacade login(String name, String password) throws FacadeException {
        AdminFacadeDB admin = null;
        if ((name.equals("admin") && password.equals("1234"))) {
            admin = new AdminFacadeDB();
        } else {
            throw new FacadeException(ErrorType.USERNAME_OR_PASSWORD_DOES_NOT_MATCH);
        }
        return admin;
    }

    /**
     * <ul><li>This method creates a Customer
     * <ul><li>By using Unique constrain in the CUSTOMERS table will throw
     * exception if name is already located</ul>
     * </ul>
     *
     * @param customer customer with fields to create
     * @throws FacadeException if customer name is already found
     */
    @Override
    public void creatCustomer(Customer customer) throws FacadeException {
        try {
            customerDAO.creatCustomer(customer);
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }

    /**
     * <ul><li>This method removes a customer with the id of (if the id is not
     * found no changes will be made)
     * <li>Customer purchase history of coupons also will be deleted</ul>
     *
     * @param customerId  customerId
     * @throws FacadeException FacadeException with connection or SQL problem
     */
    @Override
    public void removeCustomer(long customerId) throws FacadeException {
        try {
            customerDAO.removeCustomer(customerId);
            customerDAO.removeCouponsfromCustomersCoupon(customerId);//FIXME
        } catch (DAOException e) {
            throw new FacadeException(ErrorType.UNABLE_TO_UPDATE_COMPANY,e);
        }
    }

    /**
     * <ul><li>This method update a customer with id to change its password
     * <ul><li>if the id is not found no changes will be made</ul></ul>
     *
     * @param customerId customerId
     * @param password password 
     * @throws FacadeException if unable to update customer
     */
    @Override
    public void updateCustomer(long customerId, String password) throws FacadeException {
        try {
            customerDAO.updateCustomer(customerId, password);
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }

    /**
     * <ul><li>This method will return a Customer by its Id <ul>
     * <li>In case of not found customer will throw exception</ul></ul>
     *
     * @param id id
     * @return Customer 
     * @throws FacadeException FacadeException
     */
    @Override
    public Customer getCustomer(long id) throws FacadeException {
        try {
            return customerDAO.getCustomer(id);
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }

    /**
     * <ul><li>This method returns all the customers in DB</ul>
     *
     * @return Collection of Customers
     * @throws FacadeException if Unable to get the customers
     */
    @Override
    public Collection<Customer> getAllCustomer() throws FacadeException {
        try {
            return customerDAO.getAllCustomers();
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }

    /**
     * <ul><li>This method creates a Company
     * <ul><li>Creates only if name is not used ,By using Unique constrain in the companies table will throw
     * exception if name is already located</ul>
     * </ul>
     *
     * @param company company to create with its fields
     * @throws FacadeException FacadeException if company already found with the name
     */
    @Override
    public void creatCompany(Company company) throws FacadeException {
        try {
            companyDAO.creatCompany(company);
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
/**
 * <ul><li>This method removes a Company with a specific id from the db
 * <ul>
 * <li>Coupons of the company will be deleted 
 * <li>Coupons of the company that purchased by customers will be deleted also
 * <li>In case of Not found company to remove the DataBase remains with out a change</ul></ul>
 * @param companyId companyId
 * @throws FacadeException FacadeException if unable to remove company
 */
    @Override
    public void removeCompany(long companyId) throws FacadeException {
        try {
            customerDAO.removeCouponsOfCompanyFromCouponsOfCustomer(companyId);//CUSTOMER_COUPONS
            companyDAO.removeCouponsFromCompanyCoupons(companyId);//FIXME//COMPANY_COUPONS
            companyDAO.removeCouponsOfCompanyFromCoupons(companyId);//COUPONS
            companyDAO.removeCompany(companyId);//COMPANY
        } catch (DAOException e) {
            throw new FacadeException(ErrorType.UNABLE_TO_REMOVE_COMPANY,e);
        }
    }
/**
 * <ul><li>This method updates the Company with the id and updates password and email field
 * <ul><li>Incase of not found id nothing will be change</ul></ul>
 * @param companyId companyId
 * @param password password
 * @param email email
 * @throws FacadeException FacadeException if unable to update company
 */
    @Override
    public void updateCompany(long companyId, String password, String email) throws FacadeException {
        try {
            companyDAO.updateCompany(companyId, password, email);
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
/**
 * <ul><li>This method updates the Company with the id and updates password or email field
 * by the count of Strings parameters
 * <ul><li>Incase of not found id nothing will be change</ul></ul>
 * @param companyId companyId
 * @throws FacadeException FacadeException if unable to update company
 */
    @Override
    public void updateCompanyWithCheck(long companyId, String... fields) throws FacadeException {
        try {
            if (fields == null || fields[0] == null) {
                throw new FacadeException(ErrorType.COMPANY_VALUES_ARE_NOT_VALID);
            }
            Company company = companyDAO.getCompany(companyId);
            String password = "";
            String email = "";
            if (fields.length > 1) {
                email = fields[1];
                password = company.getPassword();
            } else if (fields.length == 1) {
                password = fields[0];
                email = company.getEmail();
            }
            companyDAO.updateCompany(companyId, password, email);
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
/**
 * <ul><li>This method will return a Company by its Id <ul>
 * <li>In case of not found Company will throw exception</ul></ul>
 * @param id id of company
 * @return Company
 * @throws FacadeException  FacadeException if unable to get company
 */
    @Override
    public Company getCompany(long id) throws FacadeException {
        try {
            return companyDAO.getCompany(id);
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }
/**
 * <ul><li>This method returns all the companies in DB</ul>
 * @return Collection of Companies
 * @throws FacadeException  FacadeException if unable to get companies
 */
    @Override
    public Collection<Company> getAllCompanies() throws FacadeException {
        try {
            return companyDAO.getAllCompanies();
        } catch (DAOException e) {
            throw new FacadeException(e);
        }
    }

}
