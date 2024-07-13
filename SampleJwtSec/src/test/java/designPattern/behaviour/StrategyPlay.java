package designPattern.behaviour;

import java.math.BigDecimal;

public class StrategyPlay {
    public static void main(String[] args) {
        //CreditCardPayment creditCardPayment = new CreditCardPayment("232435534","235","password123");
        PaypalPayment creditCardPayment = new PaypalPayment("sau","saurabh@gmail.com");
        ShoppingCart shoppingCart = new ShoppingCart(creditCardPayment);
        Items items = new Items("123", BigDecimal.valueOf(55.50));
        Items items2 = new Items("456", BigDecimal.valueOf(50.50));
        Items items3 = new Items("789", BigDecimal.valueOf(35.50));
        shoppingCart.addItems(items);
        shoppingCart.addItems(items2);
        shoppingCart.addItems(items3);
        shoppingCart.getTotal();


    }
}
