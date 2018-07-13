package com.lukasz.silverbars;


import java.util.List;
import java.util.Objects;

public class OrderSummary {

    private List<OrderSummaryItem> buyOrders;

    public OrderSummary(List<OrderSummaryItem> buyOrders) {
        this.buyOrders = buyOrders;
    }

    public List<OrderSummaryItem> getBuyOrders() {
        return buyOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderSummary that = (OrderSummary) o;
        return Objects.equals(buyOrders, that.buyOrders);
    }

    @Override
    public int hashCode() {

        return Objects.hash(buyOrders);
    }

    @Override
    public String toString() {
        return "OrderSummary{" +
                "buyOrders=" + buyOrders +
                '}';
    }
}
