package com.redhat.coolstore.service;

import com.redhat.coolstore.model.ShoppingCart;

import java.util.Map;


public interface CartService {

     Map<String, ShoppingCart> getCarts();
     ShoppingCart getCartByID(String cartId);

    ShoppingCart addToCart(String cartId, String itemId, int quantity);
}
