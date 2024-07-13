package designPattern.behaviour;

import java.math.BigDecimal;

public interface PaymentStrategy {
    void pay(BigDecimal amt);
}
