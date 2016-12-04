package model.Users;

/**
 * Created by Alexandra on 10/4/2016.
 * Represents an Address
 */
public class Address {
    // Common optional fields like apt# will be -1 if not
    // specified. For objects, these optional fields will be null.
    private String street;
    private String city;
    private String state;
    private String country;
    private int apt;
    private int zip;

    /**
     * Makes a new Address object
     * @param street user's street name and number
     * @param apt user's apartment number
     * @param city user's city of residence
     * @param state user's state of residence
     * @param zip user's zip code
     * @param country user's country of residence
     */
    public Address(String street, int apt, String city, String state, int zip,
                   String country) {
        this.street = street;
        this.apt = apt;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    /**
     * Makes a new Address object
     * @param street user's street name and number
     * @param city user's city of residence
     * @param state user's state of residence
     * @param zip user's zip code
     * @param country user's country of residence
     */
    public Address(String street, String city, String state, int zip,
                   String country) {
        this(street, -1, city, state, zip, country);
    }

    /**
     * Makes a new Address object
     * @param street user's street name and number
     * @param city user's city of residence
     * @param zip user's zip code
     * @param country user's country of residence
     */
    public Address(String street, String city, int zip, String country) {
        this(street, -1, city, "", zip, country);
    }

    /**
     * Gets the street name and number of this Address
     * @return a String representing this Address's street name
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street name and number of this Address to the one provided
     * @param street the updates street name of this Address
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the apartment number associated with this Address
     * @return an int representing this Address's apartment number, or
     * -1 if this Address does not have an apartment number
     */
    public int apartmentNumber() {
        return apt;
    }

    /**
     * Sets this Address's apartment number to the one provided
     * @param num the updated apartment number of this Address
     */
    public void setApartmentNum(int num) {
        this.apt = num;
    }

    /**
     * Gets the city part of this Address
     * @return a String representing the city this Address is in
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets this Address's city to the one provided
     * @param city the updated city of this Address
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the state name this Address is in
     * @return a String representing the State this address is in
     */
    public String getState() {
        return state;
    }

    /**
     * Sets this Address's state to the one provided
     * @param state the updated state this Address is in
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets this Address's zip code
     * @return the zip code of this Address
     */
    public int getZip() {
        return zip;
    }

    /**
     * Sets the zip code of this Address to the one provided
     * @param zip the zip code of this Address
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * Gets the country that this Address is in
     * @return a String representing the country's name this Address is in
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets this Address's country to the provided one
     * @param country the updated country of this Address
     */
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        String str = "";
        str += (street == null) ? "/" : street;
        str += (apt == -1) ? "/" : ("/" + apt);
        str += (city == null) ? "/" : ("/" + city);
        str += (state == null) ? "/" : ("/" + state);
        str += (zip == -1) ? "/" : ("/" + zip);
        str += (country == null) ? "/" : ("/" + country);
        return str;
    }
}
