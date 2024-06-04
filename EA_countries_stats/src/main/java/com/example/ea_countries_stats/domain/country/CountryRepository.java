package com.example.ea_countries_stats.domain.country;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Long> {
    List<Country> findAll();
    // ...
    Country findCountryByCountryIdEquals(long id);
}