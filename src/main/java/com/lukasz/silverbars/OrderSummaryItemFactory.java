package com.lukasz.silverbars;

import com.lukasz.silverbars.model.OrderSummaryItem;
import com.lukasz.silverbars.model.OrderType;
import com.lukasz.silverbars.model.PricePerKG;
import com.lukasz.silverbars.model.QuantityInKG;

import java.util.List;

public class OrderSummaryItemFactory {

    public OrderSummaryItem createWithTotalQuantity(OrderType type, PricePerKG pricePerKG, List<QuantityInKG> quantities) {
        return new OrderSummaryItem(type, totalQuantity(quantities), pricePerKG);
    }


    private QuantityInKG totalQuantity(List<QuantityInKG> quantities) {
        return quantities.stream().reduce(new QuantityInKG(0), QuantityInKG::add);
    }
}
