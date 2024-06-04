package com.example.ea_countries_stats.utils.data;

import com.example.ea_countries_stats.domain.country.Country;
import com.example.ea_countries_stats.domain.country.CountryService;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DataImporter {

    private final CountryService userService;
    private final ResourceLoader resourceLoader;

    public DataImporter(CountryService countryService, ResourceLoader resourceLoader) {
        this.userService = countryService;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    @Transactional
    public void importCountries() {
        // get data from 'resources' folder
        Resource resource = resourceLoader.getResource("classpath:data/countries.csv");

        // create Countries from data
        List<Country> countryList = parseCountries(resource);

        // save to db
        userService.saveCountries(countryList);
    }

    public List<Country> parseCountries(Resource resource) {
        List<Country> userList = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(resource.getFile()));

            // skip the header row
            reader.skip(1);

            // read row by row
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String countryTxt = nextLine[0];
                String isoCode = nextLine[1];

                if (!countryTxt.isEmpty() && isoCode.length() > 2) {
                    Country newCountry = new Country();
                    newCountry.setCountryTxt(countryTxt);
                    newCountry.setIsocode(isoCode);
                    userList.add(newCountry);
                }
            }
            log.info("Loaded {} countries successfully.", userList.size());

        } catch (Exception e) {
            log.error("Error parsing CSV file", e);
        }
        return userList;
    }
}