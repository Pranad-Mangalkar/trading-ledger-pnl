
public class Trade{
    private String ticker;
    private int quantity;
    private double price;
    Trade(String ticker, int quantity,double price){
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
    }
    public String getTicker(){
        return this.ticker;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getPrice(){
        return this.price;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

}