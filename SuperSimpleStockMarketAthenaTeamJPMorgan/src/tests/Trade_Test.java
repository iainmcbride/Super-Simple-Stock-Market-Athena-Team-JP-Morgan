package tests;

import static org.junit.Assert.*;

import java.util.Date;

import models.Stock;
import models.Trade;

import org.junit.Test;

public class Trade_Test {

	/**
	 * Ensure that the Trade class throws an IllegalArgumentException when the trade price of the stock to be 
	 * traded is negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void Stock_Trade_Negative_Price_Exception_Test()
	{
		Stock testStock = new Stock("TEA", Stock.STOCKTYPE.COMMON, 10, 0.02, 5);
		
		testStock.tradeThisStock(10, -15, Trade.TRADETYPE.BUY, new Date());
	}
	
	/**
	 * Ensure that the Trade class throws an IllegalArgumentException when the number of the stock to be 
	 * traded is negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void Stock_Trade_Negative_Quantity_Exception_Test()
	{
		Stock testStock = new Stock("TEA", Stock.STOCKTYPE.COMMON, 10, 0.02, 5);
		
		testStock.tradeThisStock(-10, 15, Trade.TRADETYPE.BUY, new Date());
	}
	
	
	@Test
	public void create_Trade_Test()
	{
		Date date = new Date();
		Trade trade = new Trade(10, 15, Trade.TRADETYPE.BUY, date);
		
		assertEquals("Trade quantity = '10'", 10, trade.getTradeQuantity());
		assertEquals("Trade price = '15'", 15, trade.getTradePrice());
		assertEquals("Trade type = 'BUY'", Trade.TRADETYPE.BUY, trade.getTradeType());
		assertEquals("Trade timestamp = now", date, trade.getTimeStamp());
		
	}

}
