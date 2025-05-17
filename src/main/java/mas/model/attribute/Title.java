package mas.model.attribute;


import java.io.Serializable;

public final class Title implements Serializable {

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

    private static void validate(String name, String description) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if(description == null || description.isEmpty()){
            throw new IllegalArgumentException("Description cannot be empty");
        }
    }


    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
