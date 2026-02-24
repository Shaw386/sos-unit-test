package Product;

import com.example.eshop.product.DigitalProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DigitalProductTest {
    @Test
    @DisplayName("Should create valid product with all attributes")
    void createNewDigitalProduct() {
        String name = "E-Book: Learn 2025";
        String description = "Best guide for JUnit 5";
        BigDecimal price = new BigDecimal("49.90");
        String url = "https://seznam.cz";

        DigitalProduct product = new DigitalProduct(name, description, price, url);

        assertAll("Verify all product attributes",
                () -> assertEquals(name, product.getName(), "Name should match"),
                () -> assertEquals(description, product.getDescription(), "Description should match"),
                () -> assertEquals(price, product.getPrice(), "Price should match"),
                () -> assertEquals(url, product.getDownloadUrl(), "Download URL should match")
        );
    }
}
