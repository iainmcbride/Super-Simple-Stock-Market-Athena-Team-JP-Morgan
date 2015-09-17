package models;

import java.util.Date;


public class Trade
{
	
	public enum TRADETYPE {BUY, SELL}
	
	
	/**
	 * The price at which the stock is traded.
	 */
	int tradePrice; 

	/**
	 * The quantity of stock which is being traded.
	 */
	int tradeQuantity;

	/**
	 * The type of trade - either BUY or SELL - the options may be extended by adding further options
	 * to the Trade.TRADETYPE enum.
	 */
	Trade.TRADETYPE tradeType;

	/**
	 * The exact time at which the trade was processed.
	 */
	Date timeStamp;

	/**
	 * Generate a new trade.
	 * 
	 * @param tradeQuantity The quantity of this stock object to be traded
	 * @param tradePrice	The price at which the stock is traded in this trade
	 * @param tradeType		The type of trade - either BUY or SELL
	 * @param timeStamp		The time at which this trade was completed
	 */
	public Trade(int tradeQuantity, int tradePrice, Trade.TRADETYPE tradeType, Date timeStamp) throws IllegalArgumentException
	{
		this.tradePrice = tradePrice;
		this.tradeQuantity = tradeQuantity;
		this.tradeType = tradeType;
		this.timeStamp = timeStamp;
		
		// if the number of items to trade is less than one then throw an IllegalArgumentException
		if (tradeQuantity < 1)
		{
			throw new IllegalArgumentException("Trade quantity is negative.");
		}
		
		// if the trade price is less than zero then throw an IllegalArgumentException
		if (tradePrice <= 0)
		{
			throw new IllegalArgumentException("Trade price is negative.");
		}
		
		
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
	public Trade.TRADETYPE getTradeType()
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