package dev.kalbarczyk.sportsmanager.person.validation;


import dev.kalbarczyk.sportsmanager.common.service.CountryService;
import dev.kalbarczyk.sportsmanager.person.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link Person} entities.
 * Ensures that fields are not empty and satisfy basic constraints,
 * and validates the country using {@link CountryService}.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class PersonValidator implements Validator {

    private final CountryService countryService;

    /**
     * Checks if the validator supports the given class.
     *
     * @param clazz the class to check
     * @return true if clazz is assignable from {@link Person}
     */
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Person.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the {@link Person} object.
     *
     * @param target the object to validate
     * @param errors the errors object to store validation errors
     */
    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        val person = (Person) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "Name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "surname.empty", "Surname is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "country.empty", "Country is required.");


        if (person.getName().length() < 2 || person.getName().length() > 50) {
            errors.rejectValue("name", "name.size", "Name must be between 2 and 50 characters.");
        }

        if (person.getSurname().length() < 2 || person.getSurname().length() > 50) {
            errors.rejectValue("surname", "surname.size", "Surname must be between 2 and 50 characters.");
        }

        if (person.getSalary() < 0) {
            errors.rejectValue("salary", "salary.empty", "Salary cannot be negative.");
        }

        if (!countryService.countryNamesContain(person.getCountry())) {
            log.warn("Validation failed for country: '{}'", person.getCountry());
            errors.rejectValue("country", "country.invalid", "The selected country is not valid.");
        }
    }
}
