package com.example.ea_countries_stats.utils.data;

import com.example.ea_countries_stats.domain.country.Country;
import com.example.ea_countries_stats.domain.country.CountryRepository;
import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttack;
import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttackRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DataImporter {

    private final CountryRepository countryRepository;
    private final TerroristAttackRepository terroristAttackRepository;
    private final ResourceLoader resourceLoader;

    public DataImporter(CountryRepository countryRepository, TerroristAttackRepository terroristAttackRepository, ResourceLoader resourceLoader) {
        this.countryRepository = countryRepository;
        this.terroristAttackRepository = terroristAttackRepository;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    @Transactional
    public void importData() {
        importCountries();
        importTerroristAttacks();
    }

    private void importCountries() {
        Resource resource = resourceLoader.getResource("classpath:data/countries.csv");
        List<Country> countryList = parseCountries(resource);
        countryRepository.saveAll(countryList);
    }

    private List<Country> parseCountries(Resource resource) {
        List<Country> countries = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(resource.getFile()))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Country country = new Country(nextLine[1], nextLine[2]);
                countries.add(country);
            }
            log.info("Loaded {} countries successfully.", countries.size());
        } catch (Exception e) {
            log.error("Error parsing countries CSV file", e);
        }
        return countries;
    }

    private void importTerroristAttacks() {
        Resource resource = resourceLoader.getResource("classpath:data/attacks.csv");
        List<TerroristAttack> attackList = parseTerroristAttacks(resource);
        terroristAttackRepository.saveAll(attackList);
    }

    private List<TerroristAttack> parseTerroristAttacks(Resource resource) {
        List<TerroristAttack> attacks = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (CSVReader reader = new CSVReader(new FileReader(resource.getFile()))) {
            reader.skip(1); // skip the header
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                attacks=settingTerroristAttack(nextLine,dateFormat,attacks);
            }
            log.info("Loaded {} terrorist attacks successfully.", attacks.size());
        } catch (Exception e) {log.error("Error parsing terrorist attacks CSV file", e);
        }return attacks;
    }

    private List<TerroristAttack> settingTerroristAttack(String[] nextLine, SimpleDateFormat dateFormat, List<TerroristAttack> attacks) throws ParseException {
        TerroristAttack attack = new TerroristAttack();
        String dateString = nextLine[1];
        if (dateString != null && !dateString.isEmpty()) {
            attack.setDate(dateFormat.parse(dateString));
        } else {
            log.warn("Empty or null date string for terrorist attack: {}", Arrays.toString(nextLine));
            // You may handle this case differently based on your requirements
            return attacks;
        }
        attack.setLocation(nextLine[2]);
        attack.setTarget(nextLine[3]);
        attack.setCasualties(Integer.parseInt(nextLine[4]));

        Long countryId = Long.parseLong(nextLine[5]);
        Country country = countryRepository.findCountryByCountryId(countryId);
        if (country != null) {
            attack.setCountry(country);
            attacks.add(attack);
        } else {
            log.warn("Country {} not found for attack on {}", nextLine[5], nextLine[1]);
            // You may handle this case differently based on your requirements
        }
        return attacks;
    }

}
