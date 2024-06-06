package com.example.ea_countries_stats.domain.country;

import com.example.ea_countries_stats.domain.country.responses.CountryProbabilityResponse;
import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository repository;
    private final TerroristAttackRepository attackRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository, TerroristAttackRepository attackRepository) {
        this.repository = countryRepository;
        this.attackRepository = attackRepository;
    }

    public List<Country> getAllCountries() {
        return repository.findAll();
    }

    public Optional<Country> getCountry(Long id) {
        return repository.findById(id);
    }

    public Country addCountry(Country country) {
        return repository.save(country);
    }

    public void saveCountries(List<Country> countries) {
        repository.saveAll(countries);
    }

    public List<CountryProbabilityResponse> getCountriesWithProbabilities() {
        List<Country> countries = getAllCountries();
        long totalAttacks = attackRepository.count();

        List<CountryProbabilityResponse> result = new ArrayList<>();
        for (Country country : countries) {
            int countryAttacks = country.getTerroristAttacksList().size();
            double probability = totalAttacks > 0 ? (double) countryAttacks / totalAttacks : 0;
            CountryProbabilityResponse response = new CountryProbabilityResponse(country, probability);
            result.add(response);
        }
        return result;
    }
}
