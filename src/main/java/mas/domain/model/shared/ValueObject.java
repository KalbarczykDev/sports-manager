package mas.domain.model.shared;

import java.io.Serializable;

public interface ValueObject<T> extends Serializable {
    T getValue();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    @Override
    String toString();
}
