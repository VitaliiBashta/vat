package com.basta.vat.service;

import com.basta.vat.exception.VatParseException;
import com.basta.vat.model.CountryVat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class Converter {

    private final ObjectMapper mapper;

    public Converter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Set<CountryVat> parseCountriesVat(String json) {

        Map<String, Map<String, Map<String, Object>>> map;
        try {
            map = mapper.readValue(json, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new VatParseException("Input data incorrect", e);

        }
        return getCountryVats(map);
    }

    private Set<CountryVat> getCountryVats(Map<String, Map<String, Map<String, Object>>> map) {
        Map<String, Map<String, Object>> rates = map.get("rates");

        return rates.values().stream().map(value -> mapper.convertValue(value, CountryVat.class)).collect(toSet());
    }
}
