package com.redhat.coolstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShoppingCartItem {
    private Product product;
    private int quantity;
}
