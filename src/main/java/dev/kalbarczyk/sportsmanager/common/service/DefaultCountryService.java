package dev.kalbarczyk.sportsmanager.common.service;

import lombok.val;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
public class DefaultCountryService implements CountryService {

    private final List<String> countriesForForm;

    private final Set<String> validCountryNames;


    public DefaultCountryService() {
        val countryCodes = Arrays.stream(Locale.getISOCountries()).collect(Collectors.toSet());

        this.validCountryNames = countryCodes.stream()
                .map(code -> new Locale("", code).getDisplayCountry(Locale.ENGLISH))
                .collect(Collectors.toSet());

        this.countriesForForm = validCountryNames.stream()
                .sorted()
                .toList();
    }


    @Override
    public boolean countryNamesContain(final String name) {
        return validCountryNames.contains(name);
    }

    @Override
    public List<String> getCountriesForForm() {
        return countriesForForm;
    }


}
