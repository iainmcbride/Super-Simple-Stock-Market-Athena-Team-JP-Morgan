# Super-Simple-Stock-Market-Athena-Team-JP-Morgan
The files in this repository contain Java code for the exercise demonstrating simple calculations involving stocks and trades for JP Morgan recruitment. I have also uploaded all of the JUnit tests that I have used to check the code for correctness.

Assumptions:

The specification is unclear whether a user should be able to add stocks to the system. ("No database or GUI is required, all data need only be held in memory.") Thus, I have not created any structure for user input. However, in the file starterClass I have added the list of stocks shown in the specification to the collection class Stocks. 

Since, the specification does not make clear whether the user should be able to add trades to the system, I have also created 10 random trades in the class StarterClass involving the stocks in the specification each with a timestamp between 30 minutes ago and now. (Only those trades within the last 15 minutes are used to calculate the stock price as per the specification.) These stocks and trades allow the user to see the results when using example stocks and trades and demonstrate that the code in the Stock and Stocks class performs as required.

I have assumed that in a trade the 'Quantity' traded must be one or more. Further, I have assumed that the 'Trade Price' must also be non-negative. I have also assumed that 'Last Dividend', 'Fixed Dividend' and 'Par Value' may take a negative, positive or zero value.

In the definition of P/E ratio, the denominator of the formula for calculating P/E ratio is described as 'Dividend'. I have assumed in the implemented calculation that this was intended to mean the 'Last Dividend' for the stock in question. Since the Last Dividend for the "TEA" stock is zero, the P/E ratio of the 'TEA' stock is ill-defined, I have assumed that in this case the P/E ratio should be zero.

I have set up the program to create a file called 'Stock and Trade Details.txt" in the same folder as the program is run. The details of the stocks and trades involved and the results of the calculations are output to this file. 
