package com.example.ea_countries_stats.domain.country;

import com.example.ea_countries_stats.domain.country.responses.CountryProbabilityResponse;
import com.example.ea_countries_stats.domain.country.responses.CountryResponse;
import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttackService;
import com.example.ea_countries_stats.utils.exceptions.NotFoundException;
import com.example.ea_countries_stats.utils.response.ArrayResponse;
import com.example.ea_countries_stats.utils.response.ObjectResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("countries")
@Validated
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(value = "", produces = "application/json")
    @Valid
    public ArrayResponse<CountryResponse> getCountries() {
        List<Country> listCountry = countryService.getAllCountries();
        return ArrayResponse.of(listCountry, CountryResponse::new);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<CountryResponse> getCountry(@PathVariable Long id) {
        Country country = countryService.getCountry(id).orElseThrow(NotFoundException::new);
        return ObjectResponse.of(country, CountryResponse::new);
    }

    @GetMapping(value = "/probabilites", produces = "application/json")
    @Valid
    public ArrayResponse<CountryProbabilityResponse> getCountriesProbabilities() {
        List<CountryProbabilityResponse> probabilities = countryService.getCountriesWithProbabilities();
        return ArrayResponse.of(probabilities, response -> response);
    }
}
