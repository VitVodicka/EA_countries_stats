package com.example.ea_countries_stats.domain.country;

import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttack;
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

    TerroristAttack attack;
    int numberTerroristAttacks;

    public CountryResponse(Country country) {
        this.countryId = country.getCountryId();
        this.countryTxt = country.getCountryTxt();
        this.isocode = country.getIsoCode();
        this.numberTerroristAttacks = country.getTerroristAttacksList().size();
        if (!country.getTerroristAttacksList().isEmpty()) {
            // Assuming you want to take the first attack if multiple exist
            this.attack = country.getTerroristAttacksList().get(0);
        }
    }
}
