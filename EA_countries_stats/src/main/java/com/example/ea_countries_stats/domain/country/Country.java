package com.example.ea_countries_stats.domain.country;

import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttack;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Country {
    @NotNull
    private Long id;

    @NotNull
    private Long countryId;

    @NotNull
    private String countryTxt;

    @NotNull
    private String isocode;

    @NotNull
    private List<TerroristAttack> terroristAttacksList = new ArrayList<>();

    public Long getId(){
        return id;
    }
    public Long getCountryId(){
        return countryId;
    }
    public String getCountryText(){
        return countryTxt;
    }
    public String getIsoCode(){
        return isocode;
    }

}
