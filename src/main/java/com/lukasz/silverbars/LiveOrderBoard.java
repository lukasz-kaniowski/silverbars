package com.lukasz.silverbars;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LiveOrderBoard {

    private final List<Order> orders = new ArrayList<>();

    public OrderSummary summary() {
        return new OrderSummary(orders.stream()
                .map(it -> new OrderSummaryItem(OrderType.BUY, it.getQuantityInKG(), it.getPricePerKG()))
                .sorted((a, b) -> b.getPricePerKG().getPrice().compareTo(a.getPricePerKG().getPrice()))
                .collect(Collectors.toList())
        );
    }

    public void register(Order order) {
        orders.add(order);
    }
}
