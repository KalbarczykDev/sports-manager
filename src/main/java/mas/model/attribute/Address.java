package mas.model.attribute;




import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {

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

        if(streetNumber < 0){
            throw new IllegalArgumentException("streetName cannot be negative");
        }

        if(streetName == null || streetName.isEmpty()){
            throw new IllegalArgumentException("streetName cannot be empty");
        }

        if(city == null || city.isEmpty()){
            throw new IllegalArgumentException("city cannot be empty");
        }

        if(state == null || state.isEmpty()){
            throw new IllegalArgumentException("state cannot be empty");
        }

        if(zip == null || zip.isEmpty()){
            throw new IllegalArgumentException("zip cannot be empty");
        }


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
