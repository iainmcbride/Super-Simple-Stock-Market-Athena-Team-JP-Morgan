package tests;


import static org.junit.Assert.*;

import java.util.Date;

import models.Stock;
import models.Trade;

import org.junit.Test;


public class Stock_Test {

	/**
	 * Check that the properties of a created Stock object are as expected
	 */
	@Test
	public void Stock_isStockObjectCreatedCorrectly_Test() 
	{
		Stock testStock = new Stock("TEA", Stock.STOCKTYPE.COMMON, 10, 0.02, 5);
		
		assertEquals("Stock Symbol = 'TEA'", "TEA", testStock.getStockName());
		assertEquals("Stock Type = 'COMMON'", Stock.STOCKTYPE.COMMON, testStock.getStockType());
		assertEquals("Stock Last Dividend = '10'", 10, testStock.getLastDividend());
		assertEquals("Stock Fixed Dividend = '0.02'", 0.02, testStock.getFixedDividend(), 2);
		assertEquals("Stock Par Value = '5'", 5, testStock.getParValue());
		
	}
	
	
	
	/**
	 * Check that the Stock object handles trades correctly. 
	 * 
	 * Does it count trades correctly?
	 * Does it count trades in the last 15 minutes correctly
	 */
	@Test
	public void Stock_areTradesCountedCorrectly_Test()
	{
		/*
		 * Check that a new stock is created with zero trades
		 */
		Stock testStock = new Stock("TEA", Stock.STOCKTYPE.COMMON, 10, 0.02, 5);
		assertEquals("Stock trade count = '0'", 0, testStock.countTrades());
		
		/*
		 * Check that adding trades with now as the timestamp are counted correctly by the stock class
		 */
		testStock.tradeThisStock(10, 15, Trade.TRADETYPE.BUY, new Date());
		assertEquals("Stock trade count = '1'", 1, testStock.countTrades());
		
		testStock.tradeThisStock(10, 15, Trade.TRADETYPE.BUY, new Date());
		assertEquals("Stock trade count = '2'", 2, testStock.countTrades());
		
		/*
		 * Test that trades within the last 15 minutes are counted correctly by adding a trade with a timestamp if 30 minutes ago
		 * This trade should not be counted as a trade within the last 15 minutes
		 */
		int timeDifference = 1000*60*30; // the number of milliseconds in 30 minutes		
		testStock.tradeThisStock(10, 15, Trade.TRADETYPE.SELL, new Date(new Date().getTime() - timeDifference));
		
		assertEquals("Stock trade count = '3'", 3, testStock.countTrades());
		assertEquals("Stock trades in last fifteen minutes count = '2'", 2, testStock.countTradesintheLastFifteenMinutes());
		
	}
	
	@Test
	public void Stock_isStockPriceCalculatedCorrectly_Test()
	{
		/*
		 * Create a new Stock object for use in this test
		 */
		Stock testStock = new Stock("TEA", Stock.STOCKTYPE.COMMON, 10, 0.02, 5);
		
		/*
		 * Create 3 trades, only 2 of which are within the last 15 minutes
		 */
		testStock.tradeThisStock(10, 15, Trade.TRADETYPE.BUY, new Date());		
		testStock.tradeThisStock(10, 15, Trade.TRADETYPE.BUY, new Date());
		
		int timeDifference = 1000*60*30; // the number of milliseconds in 30 minutes		
		testStock.tradeThisStock(10, 150, Trade.TRADETYPE.SELL, new Date(new Date().getTime() - timeDifference));
		
		
		
		/*
		 * Check that the Ticker price is correct to 2 decimal places
		 */
		assertEquals("Ticker price = '15'", 15, testStock.getStockPrice(), 0.001);
		
		
	}
		
	@Test
	public void Stock_isPriceEarningsRatioCalculatedCorrectly_Test()
	{
	
		/*
		 * Create a new Stock object for use in this test
		 */
		Stock testStock = new Stock("TEA", Stock.STOCKTYPE.COMMON, 15, 0.02, 5);
		
		/*
		 * Create 3 trades, only 2 of which have a timestamp within the last 15 minutes
		 */
		testStock.tradeThisStock(10, 30, Trade.TRADETYPE.BUY, new Date());		
		testStock.tradeThisStock(10, 30, Trade.TRADETYPE.BUY, new Date());
		
		int timeDifference = 1000*60*30; // the number of milliseconds in 30 minutes		
		testStock.tradeThisStock(10, 150, Trade.TRADETYPE.SELL, new Date(new Date().getTime() - timeDifference));
		
		
		/*
		 * Check that the Ticker price is correct to 3 decimal places
		 */
		assertEquals("P/E Ratio = '2.0'", 2.0, testStock.getPriceEarningRatio(), 0.001);
		
		
		
		/*
		 * Create a new Stock object with Last Dividend = 0 for use in this test
		 */
		Stock testStock2 = new Stock("TEA", Stock.STOCKTYPE.COMMON, 0, 0.02, 5);
		
		/*
		 * Create 2 trades, only 2 of which have a timestamp within the last 15 minutes
		 */
		testStock2.tradeThisStock(10, 15, Trade.TRADETYPE.BUY, new Date());		
		testStock2.tradeThisStock(10, 15, Trade.TRADETYPE.BUY, new Date());
				
		testStock2.tradeThisStock(10, 150, Trade.TRADETYPE.SELL, new Date(new Date().getTime() - timeDifference));
		
		
		/*
		 * Check that the Ticker price is correct to 3 decimal places
		 */
		assertEquals("P/E Ratio = '0'", 0, testStock2.getPriceEarningRatio(), 0.001);
		
	}

	@Test
	public void Stock_isDividendYieldCalculatedCorrectlyforCommonStock_Test()
	{
		/*
		 * Create a new Stock object of Common type for use in this test
		 */
		Stock testStock = new Stock("TEA", Stock.STOCKTYPE.COMMON, 15, 0.02, 5);
		
		/*
		 * Create 3 trades, only 2 of which have a timestamp within the last 15 minutes
		 */
		testStock.tradeThisStock(10, 30, Trade.TRADETYPE.BUY, new Date());		
		testStock.tradeThisStock(10, 30, Trade.TRADETYPE.BUY, new Date());
		
		int timeDifference = 1000*60*30; // the number of milliseconds in 30 minutes		
		testStock.tradeThisStock(10, 150, Trade.TRADETYPE.SELL, new Date(new Date().getTime() - timeDifference));
		
		
		/*
		 * Check that the Dividend Yield is correct to 3 decimal places for Common stocks
		 */
		assertEquals("P/E Ratio for Common Stock = '0.5'", 0.5, testStock.getDividendYield(), 0.001);
		
	}
	
	@Test
	public void Stock_isDividendYieldCalculatedCorrectlyforCommonStockwithLastDividendofZero_Test()
	{
		
		
		/*
		 * Create a new Stock object of Common type for use in this test
		 */
		Stock testStock2 = new Stock("TEA", Stock.STOCKTYPE.COMMON, 0, 0.02, 5);
		
		/*
		 * Create 3 trades, only 2 of which have a timestamp within the last 15 minutes
		 */
		testStock2.tradeThisStock(10, 30, Trade.TRADETYPE.BUY, new Date());		
		testStock2.tradeThisStock(10, 30, Trade.TRADETYPE.BUY, new Date());
		
		
		int timeDifference = 1000*60*30; // the number of milliseconds in 30 minutes		
		testStock2.tradeThisStock(10, 150, Trade.TRADETYPE.SELL, new Date(new Date().getTime() - timeDifference));
		
		
		/*
		 * Check that the Dividend Yield is correct to 3 decimal places for Common stocks
		 */
		assertEquals("P/E Ratio for Common Stock with Last Dividend of 0 = '0.00'", 0, testStock2.getDividendYield(), 0.001);
		
		
		
	}
		
	@Test
	public void Stock_isDividendYieldCalculatedCorrectlyforPreferredStock_Test()
	{	
		
		
		/*
		 * Create a new Stock object of Preferred type for use in this test
		 */
		Stock testStock3 = new Stock("GIN", Stock.STOCKTYPE.PREFERRED, 8, 0.02, 100);
		
		/*
		 * Create 3 trades, only 2 of which have a timestamp within the last 15 minutes
		 */
		testStock3.tradeThisStock(10, 10, Trade.TRADETYPE.BUY, new Date());		
		testStock3.tradeThisStock(10, 10, Trade.TRADETYPE.BUY, new Date());
				
		int timeDifference = 1000*60*30; // the number of milliseconds in 30 minutes
		testStock3.tradeThisStock(10, 150, Trade.TRADETYPE.SELL, new Date(new Date().getTime() - timeDifference));
		
		
		/*
		 * Check that the Dividend Yield is correct to 3 decimal places for Preferred stocks
		 */
		assertEquals("P/E Ratio for Preferred Stock = '0.2'", 0.2, testStock3.getDividendYield(), 0.001);
		
	}
	
}
