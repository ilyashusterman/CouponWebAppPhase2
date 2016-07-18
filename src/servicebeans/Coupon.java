package servicebeans;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * bean Coupon class
 * @author ilya shusterman
 */
@XmlRootElement
public class Coupon{
    
	private String title; // Title of Coupon
	private Timestamp startDate;//Start Date of Coupon used by Timestamp
	private Timestamp endDate;//End Date of Coupon used by Timestamp
	private int amount;//Amount of Coupon   
	private CouponType type;//Enum Type of Coupon
	private String massage;//Message Description of Coupon
	private double price;//Price of Coupon
	private String imgPath;//Image file path of Coupon
	private long id;//ID of Coupon
	
    /**
     *
     */
    public Coupon(){
	}

    /**
     *
     * @param id id 
     * @param title title    
     * @param startDate startDate
     * @param endDate endDate
     * @param amount amount
     * @param type type
     * @param massage massage
     * @param price price
     * @param imgPath imgPath
     */
    public Coupon(long id,String title, Timestamp startDate, Timestamp endDate, int amount, CouponType type, String massage, double price,
			String imgPath ) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type=type;
		this.massage = massage;
		this.price = price;
		this.imgPath = imgPath;
		this.id = id;
	}
//Get's and Set's for Coupon Fields
    /**
     *
     * @return long id
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
     * @return title
     */
    public String getTitle() {
		return title;
	}

    /**
     *
     * @param title  setTitle
     */
    public void setTitle(String title) {
		this.title = title;
	}

    /**
     *
     * @return startDate
     */
    public Timestamp getStartDate() {
		return startDate;
	}

    /**
     *
     * @param startDate set startDate
     */
    public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

    /**
     *
     * @return endDate
     */
    public Timestamp getEndDate() {
		return endDate;
	}

    /**
     *
     * @param endDate set endDate
     */
    public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

    /**
     *
     * @return amount 
     */
    public int getAmount() {
		return amount;
	}

    /**
     *
     * @param amount amount
     */
    public void setAmount(int amount) {
		this.amount = amount;
	}

    /**
     *
     * @return type
     */
    public CouponType getType() {
		return type;
	}

    /**
     *
     * @param type setType
     */
    public void setType(CouponType type) {
		this.type = type;
	}

    /**
     *
     * @return massage
     */  
    public String getMassage() {
		return massage;
	}

    /**
     *
     * @param massage setMassage
     */
    public void setMassage(String massage) {
		this.massage = massage;
	}

    /**
     *
     * @return price
     */
    public double getPrice() {
		return price;
	}

    /**
     *
     * @param price setPrice
     */
    public void setPrice(double price) {
		this.price = price;
	}

    /**
     *
     * @return imgPath
     */
    public String getImgPath() {
		return imgPath;
	}

    /**
     *
     * @param imgPath setImgPath
     */
    public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public String toString() {
		return "Coupon (" + id + "," + title + "," + startDate+ "',"+endDate + "," + amount
				+ "," + type + "," + massage + "," + price + "," + imgPath 
				+ ")";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	
	//Equals of Company to check if Coupon title is equal
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
