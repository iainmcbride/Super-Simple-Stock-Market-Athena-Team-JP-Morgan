import java.util.Date;


public class Trade
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