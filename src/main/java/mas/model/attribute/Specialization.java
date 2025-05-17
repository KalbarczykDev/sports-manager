package mas.model.attribute;

public  enum Specialization  {
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