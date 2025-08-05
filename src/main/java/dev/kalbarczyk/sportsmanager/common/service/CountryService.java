package dev.kalbarczyk.sportsmanager.common.service;

import java.util.List;

public interface CountryService {
    boolean countryNamesContain(String name);

    List<String> getCountriesForForm();
}
