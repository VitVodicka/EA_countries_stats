package com.example.ea_countries_stats.domain.country;

import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttackRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private CountryRepository repository;
    private TerroristAttackRepository attackRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository, TerroristAttackRepository attackRepository) {
        this.repository = countryRepository;
        this.attackRepository = attackRepository;
    }

    public List<Country> getAllCountries() {

        return repository.findAll();

    }

    public Optional<Country> getCountry(Long id) {
        return repository.findById(id); // Correct way to find by ID
    }
    public Country addCountry(Country country) {
        return repository.save(country);
        //countries.add(country);

    }
    public void saveCountries(List<Country> countries){
        repository.saveAll(countries);
    }
}
