package com.example.ea_countries_stats.domain.terroristAttack;


import com.example.ea_countries_stats.domain.country.CountryService;
import com.example.ea_countries_stats.utils.exceptions.NotAscOrDesc;

import com.example.ea_countries_stats.utils.exceptions.NotLowerOrHigherException;
import com.example.ea_countries_stats.utils.response.ObjectResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .orElseThrow(NotLowerOrHigherException::new);
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

    @GetMapping("/casualities/averagecasulities/{min}/{max}")
    public ResponseEntity<Double> getAverageCasualitiesInRange(@PathVariable int min, @PathVariable int max) {
        double averageCasualities = terroristAttackService.calculateAverageCasualitiesInRange(min, max);
        return ResponseEntity.ok(averageCasualities);
    }

    @GetMapping("/casualities/lowerorhighercasualities/{direction}/{numberHigherOrLower}/{sortOrder}")
    public List<TerroristAttackResponse> getTerroristAttacksByCasualities(@PathVariable String direction,
                                                                          @PathVariable int numberHigherOrLower,
                                                                          @PathVariable String sortOrder) {

        if(direction.contains("lower")||(direction.contains("higher"))) {
            if(sortOrder.equals("ASC") || sortOrder.equals("DESC")) {
                return terroristAttackService.getTerroristAttacksByCasualities(direction, numberHigherOrLower, sortOrder);
            }
            throw new NotAscOrDesc();
        }
        throw new NotLowerOrHigherException();



    }

    @GetMapping("/casualities/weighted_average")
    public ResponseEntity<Double> getWeightedAverageCasualities(@RequestParam List<Double> weights) {
        double weightedAverage = terroristAttackService.calculateWeightedAverageCasualities(weights);

        return ResponseEntity.ok(weightedAverage);
    }

    @GetMapping("/casualities/standarddeviationfromvalue/{value}")
    public ResponseEntity<Double> getStandardDeviationFromValue(@PathVariable double value) {
        double standardDeviation = terroristAttackService.calculateStandardDeviationFromValue(value);

        return ResponseEntity.ok(standardDeviation);
    }


}
