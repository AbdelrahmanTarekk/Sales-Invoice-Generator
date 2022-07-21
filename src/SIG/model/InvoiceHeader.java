package SIG.model;

import java.util.ArrayList;

public class InvoiceHeader {
    private int invNumber;
    private String invDate;
    private String customerName;
    private double invTotal;
    ArrayList<InvoiceLine> lines;

    InvoiceHeader(){
    }
    public InvoiceHeader(int invNumber, String invDate, String customerName) {
        this.invNumber = invNumber;
        this.invDate = invDate;
        this.customerName = customerName;

    }

    public int getInvNumber() {
        return invNumber;
    }

    public void setInvNumber(int invNumber) {
        this.invNumber = invNumber;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public double getInvTotal() {
        invTotal = 0.0;
        for(InvoiceLine line: getLines()){
            invTotal += line.getTotal();
        }
        return invTotal;
    }


    public ArrayList<InvoiceLine> getLines() {
        if (lines == null) {
        lines = new ArrayList<>();
        }

        return lines;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" +
                "invNumber=" + invNumber +
                ", invDate='" + invDate + '\'' +
                ", customerName='" + customerName +
                '}';
    }

    public String getAsCSV(){
        return invNumber + "," + invDate + "," + customerName;
    }
}
