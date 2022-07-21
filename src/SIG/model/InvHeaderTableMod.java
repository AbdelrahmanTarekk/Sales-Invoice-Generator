package SIG.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class InvHeaderTableMod extends AbstractTableModel {
    private ArrayList<InvoiceHeader> data;
    private String[] colsNames = {"No.","Date","Customer","Total"};

    public InvHeaderTableMod(ArrayList<InvoiceHeader> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
       return colsNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    InvoiceHeader header = data.get(rowIndex);
    switch (columnIndex){
        case 0:
            return header.getInvNumber();
        case 1:
            return header.getCustomerName();
        case 2:
            return header.getInvDate();
        case 3:
            return header.getInvTotal();
    }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return colsNames[column];
    }


}
