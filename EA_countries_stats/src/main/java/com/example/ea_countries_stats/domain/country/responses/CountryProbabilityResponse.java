package com.example.ea_countries_stats.domain.country.responses;


import com.example.ea_countries_stats.domain.country.Country;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CountryProbabilityResponse {
    @NotNull
    private Long countryId;
    @NotNull
    private String countryTxt;
    @NotNull
    private String isocode;
    @NotNull
    private double probability;

    public CountryProbabilityResponse(Country country, double probability) {
        this.countryId = country.getCountryId();
        this.countryTxt = country.getCountryTxt();
        this.isocode = country.getIsoCode();
        this.probability = probability;
    }
}