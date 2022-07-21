package SIG.view;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.*;

public class InvoiceDialog extends JDialog {
    private JTextField customerName;
    private JTextField invDateFiled;
    private JLabel custNameLb;
    private JLabel invDateLb;
    private JButton addBtn;
    private JButton cancelBtn;

    public InvoiceDialog(SIGMainFrame f) {
        custNameLb = new JLabel("Customer Name:");
        customerName = new JTextField(20);
        invDateLb = new JLabel("Invoice Date:");
        invDateFiled = new JTextField(20);
        addBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        addBtn.setActionCommand("createInvoice");
        cancelBtn.setActionCommand("CancelCreationInv");
        addBtn.addActionListener(f.getHandler());
        cancelBtn.addActionListener(f.getHandler());
        setLocation(100,100);
        setLayout(new GridLayout(3, 2));
        add(invDateLb);
        add(invDateFiled);
        add(custNameLb);
        add(customerName);
        add(addBtn);
        add(cancelBtn);
        pack();
    }

    public JTextField getCustNameField() {
        return customerName;
    }

    public JTextField getInvDateField() {
        return invDateFiled;
    }

}


