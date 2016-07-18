package servicebeans;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *  bean Company class
 * @author ilya
 */
@XmlRootElement
public class Company{
	
	private long id;//ID of company
	private String compName;// Name of company
	private String password;//Password of company
	private String email;//Email address of company
	private Collection<Coupon> coupons=new ArrayList<>();//Collection of coupons

    /**
     *no argument constructor
     */
    public Company(){}
    
	// Company to string 
	@Override
	public String toString() { 
		return "Company (ID : " + id + ",Company Name : " + compName + ",Password : "+ password 
				+ ",Email Address : "+ email+")";
	}
                    // constructor builder of bean

    /**
     *
     * @param id id 
     * @param compName company name
     * @param password password
     * @param email email
     */
	public Company(long id, String compName, String password, String email) {
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}

//Get's and Set's for Company Fields

    /**
     *
     * @return id
     */

	public long getId() {
		return id;
	}

    /**
     *
     * @param id id
     */
    public void setId(long id) {
		this.id = id;
	}

    /**
     *
     * @return String
     */
    public String getCompName() {
		return compName;
	}

    /**
     *
     * @param compName compName
     */
    public void setCompName(String compName) {
		this.compName = compName;
	}

    /**
     *
     * @return string password
     */
    public String getPassword() {
		return password;
	}

    /**
     *
     * @param password password
     */
    public void setPassword(String password) {
		this.password = password;
	}

    /**
     *
     * @return string email
     */
    public String getEmail() {
		return email;
	}

    /**
     *
     * @param email email
     */
    public void setEmail(String email) {
		this.email = email;
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
     * @param coupons coupons collection
     */
    public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
        
//hash code to get specific Object code 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compName == null) ? 0 : compName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
        
	//Equals of Company to check if Company name is equal
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (compName == null) {
			if (other.compName != null)
				return false;
		} else if (!compName.equals(other.compName))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
