    public class TestStockObject {
       public static void main(String[] args) {
         Stock stock1 = new Stock("Sun Microsystems Inc", "SUNW");
         stock1.setPreviousClosingPrice(50);
         stock1.setCurrentPrice(90);
         System.out.println("---STOCK CHANGE RESULTS---");
         System.out.println();  
         System.out.println("COMPANY: " + stock1.getName());
         System.out.println("STOCK SYMBOL: " + stock1.getSymbol());
         System.out.println();
         System.out.println("PRICE FROM PREVIOUS DAY: " + "$" + stock1.getPreviousClosingPrice());
         System.out.println("CURRENT PRICE: " + "$" + stock1.getCurrentPrice());
         System.out.println();
         System.out.printf("PRICE PERCENTAGE CHANGE: %.2f%% ", stock1.changePercent());
      } // end main
   } // end class