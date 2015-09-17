package tests;

import static org.junit.Assert.*;

import java.util.Date;

import models.Stock;
import models.Trade;
import models.Trades;

import org.junit.Test;

public class Trades_Test {

	@Test
	public void isTradesObjectCreatedCorrectly_Test() 
	{
		Trades allTrades = new Trades();
		
		allTrades.addTrade(10, 15, Trade.TRADETYPE.BUY, new Date());
		allTrades.addTrade(10, 25, Trade.TRADETYPE.SELL, new Date());
		allTrades.addTrade(10, 35, Trade.TRADETYPE.BUY, new Date());
		
		assertEquals("Trades Count = '3'", 3, allTrades.countTrades());
		
		assertEquals("Trades Count = '3'", 3, allTrades.getTrades().size());
				
		
	}

}
