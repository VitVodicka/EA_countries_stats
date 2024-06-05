package com.example.ea_countries_stats.domain.terroristAttack;

import com.example.ea_countries_stats.domain.country.Country;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
@Data
public class TerroristAttackResponse {

    @NotNull
    private Date date;

    @NotNull
    private String location;

    @NotNull
    private String target;

    @NotNull
    private Integer casualties;

    @NotNull
    private Long countryId;


    public TerroristAttackResponse(TerroristAttack terroristAttack) {
        this.date=terroristAttack.getDate();
        this.location = terroristAttack.getLocation();
        this.target= terroristAttack.getTarget();
        this.casualties = terroristAttack.getCasualties();
        this.countryId = terroristAttack.getCountry().getCountryId();

    }

}
