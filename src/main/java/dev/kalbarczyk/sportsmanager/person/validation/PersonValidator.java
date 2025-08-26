package dev.kalbarczyk.sportsmanager.person.validation;


import dev.kalbarczyk.sportsmanager.common.service.DefaultCountryService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class PersonValidator implements Validator {

    private final DefaultCountryService countryService;

    public PersonValidator(final DefaultCountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public boolean supports(final @NonNull Class<?> aClass) {
        return Competitor.class.equals(aClass);
    }

    @Override
    public void validate(@NonNull Object o, @NonNull Errors errors) {
        val competitor = (Competitor) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "Name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "surname.empty", "Surname is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "country.empty", "Country is required.");

        if (competitor.getName().length() < 2 || competitor.getName().length() > 50) {
            errors.rejectValue("name", "name.size", "Name must be between 2 and 50 characters.");
        }

        if (competitor.getSurname().length() < 2 || competitor.getSurname().length() > 50) {
            errors.rejectValue("surname", "surname.size", "Surname must be between 2 and 50 characters.");
        }

        if (competitor.getSalary() < 0) {
            errors.rejectValue("salary", "salary.empty", "Salary cannot be negative.");
        }

        if (!countryService.countryNamesContain(competitor.getCountry())) {
            log.warn("Validation failed for country: '{}'", competitor.getCountry());
            errors.rejectValue("country", "country.invalid", "The selected country is not valid.");
        }
    }
}
