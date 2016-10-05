package model.Users;

/**
 * Created by Alexandra on 10/4/2016.
 */
public class Address {
    // Common optional fields like apt# and zipExtension will be -1 if not
    // specified. For objects, these optional fields will be null.
    private String street, city, state, country;
    private int apt, zip, zipExtension;

    /**
     * Makes a new Address object
     * @param street user's street name and number
     * @param apt user's apartment number
     * @param city user's city of residence
     * @param state user's state of residence
     * @param zip user's zip code
     * @param zipExtension user's zip extension
     * @param country user's country of residence
     */
    public Address(String street, int apt, String city, String state,
                   int zip, int zipExtension, String country) {
        this.street = street;
        this.apt = apt;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.zipExtension = zipExtension;
        this.country = country;
    }

    /**
     * Makes a new Address object
     * @param street user's street name and number
     * @param apt user's apartment number
     * @param city user's city of residence
     * @param state user's state of residence
     * @param zip user's zip code
     * @param country user's country of residence
     */
    public Address(String street, int apt, String city, String state, int zip, String country) {
        this(street, apt, city, state, zip, -1, country);
    }

    /**
     * Makes a new Address object
     * @param street user's street name and number
     * @param city user's city of residence
     * @param state user's state of residence
     * @param zip user's zip code
     * @param country user's country of residence
     */
    public Address(String street, String city, String state, int zip, String country) {
        this(street, -1, city, state, zip, -1, country);
    }

    /**
     * Makes a new Address object
     * @param street user's street name and number
     * @param city user's city of residence
     * @param zip user's zip code
     * @param country user's country of residence
     */
    public Address(String street, String city, int zip, String country) {
        this(street, -1, city, null, zip, -1, country);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int apartmentNumber() {
        return apt;
    }

    public void setApartmentNum(int num) {
        this.apt = num;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getZipExtension() {
        return zipExtension;
    }

    public void setZipExtension(int zipExtension) {
        this.zipExtension = zipExtension;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        String str = "";
        str += (street == null) ? "" : street;
        str += (apt == -1) ? "" : " " + apt;
        str += (city == null) ? "" : " " + city;
        str += (state == null) ? "" : " " + state;
        str += (zip == -1) ? "" : " " + zip;
        str += (zipExtension == -1) ? "" : "-" + zipExtension;
        str += (country == null) ? "" : " " + country;
        return str;
    }
}
