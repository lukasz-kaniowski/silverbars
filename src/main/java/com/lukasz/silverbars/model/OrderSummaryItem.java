package com.lukasz.silverbars.model;

import java.util.Objects;

public class OrderSummaryItem {
    private final OrderType type;
    private final QuantityInKG quantityInKG;
    private final PricePerKG pricePerKG;

    public OrderSummaryItem(OrderType type, QuantityInKG quantityInKG, PricePerKG pricePerKG) {

        this.type = type;
        this.quantityInKG = quantityInKG;
        this.pricePerKG = pricePerKG;
    }

    public OrderType getType() {
        return type;
    }

    public QuantityInKG getQuantityInKG() {
        return quantityInKG;
    }

    public PricePerKG getPricePerKG() {
        return pricePerKG;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderSummaryItem that = (OrderSummaryItem) o;
        return type == that.type &&
                Objects.equals(quantityInKG, that.quantityInKG) &&
                Objects.equals(pricePerKG, that.pricePerKG);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, quantityInKG, pricePerKG);
    }

    @Override
    public String toString() {
        return "OrderSummaryItem{" +
                "type=" + type +
                ", quantityInKG=" + quantityInKG +
                ", pricePerKG=" + pricePerKG +
                '}';
    }
}
