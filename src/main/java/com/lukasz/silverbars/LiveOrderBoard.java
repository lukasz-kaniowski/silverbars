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
                .sorted(this::byPriceAndType)
                .collect(toList())
        );
    }

    public int byPriceAndType(OrderSummaryItem a, OrderSummaryItem b) {
        if (a.getType() != b.getType()) {
            return a.getType() == OrderType.SELL ? 1 : -1;
        }

        if (a.getType() == OrderType.BUY) {
            return b.getPricePerKG().getPrice().compareTo(a.getPricePerKG().getPrice());
        } else {
            return a.getPricePerKG().getPrice().compareTo(b.getPricePerKG().getPrice());
        }


    }

    private QuantityInKG totalPrice(List<QuantityInKG> quantities) {
        return quantities.stream().reduce(new QuantityInKG(0), QuantityInKG::add);
    }

    public void register(Order order) {
        orders.add(order);
    }

    public boolean cancel(OrderId orderId) {
        return orders.removeIf(it -> it.getOrderId().equals(orderId));
    }
}
