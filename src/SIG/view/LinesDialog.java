package SIG.view;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;

public class LinesDialog extends JDialog {
    private JTextField nameFiledIt;
    private JTextField countFieldIt;
    private JTextField piceFieldIt;
    private JLabel nameItemLb;
    private JLabel countItLb;
    private JLabel priceItLb;
    private JButton addBtn;
    private JButton cancelBtn;

    public LinesDialog(SIGMainFrame f) {
        nameFiledIt = new JTextField(20);
        nameItemLb = new JLabel("Item Name");

        countFieldIt = new JTextField(20);
        countItLb = new JLabel("Item Count");

        piceFieldIt = new JTextField(20);
        priceItLb = new JLabel("Item Price");

        addBtn = new JButton("Add");
        cancelBtn = new JButton("Cancel");

        addBtn.setActionCommand("createLineOK");
        cancelBtn.setActionCommand("cancelCreationLine");

        addBtn.addActionListener(f.getHandler());
        cancelBtn.addActionListener(f.getHandler());
        setLayout(new GridLayout(4, 2));
        setLocation(600,100);
        add(nameItemLb);
        add(nameFiledIt);
        add(countItLb);
        add(countFieldIt);
        add(priceItLb);
        add(piceFieldIt);
        add(addBtn);
        add(cancelBtn);
        pack();
    }

    public JTextField getItemNameField() {
        return nameFiledIt;
    }

    public JTextField getItemCountField() {
        return countFieldIt;
    }

    public JTextField getItemPriceField() {
        return piceFieldIt;
    }
}
