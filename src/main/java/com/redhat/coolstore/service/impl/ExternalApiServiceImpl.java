package com.redhat.coolstore.service.impl;

import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {

    final static String catalogeUrl = "/services/product/";
    final static String port = "8080";
    final static String host ="localhost";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Product getProduct(String itemId) {
        final ResponseEntity<Product> clResponse =
                restTemplate.exchange(
                        catalogeUrl+itemId,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        Product.class);
        if(HttpStatus.OK.equals(clResponse.getStatusCode()) && Objects.nonNull(clResponse.getBody()))
            return clResponse.getBody();
        else
            throw new RuntimeException("No product found.");
    }
}
