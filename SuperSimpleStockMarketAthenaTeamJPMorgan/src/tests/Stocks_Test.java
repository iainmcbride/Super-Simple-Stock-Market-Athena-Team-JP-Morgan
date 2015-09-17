package tests;

import static org.junit.Assert.*;

import java.util.Date;

import models.Stock;
import models.Stocks;
import models.Trade;

import org.junit.Test;

public class Stocks_Test {

	@Test
	public void Stocks_isStocksObjectCreatedCorrectly_Test() 
	{
		Stocks allStocks = new Stocks();

		allStocks.addStock("TEA", Stock.STOCKTYPE.COMMON, 0, 0.0, 100);
		allStocks.addStock("POP", Stock.STOCKTYPE.COMMON, 8, 0.0, 100);
		
		assertEquals("Stocks Count = '2'", 2, allStocks.getStocksCount());
		
		allStocks.addStock("ALE", Stock.STOCKTYPE.COMMON, 23, 0.0, 60);
		allStocks.addStock("GIN", Stock.STOCKTYPE.PREFERRED, 8, 0.02, 100);
		allStocks.addStock("JOE", Stock.STOCKTYPE.COMMON, 13, 0.0, 250);
		
		assertEquals("Stocks Count = '5'", 5, allStocks.getStocksCount());
		
	}
	
	
	@Test
	public void Stocks_isGBCEAllShareIndexCalculatedCorrectly_Test()
	{
		Stocks allStocks = new Stocks();

		allStocks.addStock("TEA", Stock.STOCKTYPE.COMMON, 0, 0.0, 100);
		allStocks.addStock("POP", Stock.STOCKTYPE.COMMON, 8, 0.0, 100);
		
		
		Stock stock1 = allStocks.getStock(0);
		Stock stock2 = allStocks.getStock(1);
		
		stock1.tradeThisStock(10, 2, Trade.TRADETYPE.BUY, new Date());
		stock2.tradeThisStock(10, 2, Trade.TRADETYPE.SELL, new Date());

		assertEquals("GBCE All share index = '2'", 2, allStocks.getGBCEAllShareIndex(), 0.001);
		
		
		
		allStocks = new Stocks();

		allStocks.addStock("ALE", Stock.STOCKTYPE.COMMON, 23, 0.0, 60);
		allStocks.addStock("GIN", Stock.STOCKTYPE.PREFERRED, 8, 0.02, 100);
		allStocks.addStock("JOE", Stock.STOCKTYPE.COMMON, 13, 0.0, 250);
		
		
		Stock stock3 = allStocks.getStock(0);
		Stock stock4 = allStocks.getStock(1);
		Stock stock5 = allStocks.getStock(2);
		
		stock3.tradeThisStock(10, 5, Trade.TRADETYPE.BUY, new Date());
		stock4.tradeThisStock(10, 6, Trade.TRADETYPE.SELL, new Date());
		stock5.tradeThisStock(10, 7, Trade.TRADETYPE.SELL, new Date());

		assertEquals("GBCE All share index = '5.943'", 5.943, allStocks.getGBCEAllShareIndex(), 0.001);
		
		
	}

}
