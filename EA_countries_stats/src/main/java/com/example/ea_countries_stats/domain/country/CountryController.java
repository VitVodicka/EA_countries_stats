package com.example.ea_countries_stats.domain.country;

import com.example.ea_countries_stats.utils.exceptions.NotFoundException;
import com.example.ea_countries_stats.utils.response.ArrayResponse;
import com.example.ea_countries_stats.utils.response.ObjectResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        return ArrayResponse.of(
                countryService.getAllCountries(),
                CountryResponse::new
        );
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<CountryResponse> getCountry(@PathVariable Long id) {
        Country country = countryService.getCountry(id).orElseThrow(NotFoundException::new);


        return ObjectResponse.of(country, CountryResponse::new);
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<CountryResponse> createCountry (@RequestBody @Valid CountryRequest countryRequest) {
    //TODO check for id
        if (!countryService.getAllCountries().contains(countryRequest.getCountryId())) {
            Country country = new Country();
            countryRequest.toCountry(country, countryService);
            countryService.addCountry(country);
            return ObjectResponse.of(country, CountryResponse::new);
        } else {
            throw new NotFoundException();
        }


    }

}
