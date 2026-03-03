package order;

import com.example.eshop.cart.Cart;
import com.example.eshop.order.Order;
import com.example.eshop.order.OrderService;
import com.example.eshop.order.OrderStatus;
import com.example.eshop.payment.PaymentProcessor;
import com.example.eshop.product.DigitalProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderMilotaTest {
    // Fake PaymentProcessor (bez Mockito)
    static class FakePaymentProcessor implements PaymentProcessor {
        private final boolean result;
        private BigDecimal processedAmount;

        FakePaymentProcessor(boolean result) {
            this.result = result;
        }

        @Override
        public boolean processPayment(BigDecimal amount) {
            this.processedAmount = amount;
            return result;
        }

        public BigDecimal getProcessedAmount() {
            return processedAmount;
        }
    }

    private Cart createCart(BigDecimal price, int quantity) {
        DigitalProduct product = new DigitalProduct(
                "JUnit Book",
                "Testing guide",
                price,
                "https://test.cz"
        );

        Cart cart = new Cart();
        cart.addItem(product, quantity);  // ✅ SPRÁVNĚ
        return cart;
    }

    @Test
    @DisplayName("Should throw exception when cart is empty")
    void shouldThrowWhenCartEmpty() {
        Cart cart = new Cart();
        OrderService service = new OrderService(new FakePaymentProcessor(true));

        assertThrows(IllegalStateException.class,
                () -> service.placeOrder(cart));
    }

    @Test
    @DisplayName("Should set order status to PAID when payment succeeds")
    void shouldSetPaidWhenPaymentSucceeds() {
        Cart cart = createCart(new BigDecimal("100.00"), 2); // total = 200.00
        FakePaymentProcessor processor = new FakePaymentProcessor(true);
        OrderService service = new OrderService(processor);

        Order order = service.placeOrder(cart);

        assertAll(
                () -> assertEquals(OrderStatus.PAID, order.getStatus()),
                () -> assertEquals(new BigDecimal("200.00"), processor.getProcessedAmount()),
                () -> assertTrue(cart.getItems().isEmpty())
        );
    }

    @Test
    @DisplayName("Should set order status to CANCELLED when payment fails")
    void shouldCancelWhenPaymentFails() {
        Cart cart = createCart(new BigDecimal("50.00"), 3); // total = 150.00
        FakePaymentProcessor processor = new FakePaymentProcessor(false);
        OrderService service = new OrderService(processor);

        Order order = service.placeOrder(cart);

        assertAll(
                () -> assertEquals(OrderStatus.CANCELLED, order.getStatus()),
                () -> assertEquals(new BigDecimal("150.00"), processor.getProcessedAmount()),
                () -> assertTrue(cart.getItems().isEmpty())
        );
    }
}
