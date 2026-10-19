import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.ArrayDeque;
public class PortfolioLedger {
    private Map<String,Queue<Trade>> inventory;
    private double totalRealizedPnl;

    public PortfolioLedger() {
        this.inventory = new HashMap<>();
        this.totalRealizedPnl = 0.0;
    }
    
    public void buy(String ticker,int quantity,double price){
        if(!this.inventory.containsKey(ticker)){
            this.inventory.put(ticker ,new ArrayDeque<Trade>());
        }
        Trade trade = new Trade(ticker, quantity, price);
        this.inventory.get(ticker).offer(trade);
    }

    public void sell(String ticker,int quantity,double price){
        int remainingToSell = quantity;
        Queue<Trade> queue = this.inventory.get(ticker);
        if (queue == null || queue.isEmpty()) {
            throw new IllegalStateException("No shares available to sell for " + ticker);
        }
        while (remainingToSell > 0) {
            if (queue.isEmpty()) {
                throw new IllegalStateException("Not enough shares to complete sell order for " + ticker);
            }
            Trade oldestlot = queue.peek();
            if(oldestlot.getQuantity() > remainingToSell){
                this.totalRealizedPnl += remainingToSell * (price - oldestlot.getPrice());
                oldestlot.setQuantity(oldestlot.getQuantity()-remainingToSell);
                remainingToSell = 0;
            }
            else {
                this.totalRealizedPnl += oldestlot.getQuantity() * (price - oldestlot.getPrice());
                remainingToSell -= oldestlot.getQuantity();
                queue.poll();
            }
        }
    }

    public double getTotalRealizedPnl(){
        return this.totalRealizedPnl;
    }
}
