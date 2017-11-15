package com.progressive.code.resource.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by abraun on 14/11/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryServiceImplTest {

    @Autowired
    private CountryService countryService;

    @Test
    public void testGetCountryByIsoCode() {
        String countryName = countryService.getCountryByIsoCode("CA");
        assertEquals("Canada", countryName);
    }

    @Test
    public void testGetAllCountries() {
        Map<String,String> countries = countryService.getAllCountries();
        assertNotNull(countries);
        assertEquals(4, countries.size());
        assertEquals("Canada", countries.get("CA"));
        assertEquals("Germany", countries.get("DE"));
    }
}
