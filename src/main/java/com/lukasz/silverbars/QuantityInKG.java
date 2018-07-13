package com.lukasz.silverbars;

import java.util.Objects;

public class QuantityInKG {
    private double quantity;

    public QuantityInKG(double quantity) {
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public QuantityInKG add(QuantityInKG quantityInKG) {
        return new QuantityInKG(quantityInKG.getQuantity() + this.getQuantity());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuantityInKG that = (QuantityInKG) o;
        return Double.compare(that.quantity, quantity) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(quantity);
    }

    @Override
    public String toString() {
        return "QuantityInKG{" +
                "quantity=" + quantity +
                '}';
    }
}
