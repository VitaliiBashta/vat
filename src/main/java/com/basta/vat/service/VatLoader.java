package com.basta.vat.service;

import com.basta.vat.exception.VatParseException;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class VatLoader {

    private final HttpClient httpClient;
    private final String remoteSourceUrl;

    public VatLoader(HttpClient httpClient, String remoteSourceUrl) {
        this.httpClient = httpClient;
        this.remoteSourceUrl = remoteSourceUrl;
    }

    public String loadVats() {
        HttpRequest request = buildGetRequest();
        try {
            return httpClient
                    .send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new VatParseException("Remote source error", e);
        }
    }

    private HttpRequest buildGetRequest() {
        final URI uri;
        try {
            uri = new URI(remoteSourceUrl);
        } catch (URISyntaxException e) {
            throw new VatParseException("Mis-configured source url", e);
        }
        return HttpRequest.newBuilder(uri)
                .GET()
                .build();
    }
}
