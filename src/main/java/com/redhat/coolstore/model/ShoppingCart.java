package com.redhat.coolstore.model;


import lombok.*;
import org.apache.commons.math3.util.Precision;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = -1108043957592113528L;

	private List<ShoppingCartItem> shoppingCartItemList = new ArrayList<>();

	private String cartId;

	private double shippingTotal;

	private double cartItemPromoSavings;

    private double shippingPromoSavings;

	public void addShoppingCartItem(ShoppingCartItem sci) {
		if ( sci != null ) {
			shoppingCartItemList.add(sci);
		}
	}

    public double getCartItemTotal() {
		return Precision.round(this.getShoppingCartItemList().stream().mapToDouble(i -> i.getQuantity()*i.getProduct().getPrice()).sum(),2, BigDecimal.ROUND_HALF_UP);
	}


    public double getCartTotal() {
		return Precision.round(this.getCartItemTotal()+this.getShippingTotal()-Math.abs(this.getShippingPromoSavings())-Math.abs(this.getCartItemPromoSavings()),2, BigDecimal.ROUND_HALF_UP);
	}

    public void clear() {
        shoppingCartItemList = new ArrayList<>();
        shippingTotal = 0;
        shippingPromoSavings = 0;
    }
}
