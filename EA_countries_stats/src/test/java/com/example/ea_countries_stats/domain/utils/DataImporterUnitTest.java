package com.example.ea_countries_stats.domain.utils;

import com.example.ea_countries_stats.domain.country.Country;
import com.example.ea_countries_stats.domain.country.CountryRepository;
import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttack;
import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttackRepository;
import com.example.ea_countries_stats.utils.data.DataImporter;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DataImporterUnitTest {
    @Mock
    private CountryRepository countryRepository;

    @Mock
    private TerroristAttackRepository terroristAttackRepository;

    @Mock
    private ResourceLoader resourceLoader;

    @InjectMocks
    private DataImporter dataImporter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testImportData() throws IOException {
        Resource countriesResource = new DefaultResourceLoader().getResource("classpath:data/countries.csv");
        Resource attacksResource = new DefaultResourceLoader().getResource("classpath:data/attacks.csv");

        when(resourceLoader.getResource("classpath:data/countries.csv")).thenReturn(countriesResource);
        when(resourceLoader.getResource("classpath:data/attacks.csv")).thenReturn(attacksResource);
        List<Country> mockCountries = List.of(new Country("Afghanistan", "AFG") );
        when(countryRepository.saveAll(any())).thenReturn(mockCountries);

        TerroristAttack attack = new TerroristAttack();
        attack.setDate(new Date(1970 - 1900, 6 - 1, 2)); // Datum: 1970-07-02
        attack.setLocation("Cairo");
        attack.setTarget("Government Building");
        attack.setCasualties(15);

        List<TerroristAttack> mockAttacks = List.of(attack);
        when(terroristAttackRepository.saveAll(any())).thenReturn(mockAttacks);
        dataImporter.importData();
        verify(countryRepository, times(1)).saveAll(any());
        verify(terroristAttackRepository, times(1)).saveAll(any());
    }
}
