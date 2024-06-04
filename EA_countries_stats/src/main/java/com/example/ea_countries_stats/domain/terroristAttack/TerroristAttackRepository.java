package com.example.ea_countries_stats.domain.terroristAttack;

import com.example.ea_countries_stats.domain.country.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TerroristAttackRepository extends CrudRepository<TerroristAttack, Long> {
    List<TerroristAttack> findAll();
    List<TerroristAttack> findByCountry_CountryId(Long countryId);
}

