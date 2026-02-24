package Product;

import com.example.eshop.product.PhysicalProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhysicalProductTest {
    @Test
    @DisplayName("Should create valid product with all attributes")
    void createNewPhysicalProduct() {
        String name = "E-Book: Learn 2025";
        String description = "Best guide for JUnit 5";
        BigDecimal price = new BigDecimal("49.90");
        Double weight = 0.5;
        BigDecimal shippingCost = new BigDecimal("5.00");

        PhysicalProduct product = new PhysicalProduct(name, description, price, weight, shippingCost);

        assertAll("Verify all product attributes",
                () -> assertEquals(name, product.getName(), "Name should match"),
                () -> assertEquals(description, product.getDescription(), "Description should match"),
                () -> assertEquals(price, product.getPrice(), "Price should match"),
                () -> assertEquals(weight, product.getWeight(), "Weight should match"),
                () -> assertEquals(shippingCost, product.getShippingCost(), "Shipping cost should match")
        );
    }
}
