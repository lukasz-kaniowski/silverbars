package com.lukasz.silverbars;

import java.math.BigDecimal;
import java.util.Objects;

public class PricePerKG {
    private BigDecimal price;

    public PricePerKG(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricePerKG that = (PricePerKG) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(price);
    }

    @Override
    public String toString() {
        return "PricePerKG{" +
                "price=" + price +
                '}';
    }
}
