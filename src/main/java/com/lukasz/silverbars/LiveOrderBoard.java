package com.lukasz.silverbars;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

public class LiveOrderBoard {

    private final List<Order> orders = new ArrayList<>();

    public OrderSummary summary() {
        return new OrderSummary(orders.stream()
                .collect(groupingBy(OrderGrouping::byTypeAndPrice, mapping(Order::getQuantityInKG, toList())))
                .entrySet().stream()
                .map(it -> new OrderSummaryItem(it.getKey().getType(), totalPrice(it.getValue()), it.getKey().getPricePerKG()))
                .sorted((a, b) -> b.getPricePerKG().getPrice().compareTo(a.getPricePerKG().getPrice()))
                .collect(toList())
        );
    }

    private QuantityInKG totalPrice(List<QuantityInKG> quantities) {
        return quantities.stream().reduce(new QuantityInKG(0), QuantityInKG::add);
    }

    public void register(Order order) {
        orders.add(order);
    }
}
