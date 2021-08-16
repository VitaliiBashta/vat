package com.basta.vat.service;

import com.basta.vat.model.CountryVat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EvaluatorTest {

  public static final String COUNTRY = "someCountry";
  public static final int VAT = 13;
  public static final int ANOTHER_VAT = 42;
  List<String> countryNames =
      List.of(
          "Romania",
          "Hungary",
          "United Kingdom",
          "Spain",
          "Czech Republic",
          "Finland",
          "Belgium",
          "Italy",
          "Denmark",
          "Estonia",
          "Germany",
          "Cyprus",
          "Netherlands",
          "Malta",
          "Portugal",
          "Greece",
          "Austria",
          "Latvia",
          "Luxembourg",
          "Sweden",
          "Ireland",
          "Slovakia",
          "Slovenia",
          "Poland",
          "Bulgaria",
          "France",
          "Lithuania",
          "Croatia");
  private Evaluator evaluator;

  @BeforeEach
  void setUp() {
    evaluator = new Evaluator();
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 5, 10, 100, Integer.MAX_VALUE})
  void onlyOneCountry_shouldReturnIt(int count) {
    CountryVat countryVat = createCountry(COUNTRY, 1);

    final List<String> maxVatCountries = evaluator.maxVat(Set.of(countryVat), count);
    final List<String> minVatCountries = evaluator.minVat(Set.of(countryVat), count);

    assertThat(maxVatCountries).asList().containsExactly(COUNTRY);
    assertThat(minVatCountries).asList().containsExactly(COUNTRY);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 5, 10})
  void returnsNsCountriesWithSameVat(int countriesCount) {
    Set<CountryVat> countriesWithSameVat = prepareCountriesWithSameVat(countriesCount, VAT);

    final List<String> maxVatCountries = evaluator.maxVat(countriesWithSameVat, countriesCount);
    final List<String> minVatCountries = evaluator.minVat(countriesWithSameVat, countriesCount);

    assertThat(maxVatCountries).asList().hasSize(countriesCount);
    assertThat(minVatCountries).asList().hasSize(countriesCount);
  }

  @ParameterizedTest
  @ValueSource(ints = {2, 3, 5, 10})
  void oneCountryHasHigherVat_returnsMinVatOneLess_maxVatSameCount(int countriesCount) {
    Set<CountryVat> countries = prepareCountriesWithSameVat(countriesCount, VAT);
    var countryWithAnotherVat = createCountry("TopCountry", ANOTHER_VAT);
    countries.add(countryWithAnotherVat);
    final List<String> maxVatCountries = evaluator.maxVat(countries, 2);
    final List<String> minVatCountries = evaluator.minVat(countries, 2);

    assertThat(maxVatCountries).asList().hasSize(countries.size());
    assertThat(minVatCountries).asList().hasSize(countries.size() - 1);
  }

  @ParameterizedTest
  @ValueSource(ints = {2, 3, 5, 10})
  void oneCountryHasLowerVat_returnsMaxVatOneLess_minVatSameCount(int countriesCount) {
    Set<CountryVat> countries = prepareCountriesWithSameVat(countriesCount, ANOTHER_VAT);
    var countryWithAnotherVat = createCountry("BottomCountry", VAT);
    countries.add(countryWithAnotherVat);
    final List<String> maxVatCountries = evaluator.maxVat(countries, 2);
    final List<String> minVatCountries = evaluator.minVat(countries, 2);

    assertThat(maxVatCountries).asList().hasSize(countries.size() - 1);
    assertThat(minVatCountries).asList().hasSize(countries.size());
  }

  private Set<CountryVat> prepareCountriesWithSameVat(int count, int vat) {
    return IntStream.range(0, count)
        .mapToObj(i -> createCountry(countryNames.get(i), vat))
        .collect(Collectors.toSet());
  }

  private CountryVat createCountry(String countryName, int vat) {
    var country = new CountryVat();
    country.setCountry(countryName);
    country.setStandardRate(vat);
    return country;
  }
}
