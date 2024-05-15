package com.lsyh.seukseuk.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsyh.seukseuk.domain.Holiday;
import com.lsyh.seukseuk.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiUtil {
    private final RestTemplate restTemplate;

    @Value("${godata.url}")
    private String apiUrl;
    @Value("${godata.apiKey}")
    private String apiKey;

    public ResponseEntity<String> fetchAndSaveHolidays(int year, int month) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*;q=0.9");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String monthFormatted = String.format("%02d", month);
        String finalUrl = String.format("%s?serviceKey=%s&solYear=%d&solMonth=%s&_type=json", apiUrl, apiKey, year, monthFormatted);
        URI uri = new URI(finalUrl.toString());

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        return response;
    }
}
