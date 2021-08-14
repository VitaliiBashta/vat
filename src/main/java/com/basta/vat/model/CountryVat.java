package com.basta.vat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CountryVat {

    private String country;
    private int standardRate;

    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public CountryVat setCountry(String country) {
        this.country = country;
        return this;
    }

    public int getStandardRate() {
        return standardRate;
    }

    @JsonProperty("standard_rate")
    public CountryVat setStandardRate(int standardRate) {
        this.standardRate = standardRate;
        return this;
    }

    @Override
    public String toString() {
        return country + "=" + standardRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryVat that = (CountryVat) o;
        return country.equals(that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country);
    }
}
