package mas.model;

import mas.model.attribute.Address;
import mas.util.Util;
import mas.model.data.ObjectExtent;

import java.util.Objects;

public abstract class Person extends ObjectExtent implements IPerson {
    private String name;
    private String surname;
    private Address address;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        Util.require(name != null, "Name cannot be null");

        Util.require(!name.isEmpty(), "Name is empty");

        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        Util.require(surname != null, "Surname cannot be null");
        Util.require(!surname.isEmpty(), "Surname is empty");
        this.surname = surname;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void setAddress(Address address) {
        Util.require(address != null, "Address cannot be null");
        this.address = address;
    }

    @Override
    public void removeFromExtent() {
        super.removeFromExtent();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person other)) return false;
        return name.equals(other.name) && surname.equals(other.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

}