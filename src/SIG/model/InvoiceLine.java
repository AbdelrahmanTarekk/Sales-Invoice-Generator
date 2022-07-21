package SIG.model;

public class InvoiceLine {
    private String name;
    private double price;
    private int count;
    private double total;
    InvoiceHeader h;


    public InvoiceLine( String name, double price, int count, InvoiceHeader h) {
       // this.num = num;
        this.name = name;
        this.price = price;
        this.count = count;
        this.h = h;
    }

    public InvoiceHeader getH() {
        return h;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", total=" + getTotal() +
                '}';
    }

    public double getTotal() {
        total = count * price;
        return total;
    }

    public String getAsCSV(){
        return h.getInvNumber() + "," + getName() + "," + getPrice()
                + "," + getCount() ;
    }

}
