package com.example.ea_countries_stats.domain.country;

import com.example.ea_countries_stats.utils.exceptions.NotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryRequest {
    @NotNull
    private Long countryId;

    @NotNull
    private String countryTxt;

    @NotNull
    private String isocode;

    public void toCountry(Country country, CountryService countryService) {
        country.setCountryId(this.countryId);
        country.setCountryTxt(this.countryTxt);
        country.setIsocode(this.isocode);
    }

}
