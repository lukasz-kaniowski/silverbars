package com.lukasz.silverbars;

import com.lukasz.silverbars.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class LiveOrderBoard {

    private final List<Order> orders = new ArrayList<>();

    private final OrderSummaryItemFactory orderSummaryItemFactory = new OrderSummaryItemFactory();


    public OrderSummary summary() {
        return new OrderSummary(orders.stream()
                .collect(groupingBy(OrderGrouping::byTypeAndPrice, mapping(Order::getQuantityInKG, toList())))
                .entrySet().stream()
                .map(this::toOrderSummaryItem)
                .sorted(OrderSorter::byPriceAndType)
                .collect(toList())
        );
    }

    public void register(Order order) {
        orders.add(order);
    }

    public boolean cancel(OrderId orderId) {
        return orders.removeIf(it -> it.getOrderId().equals(orderId));
    }

    private OrderSummaryItem toOrderSummaryItem(Map.Entry<OrderGrouping, List<QuantityInKG>> it) {
        return orderSummaryItemFactory.createWithTotalQuantity(
                it.getKey().getType(),
                it.getKey().getPricePerKG(),
                it.getValue()
        );

    }
}
