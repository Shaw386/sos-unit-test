package com.example.eshop;

import com.example.eshop.cart.Cart;
import com.example.eshop.cart.CartItem;
import com.example.eshop.product.DigitalProduct;
import com.example.eshop.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTest {
    @Test
    @DisplayName("Should validate cart functionality")
    void validateCartFunctionality() {
        DigitalProduct product1 = new DigitalProduct("E-book", "A digital book about Java programming", new BigDecimal("19.99"), "http://example.com/download/ebook");
        int quantity = 2;

        CartItem cartItem = new CartItem(product1, quantity);
        assertAll("Verify cart item attributes",
                () -> assertEquals(product1, cartItem.getProduct(), "Product should match"),
                () -> assertEquals(quantity, cartItem.getQuantity(), "Quantity should match")
        );
    }
}
