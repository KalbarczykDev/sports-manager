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

@Component
@Slf4j
@RequiredArgsConstructor
public class CompetitionValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Competition.class.isAssignableFrom(clazz);
    }

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
