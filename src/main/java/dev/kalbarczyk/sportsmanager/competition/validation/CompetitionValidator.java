package dev.kalbarczyk.sportsmanager.competition.validation;

import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

/**
 * Validator for {@link Competition} entities, ensuring that name, discipline, and date
 * are provided and that the date is not in the past.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public final class CompetitionValidator implements Validator {

    /**
     * Checks if the given class is supported by this validator.
     *
     * @param clazz the class to check
     * @return true if clazz is assignable from {@link Competition}, false otherwise
     */
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Competition.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the given {@link Competition} object.
     *
     * @param target the object to validate
     * @param errors the errors object to store validation errors
     */
    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        val competition = (Competition) target;
        if (competition.getName().trim().isEmpty()) {
            errors.rejectValue("name", "competition.name.empty", "Competition name is required.");
        }

        if (competition.getDiscipline() == null) {
            errors.rejectValue("discipline", "competition.discipline.empty", "Discipline is required.");
        }

        if (competition.getDate() == null) {
            errors.rejectValue("date", "competition.date.empty", "Competition date is required.");
        } else if (competition.getDate().isBefore(LocalDate.now())) {
            errors.rejectValue("date", "competition.date.before.date", "Competition date is in the past.");
        }
    }
}
