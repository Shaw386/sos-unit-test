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

class OrderServiceMilotaTest {

    static class FakePaymentProcessor implements PaymentProcessor {
        private final boolean shouldSucceed;
        private BigDecimal lastAmount;

        FakePaymentProcessor(boolean shouldSucceed) {
            this.shouldSucceed = shouldSucceed;
        }

        @Override
        public boolean processPayment(BigDecimal amount) {
            this.lastAmount = amount;
            return shouldSucceed;
        }

        BigDecimal getLastAmount() {
            return lastAmount;
        }
    }

    private static Cart cartWithOneDigitalProduct(BigDecimal price, int qty) {
        DigitalProduct product = new DigitalProduct(
                "JUnit Book",
                "Testing guide",
                price,
                "https://test.cz"
        );

        Cart cart = new Cart();
        cart.addItem(product, qty); // ✅ podle vaší Cart implementace
        return cart;
    }

    @Test
    @DisplayName("placeOrder should throw when cart is empty")
    void placeOrderShouldThrowWhenCartIsEmpty() {
        Cart cart = new Cart();
        OrderService service = new OrderService(new FakePaymentProcessor(true));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> service.placeOrder(cart));
        assertEquals("Cannot place an order with an empty cart", ex.getMessage());
    }

    @Test
    @DisplayName("placeOrder should set PAID when payment succeeds and clear cart")
    void placeOrderShouldSetPaidAndClearCartWhenPaymentSucceeds() {
        Cart cart = cartWithOneDigitalProduct(new BigDecimal("100.00"), 2); // total 200.00
        FakePaymentProcessor processor = new FakePaymentProcessor(true);
        OrderService service = new OrderService(processor);

        Order order = service.placeOrder(cart);

        assertAll(
                () -> assertEquals(OrderStatus.PAID, order.getStatus(), "Order should be PAID"),
                () -> assertEquals(new BigDecimal("200.00"), processor.getLastAmount(), "Payment should be processed for total amount"),
                () -> assertTrue(cart.getItems().isEmpty(), "Cart should be cleared"),
                () -> assertEquals(1, order.getItems().size(), "Order should keep items even after cart is cleared")
        );
    }

    @Test
    @DisplayName("placeOrder should set CANCELLED when payment fails and clear cart")
    void placeOrderShouldSetCancelledAndClearCartWhenPaymentFails() {
        Cart cart = cartWithOneDigitalProduct(new BigDecimal("50.00"), 3); // total 150.00
        FakePaymentProcessor processor = new FakePaymentProcessor(false);
        OrderService service = new OrderService(processor);

        Order order = service.placeOrder(cart);

        assertAll(
                () -> assertEquals(OrderStatus.CANCELLED, order.getStatus(), "Order should be CANCELLED"),
                () -> assertEquals(new BigDecimal("150.00"), processor.getLastAmount(), "Payment should be attempted for total amount"),
                () -> assertTrue(cart.getItems().isEmpty(), "Cart should be cleared"),
                () -> assertEquals(1, order.getItems().size(), "Order should keep items even after cart is cleared")
        );
    }
}