package dev.kalbarczyk.sportsmanager.competition.validation;

import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
    }
}
