package com.example.ea_countries_stats.domain.terroristAttack;


import com.example.ea_countries_stats.domain.country.CountryService;
import com.example.ea_countries_stats.utils.exceptions.*;

import com.example.ea_countries_stats.utils.response.ObjectResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
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
        try {
            TerroristAttack terroristAttack = new TerroristAttack();
            terroristAttackRequest.toTerroristAttack(terroristAttack, countryService);
            terroristAttackService.createTerroristAttack(terroristAttack);

            return ObjectResponse.of(terroristAttack, TerroristAttackResponse::new);
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error occurred while accessing the database", e);
        } catch (RuntimeException e) {
            throw new InternalServerErrorException("An unexpected error occurred", e);
        }


    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Valid
    @Transactional
    public ObjectResponse<TerroristAttackResponse> updateTerroristAttack(@PathVariable Long id, @RequestBody @Valid TerroristAttackRequest terroristAttackRequest) {
        try {
            TerroristAttack terroristAttack = terroristAttackService.getTerroristAttack(id)
                    .orElseThrow(NotLowerOrHigherException::new);
            terroristAttackRequest.toTerroristAttack(terroristAttack, countryService);

            terroristAttackService.updateTerroristAttack(id, terroristAttack);

            return ObjectResponse.of(terroristAttack, TerroristAttackResponse::new);
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error occurred while accessing the database", e);
        } catch (NotFoundException e) {
            throw e; // Rethrow NotFoundException for custom handling
        } catch (RuntimeException e) {
            throw new InternalServerErrorException("An unexpected error occurred", e);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTerroistAttack(@PathVariable Long id) {
        try {
            if (!terroristAttackService.getTerroristAttack(id).isPresent()) {
                throw new NotFoundException("TerroristAtack with ID " + id + " not found");
            }

            terroristAttackService.deleteTerroristAttack(id);
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error occurred while accessing the database", e);
        } catch (RuntimeException e) {
            throw new InternalServerErrorException("An unexpected error occurred", e);
        }
    }

    @GetMapping("/casualities/averagecasulities/{min}/{max}")
    public ResponseEntity<Double> getAverageCasualitiesInRange(@PathVariable int min, @PathVariable int max) {
        try {
            double averageCasualities = terroristAttackService.calculateAverageCasualitiesInRange(min, max);
            return ResponseEntity.ok(averageCasualities);
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error occurred while accessing the database", e);
        } catch (RuntimeException e) {
            throw new InternalServerErrorException("An unexpected error occurred", e);
        }
    }

    @GetMapping("/casualities/lowerorhighercasualities/{direction}/{numberHigherOrLower}/{sortOrder}")
    public List<TerroristAttackResponse> getTerroristAttacksByCasualities(@PathVariable String direction,
                                                                          @PathVariable int numberHigherOrLower,
                                                                          @PathVariable String sortOrder) {

        try {
            if (direction.equals("lower") || direction.equals("higher")) {
                if (sortOrder.equals("ASC") || sortOrder.equals("DESC")) {
                    return terroristAttackService.getTerroristAttacksByCasualities(direction, numberHigherOrLower, sortOrder);
                }
                throw new NotAscOrDesc();
            }
            throw new NotLowerOrHigherException();
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error occurred while accessing the database", e);

        } catch (RuntimeException e) {
            throw new InternalServerErrorException("An unexpected error occurred", e);
        }



    }

    @GetMapping("/casualities/weighted_average")
    public ResponseEntity<Double> getWeightedAverageCasualities(@RequestParam List<Double> weights) {
        try {
        double weightedAverage = terroristAttackService.calculateWeightedAverageCasualities(weights);

        return ResponseEntity.ok(weightedAverage);
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error occurred while accessing the database", e);
        } catch (RuntimeException e) {
            throw new InternalServerErrorException("An unexpected error occurred", e);
        }
    }

    @GetMapping("/casualities/standarddeviationfromvalue/{value}")
    public ResponseEntity<Double> getStandardDeviationFromValue(@PathVariable double value) {
        try {
            double standardDeviation = terroristAttackService.calculateStandardDeviationFromValue(value);
            return ResponseEntity.ok(standardDeviation);
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error occurred while accessing the database", e);
        } catch (RuntimeException e) {
            throw new InternalServerErrorException("An unexpected error occurred", e);
        }
    }


}
