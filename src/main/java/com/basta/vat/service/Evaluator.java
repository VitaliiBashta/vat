package com.basta.vat.service;

import com.basta.vat.model.CountryVat;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class Evaluator {

    /**
     * Return map of VAT value, list of countries with that VAT.
     */
    public Map<Integer, List<String>> minVat(Set<CountryVat> countries, int count) {
        return groupedByStandardVat(countries).entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .limit(count)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, List<String>> maxVat(Set<CountryVat> countries, int count) {
        return groupedByStandardVat(countries).entrySet().stream()
                .sorted(Map.Entry.comparingByKey(reverseOrder()))
                .limit(count)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    private Map<Integer, List<String>> groupedByStandardVat(Collection<CountryVat> countries) {
        return countries.stream()
                .collect(groupingBy(CountryVat::getStandardRate, HashMap::new, mapping(CountryVat::getCountry, toList())));
    }
}
