package com.basta.vat.service;

import com.basta.vat.model.CountryVat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Service
public class Evaluator {

  /** Return list of countries with min VAT. */
  public List<String> minVat(Set<CountryVat> countries, int count) {
    final List<List<String>> countriesGroupedByVatAsc = groupByVat(countries, count, true);
    return limitCountriesCount(countriesGroupedByVatAsc, count);
  }

  /** Return list of countries with max VAT. */
  public List<String> maxVat(Set<CountryVat> countries, int count) {
    final List<List<String>> countriesGroupedByVatDesc = groupByVat(countries, count, false);
    return limitCountriesCount(countriesGroupedByVatDesc, count);
  }

  private List<List<String>> groupByVat(Set<CountryVat> countries, int count, boolean ascending) {
    Comparator<Map.Entry<Integer, List<String>>> comparator =
        ascending ? comparingByKey() : comparingByKey(reverseOrder());
    return groupedByStandardVat(countries).entrySet().stream()
        .sorted(comparator)
        .limit(count)
        .map(Map.Entry::getValue)
        .collect(toList());
  }

  private Map<Integer, List<String>> groupedByStandardVat(Collection<CountryVat> countries) {
    return countries.stream()
        .collect(
            groupingBy(
                CountryVat::getStandardRate,
                HashMap::new,
                mapping(CountryVat::getCountry, toList())));
  }

  private List<String> limitCountriesCount(List<List<String>> countriesSortedByVat, int count) {
    List<String> countryNames = new ArrayList<>();
    int currentCount = 0;
    for (List<String> countriesWithSameVat : countriesSortedByVat) {
      if (currentCount >= count) {
        break;
      }
      for (String countryName : countriesWithSameVat) {
        countryNames.add(countryName);
        currentCount++;
      }
    }
    return countryNames;
  }
}
