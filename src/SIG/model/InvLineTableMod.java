package SIG.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class InvLineTableMod extends AbstractTableModel {

    private ArrayList<InvoiceLine> data;
    private String[] cols = {"No.","Item Name","Item Price","Count", "Total"};

    public InvLineTableMod(ArrayList<InvoiceLine> data) {
        this.data = data;
    }

    public ArrayList<InvoiceLine> getLines() {
        return data;
    }


    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine line =data.get(rowIndex);
        switch (columnIndex){
            case 0:
                return line.getH().getInvNumber();
            case 1:
                return line.getName();
            case 2:
                return line.getPrice();
            case 3:
                return line.getCount();
            case 4:
                return line.getTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }
}
