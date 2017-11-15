package com.progressive.code.resource.service;

import java.util.Map;

/**
 * Created by abraun on 14/11/2017.
 */
public interface CountryService {

    String getCountryByIsoCode(String isoCode);
    Map<String,String> getAllCountries();

}