package com.example.ea_countries_stats.domain.terroristAttack;


import com.example.ea_countries_stats.domain.country.Country;
import com.example.ea_countries_stats.domain.country.CountryResponse;
import com.example.ea_countries_stats.domain.country.CountryService;
import com.example.ea_countries_stats.utils.exceptions.NotFoundException;
import com.example.ea_countries_stats.utils.exceptions.TerroristAttackNotFound;
import com.example.ea_countries_stats.utils.response.ObjectResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("terrorist_attacks")
@Validated
public class TerroristAttackController {

    private final CountryService countryService;
    private final TerroristAttackService terroristAttackService;

    public TerroristAttackController(CountryService countryService, TerroristAttackService terroristAttackService) {
        this.countryService = countryService;
        this.terroristAttackService = terroristAttackService;
    }
    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<TerroristAttackResponse> createTerroristAttack (@RequestBody @Valid TerroristAttackRequest terroristAttackRequest) {
        TerroristAttack terroristAttack= new TerroristAttack();
        terroristAttackRequest.toTerroristAttack(terroristAttack, countryService);
        terroristAttackService.createTerroristAttack(terroristAttack);

        return ObjectResponse.of(terroristAttack, TerroristAttackResponse::new);


    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Valid
    @Transactional
    public ObjectResponse<TerroristAttackResponse> updateTerroristAttack(@PathVariable Long id, @RequestBody @Valid TerroristAttackRequest terroristAttackRequest) {
        TerroristAttack terroristAttack = terroristAttackService.getTerroristAttack(id)
                .orElseThrow(TerroristAttackNotFound::new);
        terroristAttackRequest.toTerroristAttack(terroristAttack, countryService);

        terroristAttackService.updateTerroristAttack(id, terroristAttack);

        return ObjectResponse.of(terroristAttack, TerroristAttackResponse::new);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTerroistAttack(@PathVariable Long id) {
        terroristAttackService
                .getTerroristAttack(id)
                .ifPresent(account -> {

                    terroristAttackService.deleteTerroristAttack(id);
                });
    }


}
