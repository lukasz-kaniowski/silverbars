package com.lukasz.silverbars;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.lukasz.silverbars.Order.buyOrder;
import static com.lukasz.silverbars.Order.sellOrder;
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
    public void should_register_single_buy_order() {
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(3.5), pricePerKG(303.00)));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aBuyOrderSummary(new QuantityInKG(3.5), pricePerKG(303.00))
                )
        ));
    }

    @Test
    public void should_register_multiple_unique_buy_order_and_sort_by_highest_price() {
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(3.5), pricePerKG(303.00)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(5.5), pricePerKG(203.00)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(1.0), pricePerKG(1000.00)));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aBuyOrderSummary(new QuantityInKG(1.0), pricePerKG(1000.00)),
                        aBuyOrderSummary(new QuantityInKG(3.5), pricePerKG(303.00)),
                        aBuyOrderSummary(new QuantityInKG(5.5), pricePerKG(203.00))
                )
        ));
    }


    @Test
    public void should_register_multiple_buy_order_with_some_for_the_same_price() {
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(3.5), pricePerKG(303.00)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(5.5), pricePerKG(203.00)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(1.0), pricePerKG(1000.00)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(6.5), pricePerKG(303.00)));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aBuyOrderSummary(new QuantityInKG(1.0), pricePerKG(1000.00)),
                        aBuyOrderSummary(new QuantityInKG(10.0), pricePerKG(303.00)),
                        aBuyOrderSummary(new QuantityInKG(5.5), pricePerKG(203.00))
                )
        ));
    }

    @Test
    public void should_register_single_sell_order() {
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(1.0), pricePerKG(44.44)));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aSellOrder(new QuantityInKG(1.0), pricePerKG(44.44))
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

    private OrderSummaryItem aSellOrder(QuantityInKG quantityInKG, PricePerKG pricePerKG) {
        return new OrderSummaryItem(OrderType.SELL, quantityInKG, pricePerKG);
    }
}
