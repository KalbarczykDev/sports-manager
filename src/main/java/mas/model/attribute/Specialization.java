package mas.model.attribute;

import java.io.Serializable;

public  enum Specialization implements Serializable {
    STRIKING("striking"),
    GRAPPLING("grappling"),
    ENDURANCE("endurance");

    private final String specialization;

    Specialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

}