    public class Stock {
   
   // --- GLOBAL VARIABLES/INSTANCE VARIABLES DATA TABLE ---
      private String symbol; // all stocks have a symbol
      private String name; // all stocks have a name
      private double previousClosingPrice; // all stocks have a closing price from the previous day
      private double currentPrice; // all stocks have a current price at current time
   
       Stock() { // zero arg constructor, implicitly declared in Java
      } // end zero arg Stock constructor
   
       Stock(String newName, String newSymbol) { // constructor creates stock object with a name and a symbol
         name = newName;
         symbol = newSymbol;
      } // end arg Stock constructor
   
       public String getSymbol() { // accessor method returns Stock objects current symbol value
         return symbol; // this variable is global, and all global variables have scope within the entire class, including methods in the class
      } // end getSymbol
   
       public String getName() { // accessor method returns Stock objects current name
         return name;
      } // end getName
   
       public double getPreviousClosingPrice() { // accessor method returns Stock objects closing price from previous day
         return previousClosingPrice;
      } // end getPreviousClosingPrice
   
       public double getCurrentPrice() { // accessor method returns Stock objects current price
         return currentPrice;
      } // end getCurrentPrice
   
       public void setCurrentPrice(double newCurrentPrice) { // mutator method that sets a new current price for the stock object instance
         currentPrice = newCurrentPrice;
      } // end setCurrentPrice
   
       public void setPreviousClosingPrice(double newPreviousClosingPrice) { // mutator method that sets a new previous price for the stock object instance
         previousClosingPrice = newPreviousClosingPrice;
      } // end setPreviousClosingPrice
   
       public double changePercent() { // method that takes the percent change or difference between the stock objects previous price and current price
         double percentDifference = 0;
         percentDifference = (currentPrice - previousClosingPrice) / previousClosingPrice;
         percentDifference = percentDifference * 100;
			return percentDifference;
      } // end changePercent
   
   } // end class
