package facade;

import exceptions.FacadeException;

/**
 *
 * @author ilya
 */
public interface CouponClientFacade {

    /**
     *
     * @param username username 
     * @param password password
     * @return CouponClientFacade
     * @throws FacadeException FacadeException
     * @see CouponClientFacade for more info
     */
    CouponClientFacade login(String username, String password) throws FacadeException;

}
