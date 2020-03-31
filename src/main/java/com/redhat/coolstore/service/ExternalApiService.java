package com.redhat.coolstore.service;

import com.redhat.coolstore.model.Product;

public interface ExternalApiService {

    Product getProduct(String itemId);
}
