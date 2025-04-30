package mas.domain.model.person;

import mas.domain.model.shared.Address;

public interface IPerson {
    Name getName();

    void setName(Name name);

    Surname getSurname();

    void setSurname(Surname surname);

    Address getAddress();

    void setAddress(Address address);
}
