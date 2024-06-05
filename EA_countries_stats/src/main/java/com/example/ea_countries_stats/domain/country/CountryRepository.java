package com.example.ea_countries_stats.domain.country;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Long> {
    @EntityGraph(attributePaths = "terroristAttacksList")
    List<Country> findAll();
    // ...
    Country findCountryByCountryIdEquals(long id);
}