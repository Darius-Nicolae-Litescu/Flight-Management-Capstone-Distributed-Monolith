package org.darius;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Component
public class RestClient {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpServletRequest request;

    public String getToken() {
        String jwtToken = request.getHeader("Authorization");
        return jwtToken;
    }
    public HttpHeaders getRequestHeaderBearer() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", getToken());
        return headers;
    }
    public HttpHeaders getRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;

    }
    public <T> ResponseEntity<T> restExchangeBearer(String url, HttpMethod httpMethod, Class<T> classObj) {
        return restTemplate.exchange(url, httpMethod, new HttpEntity<>("body", this.getRequestHeaderBearer()), classObj);
    }

    public <T> ResponseEntity<T> restExchange(String url, HttpMethod httpMethod, Class<T> classObj) {
        return restTemplate.exchange(url, httpMethod, new HttpEntity<>("body", this.getRequestHeader()), classObj);

    }
}