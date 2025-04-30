package mas.domain.model.Influencer;

import mas.domain.model.shared.ValueObject;
import mas.util.Util;

public class SocialMediaHandle implements ValueObject<String> {
    private final String value;

    private SocialMediaHandle(String value) {
        this.value = value;
    }

    public static SocialMediaHandle of(String value) {
        validate(value);
        return new SocialMediaHandle(value);
    }

    private static void validate(String value) {
        Util.require(value != null, "SocialMediaHandle is null");
        Util.require(!value.isEmpty(), "SocialMediaHandle is empty");
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "@" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SocialMediaHandle other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
