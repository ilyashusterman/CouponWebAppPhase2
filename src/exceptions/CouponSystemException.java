package exceptions;

import java.sql.SQLException;
/**
 * <ul><li>This class is all Exceptions may throw in coupon system Exceptions</ul>
 * @author ilya shusterman
 * @since version 1.00
 */
public class CouponSystemException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
/**
 * <ul><li>constructor that get message and SQLException and throws</ul>
 * @param massage message
 * @param e  error
 */
    public CouponSystemException(String massage, SQLException e) {
        super(massage, e);
    }
/**
 *  <ul><li>constructor that get message and throws</ul>
 * @param massage message
 */
    public CouponSystemException(String massage) {
        super(massage);
    }
/**
 * <ul><li>constructor that get message and error and throws</ul>
 * @param massage message
 * @param e  error
 */
    public CouponSystemException(String massage, Throwable e) {
        super(massage, e);
    }
/**
 * <ul><li>constructor that get error and throws</ul>
 * @param e  error
 */
    public CouponSystemException(Throwable e) {
        super(e);
    }

	public CouponSystemException(ErrorType errorType, Throwable e) {
		super(errorType.toString(), e);
	}

}
