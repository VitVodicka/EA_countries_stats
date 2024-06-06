package com.example.ea_countries_stats.domain.country;

import com.example.ea_countries_stats.domain.country.responses.CountryProbabilityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import com.example.ea_countries_stats.domain.country.responses.CountryProbabilityResponse;
import com.example.ea_countries_stats.domain.country.responses.CountryResponse;
import com.example.ea_countries_stats.utils.exceptions.DatabaseAccessException;
import com.example.ea_countries_stats.utils.exceptions.InternalServerErrorException;
import com.example.ea_countries_stats.utils.exceptions.NotFoundException;
import com.example.ea_countries_stats.utils.response.ArrayResponse;
import com.example.ea_countries_stats.utils.response.ObjectResponse;
import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

public class CountryUnitTest {
    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCountriesProbabilities() {
        // given
        List<CountryProbabilityResponse> probabilities = new ArrayList<>();
        probabilities.add(new CountryProbabilityResponse(new Country("USA", "US"), 0.5));
        probabilities.add(new CountryProbabilityResponse(new Country("Czechia", "CZ"), 0.5));

        // Mocking behavior of countryService
        when(countryService.getCountriesWithProbabilities()).thenReturn(probabilities);

        // when
        ArrayResponse<CountryProbabilityResponse> response = countryController.getCountriesProbabilities();

        // then
        assertNotNull(response);
        assertEquals(2, response.getCount());

        // Check the properties of the first item

        assertEquals("USA", response.getItems().get(0).getCountryTxt());
        assertEquals("US", response.getItems().get(0).getIsocode());
        assertEquals(0.5, response.getItems().get(0).getProbability());

        // Check the properties of the second item

        assertEquals("Czechia", response.getItems().get(1).getCountryTxt());
        assertEquals("CZ", response.getItems().get(1).getIsocode());
        assertEquals(0.5, response.getItems().get(1).getProbability());
    }


}
