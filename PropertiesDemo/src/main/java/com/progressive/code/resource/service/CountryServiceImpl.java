package com.progressive.code.resource.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by abraun on 14/11/2017.
 */
@Service
public class CountryServiceImpl implements CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);

    private static final String COUNTRY_DATA = "data/countryCodes.properties";

    private final Map<String, String> countries = new HashMap<>();

    public CountryServiceImpl() {
        readCountries();
        LOGGER.info("Loaded {} countries", countries.keySet().size());
    }

    @Override
    public String getCountryByIsoCode(String isoCode) {
        return countries.get(isoCode);
    }

    @Override
    public Map<String, String> getAllCountries() {
        return countries;
    }
    
    /**
     * This method reads the country codes properties file from the resource 
     * folder (ClassPath) and stores the result in a HashMap.
     */
    private void readCountries() {
        try {
        	
        	//Instantiate a new Properties object
            Properties props = new Properties();
            
            //Create a ClassPathResource, based on the given location within the resource folder
            ClassPathResource res = new ClassPathResource(COUNTRY_DATA);
            
            //Load the properties using InputStream provided by the ClassPathResource
            props.load(res.getInputStream());
            
            //Map the properties to the HashMap
            props.stringPropertyNames().forEach(
            		isoName -> countries.put(isoName, props.get(isoName).toString())
            );
            
        } catch (IOException e) {
        	//For this example just log the exception
            LOGGER.error("Exception occurred while trying to read {}", COUNTRY_DATA, e);
        }
    }

}