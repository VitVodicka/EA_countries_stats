package com.example.ea_countries_stats.domain.country;

import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttack;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @NotEmpty
    private String countryTxt;

    @NotNull
    private String isocode;

    /*@NotNull
    private List<TerroristAttack> terroristAttacksList = new ArrayList<>();*/

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
