package cart;

import com.example.eshop.cart.CartItem;
import com.example.eshop.product.DigitalProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartItemMilotaTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 3, 15, Integer.MAX_VALUE})
    @DisplayName("Should create CartItem with valid quantity")
    void shouldCreateCartItem(int quantity) {

        DigitalProduct product = new DigitalProduct(
                "JUnit Book",
                "Testing guide",
                new BigDecimal("100.00"),
                "https://test.cz"
        );

        CartItem item = new CartItem(product, quantity);

        assertAll(
                () -> assertEquals(product, item.getProduct()),
                () -> assertEquals(quantity, item.getQuantity()),
                () -> assertEquals(new BigDecimal("100.00").multiply(new BigDecimal(quantity)), item.getTotalPrice())
        );
    }

    @Test
    @DisplayName("Constructor should throw exception for zero quantity")
    void constructorShouldThrowForZeroQuantity() {

        DigitalProduct product = new DigitalProduct(
                "Test",
                "Desc",
                new BigDecimal("50.00"),
                "https://test.cz"
        );

        assertThrows(IllegalArgumentException.class,
                () -> new CartItem(product, 0));
    }

    @Test
    @DisplayName("Constructor should throw exception for negative quantity")
    void constructorShouldThrowForNegativeQuantity() {

        DigitalProduct product = new DigitalProduct(
                "Test",
                "Desc",
                new BigDecimal("50.00"),
                "https://test.cz"
        );

        assertThrows(IllegalArgumentException.class,
                () -> new CartItem(product, -1));
    }

    @Test
    @DisplayName("setQuantity should update quantity when valid")
    void shouldUpdateQuantity() {

        DigitalProduct product = new DigitalProduct(
                "JUnit Book",
                "Testing guide",
                new BigDecimal("100.00"),
                "https://test.cz"
        );

        CartItem item = new CartItem(product, 1);
        item.setQuantity(3);

        assertAll(
                () -> assertEquals(3, item.getQuantity()),
                () -> assertEquals(new BigDecimal("300.00"), item.getTotalPrice())
        );
    }

    @Test
    @DisplayName("setQuantity should throw exception for invalid quantity")
    void setQuantityShouldThrowForInvalidValue() {

        DigitalProduct product = new DigitalProduct(
                "Test",
                "Desc",
                new BigDecimal("50.00"),
                "https://test.cz"
        );

        CartItem item = new CartItem(product, 1);

        assertThrows(IllegalArgumentException.class,
                () -> item.setQuantity(0));
    }
}