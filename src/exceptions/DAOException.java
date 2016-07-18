package exceptions;

import java.sql.SQLException;
/**
 * <ul><li>This class is all Exceptions may throw in  DAOExceptions</ul>
 * @author ilya shusterman
 * @since version 1.00
 */
public class DAOException extends CouponSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
/**
 *  <ul><li>constructor that get message and throws</ul>
 * @param massage  massage
 */
    public DAOException(String massage) {
        super(massage);
    }
/**
 * <ul><li>constructor that get message and error and throws</ul>
 * @param massage massage
 * @param e error
 */
    public DAOException(String massage, Throwable e) {
        super(massage, e);
    }
    public DAOException(ErrorType type, Throwable e) {
        super(type.toString(), e);
    }
/**
 * <ul><li>constructor that get error and throws</ul>
 * @param e error
 */
    public DAOException(Throwable e) {
        super(e);
    }
/**
 * <ul><li>constructor that get message and SQLException and throws</ul>
 * @param massage message
 * @param e  error
 */
    public DAOException(String massage, SQLException e) {
        super(massage, e);
    }

}
