package mas.domain.model.coach;

import mas.domain.model.shared.ValueObject;

public  enum Specialization implements ValueObject<String> {
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

    @Override
    public String getValue() {
        return specialization;
    }
}