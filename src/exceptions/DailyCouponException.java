package exceptions;
/**
 * <ul><li>This class is all Exceptions may throw in  Daily coupon system Exceptions</ul>
 * @author ilya shusterman
 * @since version 1.00
 */
public class DailyCouponException extends CouponSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
/**
 *  <ul><li>constructor that get message and throws</ul>
 * @param massage  massage
 */
    public DailyCouponException(String massage) {
        super(massage);

    }
/**
 * <ul><li>constructor that get message and error and throws</ul>
 * @param massage massage
 * @param e  error
 */
    public DailyCouponException(String massage, InterruptedException e) {
        super(massage, e);
    }

}
