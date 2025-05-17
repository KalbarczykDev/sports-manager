package mas.model.attribute;


import mas.util.Util;

import java.util.Objects;

public class Address {

    private final int streetNumber;
    private final String streetName;
    private final String city;
    private final String state;
    private final String zip;

    private Address(
            int streetNumber,
            String streetName,
            String city,
            String state,
            String zip
    ) {

        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public static Address of(int streetNumber, String streetName, String city, String state, String zip) {
        validate(streetNumber, streetName, city, state, zip);
        return new Address(streetNumber, streetName, city, state, zip);
    }

    private static void validate(int streetNumber, String streetName, String city, String state, String zip) {
        Util.require(streetNumber > 0, "Street number must be positive");
        Util.require(streetName != null && !streetName.isEmpty(), "Street name cannot be null or empty");
        Util.require(city != null && !city.isEmpty(), "City cannot be null or empty");
        Util.require(state != null && !state.isEmpty(), "State cannot be null or empty");
        Util.require(zip != null && !zip.isEmpty(), "Zip cannot be null or empty");
    }


    public int getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Address address = (Address) obj;
        return streetNumber == address.streetNumber &&
                streetName.equals(address.streetName) &&
                city.equals(address.city) &&
                state.equals(address.state) &&
                zip.equals(address.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetNumber, streetName, city, state, zip);
    }

    @Override
    public String toString() {
        return "{" +
                "streetNumber=" + streetNumber +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
