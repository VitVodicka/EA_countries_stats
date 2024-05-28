package com.example.ea_countries_stats.domain.terroristAttack;


import com.example.ea_countries_stats.domain.country.CountryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("terrorist_attacks")
@Validated
public class TerroristAttackController {

    private final CountryService countryService;

    public TerroristAttackController(CountryService countryService) {
        this.countryService = countryService;
    }
}
