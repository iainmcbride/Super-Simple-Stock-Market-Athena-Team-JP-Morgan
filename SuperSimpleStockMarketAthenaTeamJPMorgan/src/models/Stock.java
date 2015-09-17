package models;

import java.util.ArrayList;
import java.util.Date;


public class Stock 
{
	public enum STOCKTYPE {COMMON, PREFERRED}
	

	/**
	 * The ticker price of this stock
	 */
	private double tickerPrice;

	/**
	 * The stock symbol for this stock
	 */
	private String stockSymbol;

	/**
	 * The type of this stock - currently either COMMON or PREFFERED
	 * (The number of types can be extended by adding to the STOCKTYPE enum
	 */
	private STOCKTYPE stockType;

	/**
	 * The last dividend payable on this stock
	 */
	private int lastDividend;

	/**
	 * The fixed dividend payable on this stock
	 */
	private double fixedDividend;

	/**
	 * The par value of this stock
	 */
	private int parValue;

	/**
	 * A list of the trades involving this stock
	 */
	private Trades allTrades;
	
	/**
	 * The number of trades within the last 15 minutes
	 */
	private int tradesintheLastFifteenMinutes;


	/**
	 * Create a new stock object
	 * 
	 * @param stockSymbol	The stock symbol to be used for this stock
	 * @param stockType		The type for this stock - either COMMON or PREFERRED (other options may be added by extending the STOCKTYPE enum)
	 * @param lastDividend	The last dividend payable on this stock
	 * @param fixedDividend	The fixed dividend payable on this stock
	 * @param parValue		The par value of this stock
	 */
	public Stock(String stockSymbol, STOCKTYPE stockType, int lastDividend, double fixedDividend, int parValue)
	{
		this.stockSymbol = stockSymbol;
		this.stockType = stockType;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;

		allTrades = new Trades();
		tradesintheLastFifteenMinutes = 0;
	}


	/**
	 * Format the String output for each stock
	 */
	public String toString()
	{
		String outputString = String.format("%-12s : %-10s : %-13s : %-14s : %-9s", stockSymbol, stockType, lastDividend, fixedDividend, parValue);
		return outputString;
	}

	/**
	 * Returns the number of times that this stock was traded whether within the last 15 minutes or not
	 * 
	 * @return The number of times that this stock was traded
	 */
	public int countTrades()
	{
		return allTrades.countTrades();
	}
	
	
	/**
	 * Returns the number of times that this stock was traded within the last 15 minutes
	 * 
	 * @return The number of times that this stock was traded in the last 15 minutes
	 */
	public int countTradesintheLastFifteenMinutes()
	{
		
		this.tradesintheLastFifteenMinutes = 0;
		
		ArrayList<Trade> trades = allTrades.getTrades();


		/*
		 * Select the trades from the last 15 minutes that will be used to calculate the stock price
		 */
		Date now = new Date();
		long milliSecsinFifteenMinutes = (15*60*1000);  // (15*60*1000) is the number of milliseconds in 15 minutes
		Long fifteenMinutesAgo = now.getTime() - milliSecsinFifteenMinutes; 
		
		for (int i = 0; i < trades.size(); i++)
		{
			Trade thisTrade = trades.get(i);
			
			if (thisTrade.getTimeStamp().after(new Date(fifteenMinutesAgo)))
			{
				tradesintheLastFifteenMinutes++;
			}
		}

		return tradesintheLastFifteenMinutes;
	}
	

	/**
	 * Return a dividend yield calculated on the basis of the type of stock and the dividend for the particular stock in question
	 * 
	 * @return dividendYield The calculated dividend yield
	 */
	public double getDividendYield()
	{
		double dividendYield = 0.0;
		getStockPrice();

		// The assumption here is that the stock will be either COMMON or PREFFERED.
		// Any other type of stock will return a dividend of zero.

		if (stockType == STOCKTYPE.COMMON)
		{
			dividendYield = lastDividend / tickerPrice;
		}
		else if (stockType == STOCKTYPE.PREFERRED)
		{
			dividendYield = (fixedDividend * parValue) / tickerPrice;
		}

		return dividendYield;
	}
	/**

	 * Return the P/E ratio for this stock.
	 * 
	 * The P/E ratio is calculated as the ratio of the ticker price to the last dividend
	 * 
	 * @return tickerPrice/lastDividend
	 */
	public double getPriceEarningRatio()
	{
		// have to assume here that dividend in the specification is the last dividend
		// it is not specified whether last dividend or fixed dividend is to be used
		// but only last dividend exists for all stocks in the specification

		if (lastDividend > 0)
		{
			getStockPrice();
			return tickerPrice / lastDividend;
		}
		else
		{
			// have to assume that if the last dividend is zero then the P/E ratio should also be zero - I'm not sure this assumption is correct
			return 0.0;
		}
	}


	/**
	 * Create a trade involving this stock.
	 * 
	 * @param tradePrice	The price at which this stock was traded
	 * @param tradeQuantity	The quantity of stock involved in this trade
	 * @param tradeType		The type of trade - was this BUY or SELL
	 * @param timeStamp		The timestamp of this trade
	 */
	public void tradeThisStock(int tradeQuantity, int tradePrice,  Trade.TRADETYPE tradeType, Date timeStamp) throws IllegalArgumentException
	{
		allTrades.addTrade(tradeQuantity, tradePrice, tradeType, timeStamp);
	}


	/**
	 * Calculate the stock price for this stock based on trades that happened in the last 15 minutes
	 * 
	 * @return 		The sum of all trade prices times quantities taken over all trades divided by the sum of all quantities taken over all trades 
	 */
	public double getStockPrice()
	{
		double numerator = 0.0;
		double denominator = 0.0;
		
		this.tradesintheLastFifteenMinutes = 0;
		
		ArrayList<Trade> trades = allTrades.getTrades();


		/*
		 * Calculate the time 15 minutes ago
		 */
		Date now = new Date();
		long milliSecsinFifteenMinutes = (15*60*1000);  // (15*60*1000) is the number of milliseconds in 15 minutes
		Long fifteenMinutesAgo = now.getTime() - milliSecsinFifteenMinutes; 
		
		// Iterate over all trades for this stock selecting only those within the last 15 minuets to calculate
		// the stock price
		for (int i = 0; i < trades.size(); i++)
		{
			Trade thisTrade = trades.get(i);
			
			// if this trade has a timestamp within the last 15 minutes
			if (thisTrade.getTimeStamp().after(new Date(fifteenMinutesAgo)))
			{
				numerator += (thisTrade.getTradePrice() * thisTrade.getTradeQuantity());
				denominator += thisTrade.getTradeQuantity();
				tradesintheLastFifteenMinutes++;
			}
		}

		tickerPrice = numerator / denominator;
		return tickerPrice;

	}

	/**
	 * Generate a string containing the details of each of the transactions involving this stock
	 * 
	 * @return A String containing all of the trades involving this stock
	 */
	public String printTrades()
	{
		String outputString = "";
		
		ArrayList<Trade> trades = allTrades.getTrades();

		for (Trade trade : trades)
		{
			outputString += trade + "\n";
		}
		
		return outputString;
	}

	/**
	 * Returns the Stock Symbol for this stock
	 * @return
	 */
	public String getStockName()
	{
		return this.stockSymbol;
	}
	
	/**
	 * Return the stock type of this stock - either Common or Preferred. Other types may be added to the
	 * list of possible stock types by extending the Stock.STOCKTYPE enum.
	 * 
	 * @return stockType The type of stock either Common or Preferred
	 */
	public STOCKTYPE getStockType()
	{
		return stockType;
	}
	
	/**
	 * Return the last dividend paid on this stock.
	 * 
	 * @return The last dividend paid on this stock
	 */
	public int getLastDividend()
	{
		return lastDividend;
	}

	/**
	 * Return the fixed dividend payable on this stock.
	 * 
	 * @return fixedDividend The fixed dividend payable on this stock.
	 */
	public double getFixedDividend()
	{
		return fixedDividend;
	}
	
	/**
	 * Return the Par Value of this stock
	 * 
	 * @return parValue The Par Value payable on this stock
	 */
	public int getParValue()
	{
		return parValue;
	}


}
