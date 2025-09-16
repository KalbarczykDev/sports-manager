package dev.kalbarczyk.sportsmanager.common.service;

import java.util.List;

public interface CountryService {
    /**
     * Checks if the given country name is valid.
     *
     * @param name country name to check
     * @return true if valid, false otherwise
     */
    boolean countryNamesContain(final String name);

    /**
     * Returns the sorted list of country names for forms.
     *
     * @return list of country names
     */
    List<String> getCountriesForForm();

}
