import java.util.ArrayList;
import java.util.Date;


public class Trades 
{
	
	private ArrayList<Trade> trades;
	
	public Trades()
	{
		trades = new ArrayList<Trade>();
	}
	
	
	public ArrayList<Trade> getTrades()
	{
		return trades;
	}
	
	public int countTrades()
	{
		return trades.size();
	}
	
	public void addTrade(int tradeQuantity, int tradePrice,  Stock.TRADETYPE tradeType, Date timeStamp)
	{
		trades.add(new Trade(tradeQuantity, tradePrice, tradeType, timeStamp));
	}

}
