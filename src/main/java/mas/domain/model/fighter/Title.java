package mas.domain.model.fighter;


import mas.domain.model.shared.ValueObject;
import mas.util.Util;

public final class Title implements ValueObject {

    private final String name;
    private final String description;

    private Title(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Title of(String name, String description) {
        validate(name, description);
        return new Title(name, description);
    }


    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return this;
    }

    private static void validate(String name, String description) {
        Util.require(name != null, "Name cannot be null");
        Util.require(description != null, "Description cannot be null");
        Util.require(!name.isEmpty(), "Name cannot be empty");
        Util.require(!description.isEmpty(), "Description cannot be empty");
    }


    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
