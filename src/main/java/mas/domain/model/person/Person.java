package mas.domain.model.person;

import mas.domain.model.shared.Address;
import mas.util.Util;
import mas.infrastructure.repository.ObjectExtent;

import java.util.Objects;

public abstract class Person extends ObjectExtent implements IPerson {
    private Name name;
    private Surname surname;
    private Address address;

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public void setName(Name name) {
        Util.require(name != null, "Name cannot be null");
        this.name = name;
    }

    @Override
    public Surname getSurname() {

        return surname;
    }

    @Override
    public void setSurname(Surname surname) {
        Util.require(surname != null, "Surname cannot be null");
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