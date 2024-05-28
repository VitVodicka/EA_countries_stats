package com.example.ea_countries_stats.domain.country;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private List<Country> countries = new ArrayList<>();

    public List<Country> getAllCountries() {
        return countries;
    }

    public Optional<Country> getCountry(Long id) {
        return countries.stream().filter(account -> account.getCountryId().equals(id))
                .findFirst();
    }
    public void addCountry(Country country) {
        countries.add(country);

    }
}
