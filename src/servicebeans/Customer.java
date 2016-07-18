package servicebeans;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ilya shusterman
 */
@XmlRootElement
public class Customer{
	private String custName;//Customer Name 
	private String password;//Password of Customer
	private long id;//ID of Customer
	private Collection<Coupon> coupons=new ArrayList<>();//Collection of coupons of Customer
	
    /**
     *No argument constructor
     */
    public Customer(){}
	
    /**
     *
     * @param id id 
     * @param custName custName
     * @param password password
     */
    public Customer(long id, String custName, String password) {
		this.id = id;
		this.custName = custName;
		this.password = password;
	}
	

	@Override
	public String toString() {
		return "Customer (ID : " + id + ", Name : " + custName + ", Password : " + password  
				+ " )";
	}

    /**
     *
     * @return long id
     */
    public long getId() {
		return id;
	}

    /**
     *
     * @param id setId
     */
    public void setId(long id) {
		this.id = id;
	}

    /**
     *
     * @return custName
     */
    public String getCustName() {
		return custName;
	}

    /**
     *
     * @param custName setCustName
     */
    public void setCustName(String custName) {
		this.custName = custName;
	}

    /**
     *
     * @return password
     */
    public String getPassword() {
		return password;
	}

    /**
     *
     * @param password setPassword
     */
    public void setPassword(String password) {
		this.password = password;
	}

    /**
     *
     * @return coupons
     */
    public Collection<Coupon> getCoupons() {
		return coupons;
	}

    /**
     *
     * @param coupons setCoupons
     */
    public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custName == null) ? 0 : custName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
        	//Equals of Company to check if Customer name is equal
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (custName == null) {
			if (other.custName != null)
				return false;
		} else if (!custName.equals(other.custName))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
