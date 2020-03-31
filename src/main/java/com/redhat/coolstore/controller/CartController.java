package com.redhat.coolstore.controller;

import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/services/cart")
public class CartController {


    @Autowired
    private CartService cartService;

    @GetMapping(value = "")
    public @ResponseBody
    Map<String, ShoppingCart> getCarts(){
        return cartService.getCarts();

    }

    @GetMapping(value = "/{cartId}")
    public @ResponseBody
    ShoppingCart getCartByID(@PathVariable("cartId") String cartId){
        return cartService.getCartByID(cartId);

    }

    @PostMapping(value = "/{cartId}/{itemId}/{quantity}")
    public @ResponseBody
    ShoppingCart addToCart(@PathVariable("cartId") String cartId,
                           @PathVariable("itemId") String itemId,
                           @PathVariable("quantity") int quantity){
        return cartService.addToCart(cartId, itemId, quantity);

    }
}
