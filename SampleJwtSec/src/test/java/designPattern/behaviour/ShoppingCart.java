package designPattern.behaviour;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    PaymentStrategy strategy;
    ShoppingCart(PaymentStrategy strategy){
        this.strategy = strategy;
    }
    List<Items> itemsList = new ArrayList<>();

    public void addItems(Items items){
        itemsList.add(items);
    }

    public void removeItems(Items items){
        itemsList.remove(items);
    }

    public void getTotal(){
        BigDecimal sum = BigDecimal.valueOf(0);
        for(Items item : itemsList){
            sum = sum.add(item.getPrice());
        }
        strategy.pay(sum);
    }

}
