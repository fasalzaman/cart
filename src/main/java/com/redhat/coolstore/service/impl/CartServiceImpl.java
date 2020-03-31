package com.redhat.coolstore.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.coolstore.Kafka.KafkaCartProducer;
import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.model.ShoppingCartItem;
import com.redhat.coolstore.service.CartService;
import com.redhat.coolstore.service.ExternalApiService;
import com.redhat.coolstore.utils.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class CartServiceImpl implements CartService {

    private final static Map<String, ShoppingCart> carts = new ConcurrentHashMap<>();

    static {
        carts.put("99999", Generator.generateShoppingCart("99999"));
    }

    @Override
    public @ResponseBody Map<String, ShoppingCart> getCarts() {
        return carts;
    }

    @Autowired
    private ExternalApiService externalApiService;

    @Autowired
    KafkaCartProducer kafkaCartProducer;

    @Override
    public ShoppingCart getCartByID(String cartId) {
        return getCart(cartId);
    }

    @Override
    public ShoppingCart addToCart(String cartId, String itemId, int quantity)  throws  RuntimeException{

        ShoppingCart cart = getCart(cartId);

        boolean productAlreadyInCart = cart.getShoppingCartItemList().stream()
                .anyMatch(i -> i.getProduct().getItemId().equals(itemId));
        if(productAlreadyInCart) {
            cart.getShoppingCartItemList().forEach(item -> {
                if (item.getProduct().getItemId().equals(itemId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                }
            });
            return cart;
        }else{
            Product product = getFakeProduct();
                    //externalApiService.getProduct(itemId);
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getShoppingCartItemList().add(newItem);
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                kafkaCartProducer.send(objectMapper.writeValueAsString(newItem));
            }catch (Exception ex){
                throw  new RuntimeException("Kafka error");
            }
            return cart;
        }

    }

    private Product getFakeProduct() {
        Product product = new Product();
        product.setItemId(Math.random()+"");
        product.setName("Apple");
        product.setPrice(123133);
        product.setDesc("Apple product");
        return  product;

    }

    private static ShoppingCart getCart(String cartId) {
        if(carts.containsKey(cartId)) {
            return carts.get(cartId);
        } else {
            ShoppingCart cart = new ShoppingCart();
            cart.setCartId(cartId);
            carts.put(cartId,cart);
            return cart;
        }

    }




}
