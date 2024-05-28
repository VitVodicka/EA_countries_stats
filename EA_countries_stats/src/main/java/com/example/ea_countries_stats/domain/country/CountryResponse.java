package com.example.ea_countries_stats.domain.country;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CountryResponse {

    @NotNull
    private Long countryId;

    @NotNull
    private String countryTxt;

    @NotNull
    private String isocode;

    public CountryResponse(Country country) {
        this.countryId = country.getCountryId();
        this.countryTxt = country.getCountryTxt();
        this.isocode = country.getIsoCode();
    }
}
