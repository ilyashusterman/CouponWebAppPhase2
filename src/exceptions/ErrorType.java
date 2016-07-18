/**
 * 
 */
package exceptions;

/**
 * @author ilya shusterman
 *
 */
public enum ErrorType {
	  DATABASE(0, "A database error has occured."),
	  DATABASE_TABLES(1,"Unable to init tables"),
	  DUPLICATE_COMPANY(2, "This Company name already exists."),
	  DUPLICATE_COUPON(3, "This Coupon Title already exists."),
	  DUPLICATE_CUSTOMER(4,"This Customer name already exists"),
	  COUPON_ALREADY_PURCHASED(5,"This Coupon already purchased by the Customer"),
	  COUPON_OUT_OF_STOCK(6,"This Coupon is out of stock"),
	  COUPON_EXPIRED(7,"This Coupon expired"),
	  UNKNOWN_ERROR(8,"This Error is not found !"),
	  RETURN_CONNECTION_ERROR(9,"Unable to return connection "),
	  GET_CONNECTION_ERROR(10,"Unable to get connection"),
	  UNABLE_TO_CREATE_CONNECTIONS(11,"Unable to create connections to the system"),
	  CONNECTION_NOT_VALID(12,"Connection not valid cannot return connection"),
	  FAILED_TO_CLOSE_CONNECTION(13,"Failed to close connection"),
	  VALUES_ARE_NOT_VALID(14,"Values are not valid cannot proccess statement"),
	  COMPANY_VALUES_ARE_NOT_VALID(15,"Values of Company not valid cannot proccess values "),
	  CUSTOMER_VALUES_ARE_NOT_VALID(16,"Values of Customer not valid cannot proccess values "),
	  COUPON_VALUES_ARE_NOT_VALID(17,"Values of Coupon not valid cannot proccess values "),
	  UNABLE_TO_REMOVE_COMPANY(18,"Unable to remove Company from DataBase "),
	  UNABLE_TO_REMOVE_CUSTOMER(19,"Unable to remove Customer from DataBase"),
	  UNABLE_TO_REMOVE_COUPON(20,"Unable to remove Coupon from DataBase "),
	  UNABLE_TO_UPDATE_COMPANY(21,"Unable to update Company in DataBase "),
	  UNABLE_TO_UPDATE_CUSTOMER(22,"Unable to update Customer in DataBase "),
	  UNABLE_TO_UPDATE_COUPON(23,"Unable to update Coupon in DataBase "),
	  UNABLE_TO_GET_ID(24,"Unable to get ID of Coupon"),
	  UNABLE_TO_GET_COMPANY(25,"Unable to get Company"),
	  UNABLE_TO_GET_COUPON(26,"Unable to get Coupon "),
	  UNABLE_TO_GET_CUSTOMER(27,"Unable to get Customer "),
	  UNABLE_TO_GET_COMPANIES(28,"Unable to get Companies"),
	  UNABLE_TO_GET_COUPONS(29,"Unable to get Coupons "),
	  UNABLE_TO_GET_CUSTOMERS(30,"Unable to get Customers "),
	  UNABLE_TO_GET_COUPONS_BY_TYPE(31,"Unable to get Coupon by type  : "),
	  UNABLE_TO_GET_COUPONS_BY_PRICE(32,"Unable to get Coupon by price  : "),
	  UNABLE_TO_GET_COUPONS_BY_DATE(33,"Unable to get Coupon by date  : "),
	  UNABLE_TO_CREATE_COUPON_BY_COMPANY(34,"Unable to create coupon by company"),
	  UNABLE_TO_PURCHASE_COUPON_BY_CUSTOMER(35,"Unable to purchase coupon by customer"),
	  DAILY_COUPON_EXPIRE_DATE_TASK_ERROR(36,"Error has occured while doing the Coupon-expired-task deletion"),
	  UNABLE_TO_PROCESS_RESULT_SET_OF_COUPON(38,"Unable to process the request of coupon from the DataBase"),
	  UNABLE_TO_PROCESS_RESULT_SET_OF_COMPANY(39,"Unable to process the request of company from the DataBase"),
	  UNABLE_TO_PROCESS_RESULT_SET_OF_CUSTOMER(40,"Unable to process the request of customer from the DataBase"),
	  UNABLE_TO_PROCESS_RESULT_SET_OF_ID(41,"Unable to process the request of coupon id from the DataBase"),
	  USERNAME_OR_PASSWORD_DOES_NOT_MATCH(42,"Username , Password and type doesnt match , login failed... "),
	  UNAUTHORIZED_REQUEST(43,"The request you are making is not authorized"),
	  UNABLE_TO_CONFIRM_LOGIN(44,"Unable to get login authorization from DataBase"),
	  COUPON_DOES_NOT_EXISTS(45,"Coupon does not exists"),
	  COUPON_DATE_HAS_EXPIRED(46,"Coupon date has expired"),
	  TEST_ERROR_HAS_BEEN_INVOKED(47,"Test error has been invoked "),
	  UNABLE_TO_CREATE_COUPON(48,"Unable to create Coupon to the DataBase"),
	  DATE_VALUES_INSERTED_ARE_NOT_VALID(49,"Unable to proccess date values are not valid");
	  
	  
	  
	  
	
	  private final int code;
	  private final String description;

	  private ErrorType(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	  public String getDescription() {
	     return description;
	  }

	  public int getCode() {
	     return code;
	  }

	  @Override
	  public String toString() {
	    return " Error Code "+code + ": " + description;
	  }
	}
