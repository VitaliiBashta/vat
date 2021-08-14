package com.basta.vat.controller;

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

    private static final int TOPS_COUNT = 2;
    private final VatLoader vatLoader;
    private final Converter converter;
    private final Evaluator evaluator;

    public VatController(VatLoader vatLoader, Converter converter, Evaluator evaluator) {
        this.vatLoader = vatLoader;
        this.converter = converter;
        this.evaluator = evaluator;
    }

    @GetMapping("/")
    public Map<String, Map<Integer, List<String>>> calculateVat() {

        String vatsJson = vatLoader.loadVats();
        Set<CountryVat> countryVats = converter.parseCountriesVat(vatsJson);
        Map<Integer, List<String>> maxVats = evaluator.maxVat(countryVats, TOPS_COUNT);
        Map<Integer, List<String>> minVats = evaluator.minVat(countryVats, TOPS_COUNT);

        return Map.of("HighestVAt", maxVats,
                "LowestVat", minVats);
    }
}
