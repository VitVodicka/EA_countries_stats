package com.example.ea_countries_stats.domain.country;

import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttack;
import jakarta.persistence.*;
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
    public Country(String countryTxt, String isocode) {
        this.countryTxt = countryTxt;
        this.isocode = isocode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @NotEmpty
    @Column(name = "country_txt")
    private String countryTxt;

    @NotNull
    @Column(name = "iso_code")
    private String isocode;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TerroristAttack> terroristAttacksList = new ArrayList<>();

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
