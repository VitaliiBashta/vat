package com.basta.vat.controller;

import com.basta.vat.config.VatConfig;
import com.basta.vat.model.CountryVat;
import com.basta.vat.service.Converter;
import com.basta.vat.service.Evaluator;
import com.basta.vat.service.VatLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/vat")
public class VatController {

  private final VatLoader vatLoader;
  private final Converter converter;
  private final Evaluator evaluator;
  private final VatConfig vatConfig;

  public VatController(VatLoader vatLoader, Converter converter, Evaluator evaluator, VatConfig vatConfig) {
    this.vatLoader = vatLoader;
    this.converter = converter;
    this.evaluator = evaluator;
    this.vatConfig = vatConfig;
  }

  @GetMapping("/")
  public Map<String, List<String>> calculateVat() {

    String vatsJson = vatLoader.loadVats();
    Set<CountryVat> countryVats = converter.parseCountriesVat(vatsJson);
    List<String> maxVats = evaluator.maxVat(countryVats, vatConfig.getTopCount());
    List<String> minVats = evaluator.minVat(countryVats, vatConfig.getBottomCount());

    return Map.of(
        "Countries with highest standard VAT",
        maxVats,
        "Countries with smallest standard VAT",
        minVats);
  }
}
