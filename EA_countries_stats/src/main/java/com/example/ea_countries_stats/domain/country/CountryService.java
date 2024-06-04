package com.example.ea_countries_stats.domain.country;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private CountryRepository repository;

    public CountryService(CountryRepository repository){
        this.repository= repository;
    }

    public List<Country> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        repository.findAll().forEach(countries::add);
        return countries;
    }

    public Optional<Country> getCountry(Long id) {
        return repository.findById(id); // Correct way to find by ID
    }
    public Country addCountry(Country country) {
        return repository.save(country);
        //countries.add(country);

    }
}
