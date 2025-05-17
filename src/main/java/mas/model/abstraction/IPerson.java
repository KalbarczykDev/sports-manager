package mas.model.abstraction;

import mas.model.attribute.Address;

public interface IPerson {
    String getName();

    void setName(String name);

    String getSurname();

    void setSurname(String surname);

    Address getAddress();

    void setAddress(Address address);
}
