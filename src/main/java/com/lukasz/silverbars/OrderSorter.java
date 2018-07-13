package com.lukasz.silverbars;

import com.lukasz.silverbars.model.OrderSummaryItem;
import com.lukasz.silverbars.model.OrderType;

public class OrderSorter {

    static public int byPriceAndType(OrderSummaryItem a, OrderSummaryItem b) {
        if (a.getType() != b.getType()) {
            return a.getType() == OrderType.SELL ? 1 : -1;
        }

        if (a.getType() == OrderType.BUY) {
            return b.getPricePerKG().getPrice().compareTo(a.getPricePerKG().getPrice());
        } else {
            return a.getPricePerKG().getPrice().compareTo(b.getPricePerKG().getPrice());
        }


    }
}
