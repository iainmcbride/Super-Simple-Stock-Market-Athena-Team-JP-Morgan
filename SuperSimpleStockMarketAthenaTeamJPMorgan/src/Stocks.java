import java.util.ArrayList;

public class Stocks 
{
	/**
	 * A collection of each of the Stock objects contained in the list of stocks
	 */
	private ArrayList<Stock> stocks;

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
			for (Stock stock : stocks)
			{
				if (stock.countTradesintheLastFifteenMinutes() > 0)
				{
					productofStockPrices *= stock.getStockPrice();
					stocksTraded++;
				}

			}

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
