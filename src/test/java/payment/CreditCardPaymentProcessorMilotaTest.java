package payment;

import com.example.eshop.payment.CreditCardPaymentProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardPaymentProcessorMilotaTest {

    @Test
    @DisplayName("Should return true when processing payment")
    void shouldReturnTrueWhenProcessingPayment() {
        CreditCardPaymentProcessor processor = new CreditCardPaymentProcessor();

        boolean result = processor.processPayment(new BigDecimal("100.00"));

        assertTrue(result, "Credit card payment should always return true");
    }

    @Test
    @DisplayName("Should return true for zero amount")
    void shouldReturnTrueForZeroAmount() {
        CreditCardPaymentProcessor processor = new CreditCardPaymentProcessor();

        boolean result = processor.processPayment(BigDecimal.ZERO);

        assertTrue(result);
    }

    @Test
    @DisplayName("Should return true for large amount")
    void shouldReturnTrueForLargeAmount() {
        CreditCardPaymentProcessor processor = new CreditCardPaymentProcessor();

        boolean result = processor.processPayment(new BigDecimal("999999.99"));

        assertTrue(result);
    }
}