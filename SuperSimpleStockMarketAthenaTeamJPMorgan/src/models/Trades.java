package models;

import java.util.ArrayList;
import java.util.Date;


public class Trades 
{
	
	private ArrayList<Trade> trades;
	
	/**
	 * A collection class of Trade objects.
	 */
	public Trades()
	{
		trades = new ArrayList<Trade>();
	}
	
	/**
	 * Return a list of the Trade objects in this Trades object.
	 * 
	 * @return An array list of Trade objects.
	 */
	public ArrayList<Trade> getTrades()
	{
		return trades;
	}
	
	/**
	 * Return a count of the total number of Trade objects in this Trades collection.
	 * 
	 * @return The number of trades in this collection
	 */
	public int countTrades()
	{
		return trades.size();
	}
	
	/**
	 * Add a new Trade object to this collection.
	 * 
	 * @param tradeQuantity The quantity of a given stock to be traded
	 * @param tradePrice	The price at which the given stock is to be traded
	 * @param tradeType		The type of Trade - either BUY or SELL
	 * @param timeStamp		The timestamp of this trade
	 */
	public void addTrade(int tradeQuantity, int tradePrice,  Trade.TRADETYPE tradeType, Date timeStamp)
	{
		trades.add(new Trade(tradeQuantity, tradePrice, tradeType, timeStamp));
	}

}
