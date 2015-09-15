import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;


public class StarterClass {



	public static void main(String[] args) 
	{

		/*
		 * Create the example stocks as shown in the specification
		 */
		Stocks allStocks = new Stocks();

		allStocks.addStock("TEA", Stock.STOCKTYPE.COMMON, 0, 0.0, 100);
		allStocks.addStock("POP", Stock.STOCKTYPE.COMMON, 8, 0.0, 100);
		allStocks.addStock("ALE", Stock.STOCKTYPE.COMMON, 23, 0.0, 60);
		allStocks.addStock("GIN", Stock.STOCKTYPE.PREFERRED, 8, 0.02, 100);
		allStocks.addStock("JOE", Stock.STOCKTYPE.COMMON, 13, 0.0, 250);



		/*
		 * Generate a sequence of random transactions involving the stocks shown above
		 */
		Random random = new Random();
		int numberofTrades = 10;

		for (int i = 0; i < numberofTrades; i++)
		{
			// Generate a random number between zero and the number of stock objects minus one
			int j = random.nextInt(allStocks.getStocksCount());

			// Select one of the stock objects at random
			Stock stockTraded = allStocks.getStock(j);

			// Generate a random number either 0 or 1 - 0 is a BUY trade, 1 is a SELL trade
			int k = random.nextInt(2);

			// Generate a random number between 1 and 10 - this will be the number of the stock object which is sold
			int numberSold = random.nextInt(10) + 1;

			// Generate a random number between 1 and 50 - this will be the price at which this stock object will be traded
			int price = random.nextInt(50) + 1;

			// Generate a random number to between 0 and 1000*60*30
			int timeDifference = random.nextInt(1000*60*30);
			//long timeDifference = random.nextLong() / (1000*60*30);

			
			if (k == 0)
			{
				stockTraded.tradeThisStock(numberSold, price, Stock.TRADETYPE.BUY, new Date(new Date().getTime() - timeDifference));
			}
			else
			{
				stockTraded.tradeThisStock(numberSold, price, Stock.TRADETYPE.SELL, new Date(new Date().getTime() - timeDifference));
			}
		}


		PrintWriter out = null;

		try
		{

			try
			{
				out = new PrintWriter("Stock and Trade Details.txt");


				/*
				 * Output the details of the stocks and the information requested in the specification
				 */
				out.println("****All stocks\n");

				out.println(allStocks);
				out.println();


				/*
				 * Output the details of the random transactions created by stock
				 */
				out.println("****Transactions by Stock \n");


				for (int i = 0; i < allStocks.getStocksCount(); i++)
				{
					Stock stock = allStocks.getStock(i);

					out.println(stock.getStockName());
					//System.out.println(stock.printTrades());

					if (stock.countTrades() >0)
					{

						out.format("%8s : %11s : %8s : %8s \n", "Quantity", "Stock Price", "BUY / SELL", "Timestamp");
						out.println(stock.printTrades());
					}
					else
					{
						out.format("No trades involving this stock. \n");
						out.println();

					}


				}


				/*
				 * Output the stock performance data including Stock Price, PE Ratio, Dividend Yield and a count of the number of trades in the last 15 minutes
				 */
				out.println();

				out.println("****Stock performance data \n");

				out.format("%12s : %11s : %8s : %14s : %26s \n", "Stock Symbol", "Stock Price", "PE Ratio", "Dividend Yield", "Trades in the last 15 mins");
				for (int i = 0; i < allStocks.getStocksCount(); i++)
				{

					Stock stock = allStocks.getStock(i);
					stock.getStockPrice();

					if (stock.countTradesintheLastFifteenMinutes() >0)
					{
						out.format("%-12s : %-11.2f : %-8.3f : %-14.3f : %-26s \n", stock.getStockName(), stock.getStockPrice(), stock.getPriceEarningRatio(), stock.getDividendYield(), stock.countTradesintheLastFifteenMinutes());
					}
					else
					{
						out.format("%-12s : No trades in the last 15 minutes. \n", stock.getStockName());
					}

				}

				/*
				 * Output the GBCE All Shares Index calculated according to the specification
				 */
				out.println();
				out.format("GBCE All Share Index : %.2f", allStocks.getGBCEAllShareIndex());

				out.close();

			}
			finally
			{
				// If the printwriter has been initialised then close the printwriter
				if (out != null) out.close();
			}
		}
		catch (IOException error)
		{
			// handle the exception if there is a problem writing to the file
		}



	}





}
