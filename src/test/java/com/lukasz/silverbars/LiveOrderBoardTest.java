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
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(5.5), pricePerKG(302.00)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(1.0), pricePerKG(305.00)));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aBuyOrderSummary(new QuantityInKG(1.0), pricePerKG(305.00)),
                        aBuyOrderSummary(new QuantityInKG(3.5), pricePerKG(303.00)),
                        aBuyOrderSummary(new QuantityInKG(5.5), pricePerKG(302.00))
                )
        ));
    }


    @Test
    public void should_register_multiple_buy_order_with_some_for_the_same_price() {
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(3.5), pricePerKG(303.00)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(5.5), pricePerKG(302.00)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(1.0), pricePerKG(305.00)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(6.5), pricePerKG(303.00)));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aBuyOrderSummary(new QuantityInKG(1.0), pricePerKG(305.00)),
                        aBuyOrderSummary(new QuantityInKG(10.0), pricePerKG(303.00)),
                        aBuyOrderSummary(new QuantityInKG(5.5), pricePerKG(302.00))
                )
        ));
    }

    @Test
    public void should_register_single_sell_order() {
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(1.0), pricePerKG(44.44)));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aSellOrderSummary(new QuantityInKG(1.0), pricePerKG(44.44))
                )
        ));
    }

    @Test
    public void should_register_multiple_sell_orders_and_sort_by_lowest_price() {
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(3.5), pricePerKG(306)));
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(1.2), pricePerKG(310)));
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(1.5), pricePerKG(307)));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aSellOrderSummary(new QuantityInKG(3.5), pricePerKG(306)),
                        aSellOrderSummary(new QuantityInKG(1.5), pricePerKG(307)),
                        aSellOrderSummary(new QuantityInKG(1.2), pricePerKG(310))
                )
        ));
    }

    @Test
    public void should_register_multiple_sell_orders_with_some_for_the_same_price() {
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(3.5), pricePerKG(306)));
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(1.2), pricePerKG(310)));
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(1.5), pricePerKG(307)));
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(2.0), pricePerKG(306)));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aSellOrderSummary(new QuantityInKG(5.5), pricePerKG(306)),
                        aSellOrderSummary(new QuantityInKG(1.5), pricePerKG(307)),
                        aSellOrderSummary(new QuantityInKG(1.2), pricePerKG(310))
                )
        ));
    }

    @Test
    public void should_register_multiple_sell_and_buy_orders() {
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(3.5), pricePerKG(306)));
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(1.2), pricePerKG(310)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(1.2), pricePerKG(306)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(1.2), pricePerKG(306)));
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(1.5), pricePerKG(307)));
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(1.2), pricePerKG(310)));
        orderBoard.register(sellOrder(randomUser(), new QuantityInKG(2.0), pricePerKG(306)));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aBuyOrderSummary(new QuantityInKG(1.2), pricePerKG(310)),
                        aBuyOrderSummary(new QuantityInKG(2.4), pricePerKG(306)),
                        aSellOrderSummary(new QuantityInKG(5.5), pricePerKG(306)),
                        aSellOrderSummary(new QuantityInKG(1.5), pricePerKG(307)),
                        aSellOrderSummary(new QuantityInKG(1.2), pricePerKG(310))
                )
        ));
    }

    @Test
    public void should_return_false_when_canceling_non_existing_order() {
        assertThat(orderBoard.cancel(new OrderId()), is(false));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf()
        ));
    }

    @Test
    public void should_cancel_existing_order() {
        Order order = sellOrder(randomUser(), new QuantityInKG(3.5), pricePerKG(306));
        orderBoard.register(order);

        assertThat(orderBoard.cancel(order.getOrderId()), is(true));

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf()
        ));
    }

    @Test
    public void should_cancel_one_order_and_leave_other_ones() {
        Order order = sellOrder(randomUser(), new QuantityInKG(3.5), pricePerKG(306));
        orderBoard.register(order);
        orderBoard.register(buyOrder(randomUser(), new QuantityInKG(1.2), pricePerKG(306)));

        orderBoard.cancel(order.getOrderId());

        assertThat(orderBoard.summary(), is(
                anOrderSummaryOf(
                        aBuyOrderSummary(new QuantityInKG(1.2), pricePerKG(306))
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

    private OrderSummaryItem aSellOrderSummary(QuantityInKG quantityInKG, PricePerKG pricePerKG) {
        return new OrderSummaryItem(OrderType.SELL, quantityInKG, pricePerKG);
    }
}
