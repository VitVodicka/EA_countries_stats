package com.example.ea_countries_stats.utils.data;

import com.example.ea_countries_stats.domain.country.Country;
import com.example.ea_countries_stats.domain.country.CountryService;
import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttackService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class Seeder {

    private final CountryService countryService;
    private final TerroristAttackService terroristAttackService;

    public Seeder(CountryService countryService, TerroristAttackService terroristAttackService, TerroristAttackService terroristAttackService1) {
        this.countryService = countryService;

        this.terroristAttackService = terroristAttackService1;
    }
    private boolean shouldSeedData() {
        return countryService.getAllCountries().isEmpty();
    }

    @PostConstruct
    public void seedDefaultData() {
        if (!shouldSeedData()) {
            log.info("--- Default data already seeded ---");
            return;
        }

        //Country country1 = new Country( 1L, "Czechia", "151-05", new ArrayList<>());
        //Country country2 = new Country( 2L, "CzechoSlovaika", "151-05", new ArrayList<>());

        Country country1 = new Country(  "Czechia", "151-05");
        Country country2 = new Country(  "CzechoSlovaika", "151-05");

        country1=countryService.addCountry(country1);
        country2= countryService.addCountry(country2);

        log.info("--- Default data seeded ---");
    }


}
