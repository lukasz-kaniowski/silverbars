package com.lukasz.silverbars;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class LiveOrderBoard {

    private final List<Order> orders = new ArrayList<>();

    public OrderSummary summary() {
        return new OrderSummary(orders.stream()
                .collect(Collectors.groupingBy(Order::getPricePerKG, Collectors.mapping(Order::getQuantityInKG, toList())))
                .entrySet().stream()
                .map(it -> new OrderSummaryItem(OrderType.BUY, it.getValue().stream().reduce(new QuantityInKG(0), QuantityInKG::add), it.getKey()))
                .sorted((a, b) -> b.getPricePerKG().getPrice().compareTo(a.getPricePerKG().getPrice()))
                .collect(toList())
        );
    }

    public void register(Order order) {
        orders.add(order);
    }
}
