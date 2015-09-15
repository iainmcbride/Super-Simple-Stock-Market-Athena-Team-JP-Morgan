import java.util.ArrayList;
import java.util.Date;


public class Stock 
{
	public enum STOCKTYPE {COMMON, PREFERRED}
	public enum TRADETYPE {BUY, SELL}

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
	private ArrayList<Trade> trades;
	
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

		trades = new ArrayList<Trade>();
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
		return trades.size();
	}
	
	
	/**
	 * Returns the number of times that this stock was traded within the last 15 minutes
	 * 
	 * @return The number of times that this stock was traded in the last 15 minutes
	 */
	public int countTradesintheLastFifteenMinutes()
	{
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
	public void tradeThisStock(int tradeQuantity, int tradePrice,  Stock.TRADETYPE tradeType, Date timeStamp)
	{
		trades.add(new Trade(tradeQuantity, tradePrice, tradeType, timeStamp));
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
	 * Create an inner class to handle the trades for a given stock.
	 * 
	 * @author Iain
	 *
	*/
	private class Trade
	{
		/**
		 * The price at which the stock is traded.
		 */
		int tradePrice; 

		/**
		 * The quantity of stock which is being traded
		 */
		int tradeQuantity;

		/**
		 * The type of trade - either BUY or SELL
		 */
		Stock.TRADETYPE tradeType;

		/**
		 * The exact time at which the trade was processed
		 */
		Date timeStamp;

		/**
		 * Generate a trade involving this stock object
		 * 
		 * @param tradeQuantity The quantity of this stock object to be traded
		 * @param tradePrice	The price at which the stock is traded in this trade
		 * @param tradeType		The type of trade - either BUY or SELL
		 * @param timeStamp		The timestamp of this trade
		 */
		public Trade(int tradeQuantity, int tradePrice, Stock.TRADETYPE tradeType, Date timeStamp)
		{
			this.tradePrice = tradePrice;
			this.tradeQuantity = tradeQuantity;
			this.tradeType = tradeType;
			this.timeStamp = timeStamp;
		}


		/**
		 * Returns the price at which the stock was traded in this trade
		 * 
		 * @return tradePrice	The price at which the stock was traded
		 */
		public int getTradePrice()
		{
			return tradePrice;
		}

		/**
		 * Returns the quantity of this stock which was traded in this trade
		 * 
		 * @return tradeQuantity	The quantity of this stock which was traded in this trade
		 */
		public int getTradeQuantity()
		{
			return tradeQuantity;
		}

		/**
		 * Returns the type of this trade - either BUY or SELL
		 * 
		 * @return tradeType 	The type of this trade - either BUY or SELL
		 */
		public Stock.TRADETYPE getTradeType()
		{
			return tradeType;
		}

		/**
		 * Returns the exact time at which this trade was processed
		 * 
		 * @return timeStamp 	The exact time at which this trade was processed
		 */
		public Date getTimeStamp()
		{
			return timeStamp;
		}


		public String toString()
		{
			String tradeDetails = String.format("%-8s : %-11s : %-10s : %-8s", tradeQuantity, tradePrice, tradeType, timeStamp);
			return tradeDetails;
		}

	}

}
