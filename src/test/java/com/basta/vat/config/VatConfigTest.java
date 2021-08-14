package com.basta.vat.config;

import com.basta.vat.service.VatLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.net.http.HttpClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VatConfigTest {

    @Test
    void configurationTest() {
        var runner = new ApplicationContextRunner().withUserConfiguration(VatConfig.class);
        runner.run(it -> {
            assertThat(it.getBean(ObjectMapper.class)).isNotNull();
            assertThat(it.getBean(HttpClient.class)).isNotNull();
            assertThat(it.getBean(VatLoader.class)).isNotNull();
        });
    }
}