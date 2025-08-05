package dev.kalbarczyk.sportsmanager.common.service;

import java.util.List;

public interface CountryService {
    boolean countryNamesContain(final String name);

    List<String> getCountriesForForm();

}
