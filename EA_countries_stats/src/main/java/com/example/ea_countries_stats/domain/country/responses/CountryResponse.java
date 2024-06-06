package com.example.ea_countries_stats.domain.country.responses;

import com.example.ea_countries_stats.domain.country.Country;

import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttackResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CountryResponse {

    @NotNull
    private Long countryId;

    @NotNull
    private String countryTxt;

    @NotNull
    private String isocode;

    private List<TerroristAttackResponse> attacks;

    public CountryResponse(Country country) {
        this.countryId = country.getCountryId();
        this.countryTxt = country.getCountryTxt();
        this.isocode = country.getIsoCode();
        if(country.getTerroristAttacksList().size()>0) {
            this.attacks = country.getTerroristAttacksList().stream()
                    .map(TerroristAttackResponse::new)
                    .collect(Collectors.toList());
        }
    }
}