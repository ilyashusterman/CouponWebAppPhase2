package facadedb;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import exceptions.FacadeException;
import servicebeans.Coupon;
import servicebeans.CouponType;
/**
 * this class use as a filter for collections of coupons
 * @author ilya shusterman
 */
public class FilterFacade {
/**
 * uses filter object can be Double , Timestamp or String as enumeration
 * @param filter filter
 * @param className class name 
 * @param coupons collections of coupons
 * @return Collection(Coupon)
 * @throws FacadeException  FacadeException
 */
	public static Collection<Coupon> getCouponsbyFilter
	(Object filter,String className,Collection<Coupon> coupons) throws FacadeException{
		ArrayList<Coupon> queryCoupons = new ArrayList<>();
		ArrayList<Coupon> allCoupons =(ArrayList<Coupon>) coupons;
		if(allCoupons.get(0)!=null){
		switch (className) {
		case "price": for (Coupon coupon : allCoupons) {if (coupon.getPrice()<=((Double)filter))
		{queryCoupons.add(coupon);}}break;
		case "date":		for (Coupon coupon : allCoupons) {if (coupon.getEndDate().before((Timestamp)filter))
				 {queryCoupons.add(coupon);}}break;
		case "couponType":		for (Coupon coupon : allCoupons) 
		{if (coupon.getType().equals((CouponType)filter)) 
			{queryCoupons.add(coupon);}}break;
		default:
			break;
		}
		}
		return queryCoupons;
	}
}
