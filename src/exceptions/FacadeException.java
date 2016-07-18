package exceptions;

/**
 * <ul><li>This class is all Exceptions may throw in FacadeException</ul>
 *
 * @author ilya shusterman
 * @since version 1.00
 */
public class FacadeException extends CouponSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * <ul><li>constructor that get message and throws</ul>
     *
     * @param massage message 
     */
    public FacadeException(String massage) {
        super(massage);
        // TODO Auto-generated constructor stub
    }
/**
 * <ul><li>constructor that get message and error and throws</ul>
 * @param massage message
 * @param e  error
 */
    public FacadeException(String massage, Throwable e) {
        super(massage, e);
    }
/**
 * <ul><li>constructor that get error and throws</ul>
 * @param e error
 */
    public FacadeException(Throwable e) {
        super(e);
    }
    /**
     * this constructor returns prints the code to the user
     * @param type type of error
     * @param e throwable e 
     */
    public FacadeException(ErrorType type, Throwable e) {
        super(type.toString(), e);
    }
    public FacadeException(ErrorType type) {
        super(type.toString());
    }

}
