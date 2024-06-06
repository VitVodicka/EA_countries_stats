package com.example.ea_countries_stats.domain.country;

import com.example.ea_countries_stats.domain.country.responses.CountryProbabilityResponse;
import com.example.ea_countries_stats.domain.country.responses.CountryResponse;
import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttackService;
import com.example.ea_countries_stats.utils.exceptions.DatabaseAccessException;
import com.example.ea_countries_stats.utils.exceptions.InternalServerErrorException;
import com.example.ea_countries_stats.utils.exceptions.NotFoundException;
import com.example.ea_countries_stats.utils.response.ArrayResponse;
import com.example.ea_countries_stats.utils.response.ObjectResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        try {
            List<Country> listCountry = countryService.getAllCountries();
            return ArrayResponse.of(listCountry, CountryResponse::new);
        }
        catch (DataAccessException e) {
            throw new DatabaseAccessException("Error occurred while accessing the database", e);
        }
        catch (RuntimeException e) {
            throw new InternalServerErrorException("An unexpected error occurred", e);
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<CountryResponse> getCountry(@PathVariable Long id) {
        try {
        Country country = countryService.getCountry(id).orElseThrow(() -> new NotFoundException("not found country"+id));
        return ObjectResponse.of(country, CountryResponse::new);}
        catch (DataAccessException e) {
            throw new DatabaseAccessException("Error occurred while accessing the database", e);
        } catch (NotFoundException e) {
            throw new NotFoundException("country with id not found"); // Rethrow NotFoundException for custom handling
        }
    }

    @GetMapping(value = "/probabilites", produces = "application/json")
    @Valid
    public ArrayResponse<CountryProbabilityResponse> getCountriesProbabilities() {
        try {
        List<CountryProbabilityResponse> probabilities = countryService.getCountriesWithProbabilities();
        return ArrayResponse.of(probabilities, response -> response);
        }
        catch (DataAccessException e) {
            throw new DatabaseAccessException("Error occurred while accessing the database", e);

        }
        catch (RuntimeException e) {
            throw new InternalServerErrorException("An unexpected error occurred", e);
        }


    }
}
