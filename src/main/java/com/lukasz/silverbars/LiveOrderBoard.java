package com.lukasz.silverbars;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LiveOrderBoard {

    private final List<Order> orders = new ArrayList<>();

    public OrderSummary summary() {
        return new OrderSummary(orders.stream()
                .map(it -> new OrderSummaryItem(OrderType.BUY, it.getQuantityInKG(), it.getPricePerKG()))
                .collect(Collectors.toList())
        );
    }

    public void register(Order order) {
        orders.add(order);
    }
}
