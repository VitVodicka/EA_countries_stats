package com.example.ea_countries_stats.domain.terroristAttack;

import com.example.ea_countries_stats.domain.country.Country;
import com.example.ea_countries_stats.domain.country.CountryService;
import com.example.ea_countries_stats.utils.exceptions.CountryNotFound;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerroristAttackRequest {


        @NotNull
        private Timestamp date;

        @NotNull
        private String location;

        @NotNull
        private String target;

        @NotNull
        private Integer casualities;

        @NotNull
        private Long country_id;

        public void toTerroristAttack(TerroristAttack terroristAttack, CountryService countryService) {
            terroristAttack.setDate(date);
            terroristAttack.setLocation(location);
            terroristAttack.setTarget(target);
            terroristAttack.setCasualties(casualities);

            Country country = countryService.getCountry(country_id).orElseThrow(() -> new CountryNotFound());
            terroristAttack.setCountry(country);

        }



}
