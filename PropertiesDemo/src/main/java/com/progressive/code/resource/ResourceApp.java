package com.progressive.code.resource;

import com.progressive.code.resource.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

/**
 * Created by abraun on 14/11/2017.
 */
@SpringBootApplication
public class ResourceApp implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceApp.class);

    @Autowired
    private CountryService countryService;

    public static void main(String[] args) {
        SpringApplication.run(ResourceApp.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        LOGGER.info("Country for ISO code CA is {}", countryService.getCountryByIsoCode("CA"));
        Map<String, String> countries = countryService.getAllCountries();
        countries.keySet().forEach(isoCode -> {
            LOGGER.info("Found ISO code {} for country {}", isoCode, countries.get(isoCode));
        });
    }
}