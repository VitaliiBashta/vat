package com.basta.vat.config;

import com.basta.vat.service.VatLoader;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class VatConfig {

    @Value("${remote.vat.source.url}")
    private String remoteSourceUrl;

    @Value("${vat.top.count}")
    private int topCount;

    @Value("${vat.bottom.count}")
    private int bottomCount;

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public HttpClient client() {
        return HttpClient
                .newBuilder()
                .build();
    }

    @Bean
    public VatLoader vatLoader(HttpClient client) {
        return new VatLoader(client, remoteSourceUrl);
    }


    public int getTopCount() {
        return topCount;
    }

    public int getBottomCount() {
        return bottomCount;
    }
}
