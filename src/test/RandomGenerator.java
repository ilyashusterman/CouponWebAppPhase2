package test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import servicebeans.Company;
import servicebeans.Customer;
import servicebeans.Coupon;
import servicebeans.CouponType;

/**
 *Generator beans class 
 * @author ilya shusterman 
 */
public class RandomGenerator {
/**
 * class fields
 */
    final Set<String> identifiers = new HashSet<String>();
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    private Random rand = new Random();
    String[] companies = {"Loria", "Kourtney", "Letty", "Delia ", "Brenda ", "Arlyne", "Luana", "Ava", "Zetta",
        "Tressie", "Delpha", "Artie", "Sherry", "Julie", "Vernell", "Berniece", "Tisa ", "Marietta", "Elease",
        "Edwina", "Hortensia", "Yuonne", "Tia", "Cathry", "Zora", "Krystina", "Annie ", "Marx", "Olevia",
        "Florrie", "Laquita ", "Sherrie", "Margrett", "Roseanne", "Tesha", "Evelyn", "Astrid", "Enda", "Bridget",
        "Tammera", "Hannelore", "Lavenia"};

    String[] couponName = {"easier",
        "interesting", " honest", "forests", "Saturday", "dinner", "comfortable", "gently", "fresh", "pal",
        "warmth", "rest", "welcome", "dearest", "useful", "cherry", "safe", "better", "piano", "silk", " relief",
        "rhyme", "hi", "agree", "water"};

    private static char[] letters = new char[52];

    static {
        for (int i = 0; i < 26; i++) {
            letters[i] = (char) ('A' + i);
        }

        for (int i = 0; i < 26; i++) {
            letters[i + 26] = (char) ('a' + i);
        }
    }

    /**
     *
     */
    public RandomGenerator() {
    }

    /**
     *
     * @return random Customer
     */
    public Customer getRandomCustomer() {
        Customer customer = new Customer();

        customer.setCustName(randomName());
        customer.setPassword(getRandomPassword());

        return customer;
    }

    /**
     *
     * @return random Company
     */
    public Company getRandomCompany() {
        Company company = new Company();

        company.setCompName(randomName());
        company.setPassword(getRandomPassword());
        company.setEmail(getEmailString());

        return company;
    }

    /**
     *
     * @return random Coupon
     */
    public Coupon getRandomCoupon() {
        Coupon coupon = new Coupon();
        coupon.setAmount(getRndAmount(50));
        coupon.setStartDate(generateDate(2016));
        coupon.setEndDate(generateDate(2018));
        coupon.setType(generateCouponType());
        coupon.setTitle(randomCouponName());
        coupon.setMassage(getRandomString(50));
        coupon.setImgPath(getRandomString(20));
        coupon.setPrice(getRandomPrice());
        return coupon;
    }

    /**
     *
     * @param size size
     * @return Coupon[] by the size
     */
    public Coupon[] getRandomCouponArray(int size) {
        Coupon[] array = new Coupon[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = getRandomCoupon();
        }
        return array;
    }

    /**
     *
     * @param size size 
     * @return Company[] by the size
     */
    public Company[] getRandomCompanyArray(int size) {
        Company[] array = new Company[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = getRandomCompany();
        }
        return array;
    }

    /**
     *
     * @param size size
     * @return Customer[] by the size
     */
    public Customer[] getRandomCustomerArray(int size) {
        Customer[] array = new Customer[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = getRandomCustomer();
        }
        return array;
    }
/**
 * 
 * @return random Email String
 */
    private String getEmailString() {
        StringBuilder builder = new StringBuilder();

        builder.append(getRandomString(6));
        builder.append('@');
        builder.append(getRandomString(5));
        builder.append(".com");

        return builder.toString();
    }

    private double getRandomPrice() {
        return rand.nextDouble() * 100;
    }

    private int getRndAmount(int maxAmount) {
        return (int) ((Math.random() * maxAmount) + 10);
    }

    private Timestamp generateDate(long startYear) {
        long offset = Timestamp.valueOf(startYear + "-01-01 00:00:00").getTime();
        long end = new Timestamp((new java.util.Date()).getTime()).getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
        return rand;
    }

    private CouponType generateCouponType() {
        return CouponType.values()[rand.nextInt(7)];
    }

    private String getRandomString(int length) {
        char[] charArray = new char[length];

        for (int i = 0; i < length; i++) {
            charArray[i] = letters[rand.nextInt(52)];
        }

        String randomString = new String(charArray);

        return randomString;
    }

    /**
     *
     * @return random name string
     */
    public String randomName() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(2) + 2;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        List<String> names = Arrays.asList(companies);
        int index = new Random().nextInt(names.size());
        return names.get(index) + builder.toString();
    }

    /**
     *
     * @return random title coupon string name
     */
    public String randomCouponName() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(2) + 2;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        List<String> names = Arrays.asList(couponName);
        int index = new Random().nextInt(names.size());
        return names.get(index) + builder.toString();
    }

    private String getRandomPassword() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            if (rand.nextInt(2) == 0) {
                builder.append(getRandomString(1));
            } else {
                builder.append(letters[rand.nextInt(51)]);
            }
        }
        return builder.toString();
    }
//	public static void main(String[] args) {
//		for (int i = 0; i < 10; i++) {
//			long number =ThreadTests.getRandomSleep();
//			System.out.println("number is :"+number);
//		}
//	}
}
