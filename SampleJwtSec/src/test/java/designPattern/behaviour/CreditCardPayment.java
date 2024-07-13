package designPattern.behaviour;

import java.math.BigDecimal;

public class CreditCardPayment implements PaymentStrategy{
    public CreditCardPayment(String cardNumber, String cvv, String password) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.password = password;
    }

    private String cardNumber;
    private String cvv;
    private String password;

    @Override
    public void pay(BigDecimal amt) {

        System.out.println("payment via credit card "+amt);
    }
}
