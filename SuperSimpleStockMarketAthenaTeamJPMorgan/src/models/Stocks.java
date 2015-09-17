package models;

import java.util.ArrayList;

public class Stocks 
{
	/**
	 * A collection of the Stock objects contained in this Stocks object
	 */
	private ArrayList<Stock> stocks;

	/**
	 * A collection class of Stock objects.
	 */
	public Stocks()
	{
		stocks = new ArrayList<Stock>();
	}

	/**
	 * Add a new stock to the collection of stocks
	 * 
	 * @param stockSymbol	The stock symbol for the newly added stock
	 * @param stockType		The type of the newly added stock - currently either COMMON or PREFERRED
	 * @param lastDividend	The last dividend payable on this stock
	 * @param fixedDividend	The fixed dividend payable on this stock
	 * @param parValue		The par value of this stock	
	 */
	public void addStock(String stockSymbol, Stock.STOCKTYPE stockType, int lastDividend, double fixedDividend, int parValue)
	{
		stocks.add(new Stock(stockSymbol, stockType, lastDividend, fixedDividend, parValue));
	}

	/**
	 * Return the GBCE All Share index. This is calculated as the geometric mean of of prices for all of the stocks.
	 * 
	 * @return The geometric mean of the stock price taken over all stocks that have been traded within the last 15 minutes
	 */
	public double getGBCEAllShareIndex()
	{
		double productofStockPrices = 1.0;
		int stocksTraded = 0;

		if (stocks.size() == 0)		// if there are no stocks in the arraylist then return a value of 0.0
		{
			return 0.0;
		}
		else
		{
			// Check each stock in the stocks collection
			for (Stock stock : stocks)
			{
				// This stock should only be considered part of the calculation if there
				// have been one or more trades involving this stock in the last 15 minutes
				if (stock.countTradesintheLastFifteenMinutes() > 0)
				{
					productofStockPrices = stock.getStockPrice() * productofStockPrices;
					stocksTraded++;
				}

			}

			// This calculation is the geometric mean of the price of all of the stocks
			// which have been traded within the last 15 minutes
			return Math.pow(productofStockPrices, (1.0 / stocksTraded));

		}
	}


	/**
	 * Return a selected stock from a given position in the list of stocks
	 * 
	 * @param stockPositioninArrayList The position of the desired stock in the list of stocks
	 * 
	 * @return	The stock object at position stockPositioninArrayList
	 */
	public Stock getStock(int stockPositioninArrayList)
	{
		return stocks.get(stockPositioninArrayList);
	}

	/**
	 * Return a count of the number of stock objects in the collection of stocks
	 * 
	 * @return The number of stock objects in the collection
	 */
	public int getStocksCount()
	{
		return stocks.size();
	}

	public String toString()
	{
		String outputString = ("Stock Symbol : Type       : Last Dividend : Fixed Dividend : Par Value \n");

		for (Stock stock : stocks)
		{
			outputString += stock + "\n";
		}

		return outputString;
	}
}
