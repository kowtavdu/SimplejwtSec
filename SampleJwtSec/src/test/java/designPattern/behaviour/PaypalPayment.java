package designPattern.behaviour;

import java.math.BigDecimal;

public class PaypalPayment implements PaymentStrategy{
    private String username;

    public PaypalPayment(String username, String email) {
        this.username = username;
        this.email = email;
    }

    private String email;

    @Override
    public void pay(BigDecimal amt) {
        System.out.println("Payment through paypal "+amt);
    }
}
