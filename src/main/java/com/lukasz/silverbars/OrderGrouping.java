package com.lukasz.silverbars;

import java.util.Objects;

public class OrderGrouping {

    private final OrderType type;
    private final PricePerKG pricePerKG;

    private OrderGrouping(OrderType type, PricePerKG pricePerKG) {
        this.type = type;
        this.pricePerKG = pricePerKG;
    }

    public static OrderGrouping byTypeAndPrice(Order order) {
        return new OrderGrouping(order.getType(), order.getPricePerKG());
    }

    public OrderType getType() {
        return type;
    }

    public PricePerKG getPricePerKG() {
        return pricePerKG;
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, pricePerKG);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderGrouping that = (OrderGrouping) o;
        return type == that.type &&
                Objects.equals(pricePerKG, that.pricePerKG);
    }

    @Override
    public String toString() {
        return "OrderGrouping{" +
                "type=" + type +
                ", pricePerKG=" + pricePerKG +
                '}';
    }
}
