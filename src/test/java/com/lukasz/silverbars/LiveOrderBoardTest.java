package com.lukasz.silverbars;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.lukasz.silverbars.Order.buyOrder;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LiveOrderBoardTest {

    private LiveOrderBoard orderBoard;

    @Before
    public void setUp() throws Exception {
        orderBoard = new LiveOrderBoard();
    }

    @Test
    public void should_be_empty_at_start() {
        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf()
        ));
    }

    @Test
    public void should_return_single_buy_order() {
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(3.5), pricePerKG(303.00)));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aBuyOrderSummary(new QuantityInKG(3.5), pricePerKG(302.00))
                )
        ));
    }

    private PricePerKG pricePerKG(double price) {
        return new PricePerKG(new BigDecimal(price));
    }

    private UserId randomUser() {
        return new UserId();
    }

    private OrderSummary anOrderSummaryOf(OrderSummaryItem... buyOrders) {
        return new OrderSummary(asList(buyOrders));
    }

    private OrderSummaryItem aBuyOrderSummary(QuantityInKG quantityInKG, PricePerKG pricePerKG) {
        return new OrderSummaryItem(OrderType.BUY, quantityInKG, pricePerKG);
    }
}
