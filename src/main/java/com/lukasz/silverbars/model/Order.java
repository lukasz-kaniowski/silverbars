package com.lukasz.silverbars.model;

import java.util.Objects;

public class Order {

    private final UserId userId;
    private final QuantityInKG quantityInKG;
    private final PricePerKG pricePerKG;
    private final OrderType type;
    private final OrderId orderId;

    private Order(UserId userId, QuantityInKG quantityInKG, PricePerKG pricePerKG, OrderType type, OrderId orderId) {
        this.userId = userId;
        this.quantityInKG = quantityInKG;
        this.pricePerKG = pricePerKG;
        this.type = type;
        this.orderId = orderId;
    }

    public static Order buyOrder(UserId userId, QuantityInKG quantityInKG, PricePerKG pricePerKG) {
        return new Order(userId, quantityInKG, pricePerKG, OrderType.BUY, new OrderId());
    }

    public static Order sellOrder(UserId userId, QuantityInKG quantityInKG, PricePerKG pricePerKG) {
        return new Order(userId, quantityInKG, pricePerKG, OrderType.SELL, new OrderId());
    }

    public UserId getUserId() {
        return userId;
    }

    public QuantityInKG getQuantityInKG() {
        return quantityInKG;
    }

    public PricePerKG getPricePerKG() {
        return pricePerKG;
    }

    public OrderType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(userId, order.userId) &&
                Objects.equals(quantityInKG, order.quantityInKG) &&
                Objects.equals(pricePerKG, order.pricePerKG) &&
                type == order.type &&
                Objects.equals(orderId, order.orderId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", quantityInKG=" + quantityInKG +
                ", pricePerKG=" + pricePerKG +
                ", type=" + type +
                ", orderId=" + orderId +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, quantityInKG, pricePerKG, type, orderId);
    }

    public OrderId getOrderId() {
        return orderId;
    }
}
