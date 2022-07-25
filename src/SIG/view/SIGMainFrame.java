package SIG.view;

import SIG.controller.ActionsHandeller;
import SIG.model.InvHeaderTableMod;
import SIG.model.InvLineTableMod;
import SIG.model.InvoiceHeader;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SIGMainFrame extends JFrame {
    private JFrame f;
    private JMenuBar sigMenu;
    private JPanel leftSidePanel;
    private JPanel rightSidePanel;

    private JMenu fileMenu;
    private JMenuItem loadFileMitem;
    private JMenuItem saveFileMitem;

    private String[][] customerData={
    };
    private static JTable invTable;

    private JButton createNewInvbtn;
    private JButton deleteInvbtn;
    private Border b;
    private JPanel invTablePanel;

    private String[] customerTableBar= {"No.","Date","Customer","Total"};
    private JPanel invoiceDataPanel;
    private JPanel invoiceItemsPanel;
    private JPanel buttonsPanel;

    private JLabel invNubmerLable;
    private JTextField invoiceDateTF;
    private JTextField customerNameTF;

    private JLabel invTotalLable;

    private Border titledBorder;

    String [] itemTableBar= {"No.","Item Name","Item Price","Count","Item Total"};
    String [][] itemData={};
    private JTable invItemsTable;
    private JButton savebtn;
    private JButton cancelbtn;


    private ActionsHandeller handler ;
    private ArrayList<InvoiceHeader> invHeaderArray;
    private InvHeaderTableMod invHeaderTableMod;
    private InvLineTableMod invLineTableMod;
    private List<ListSelectionModel> models = new ArrayList<>();

    public List<ListSelectionModel> getModels() {
        return models;
    }

    public void register (ListSelectionModel model){
        models.add(model);
        model.addListSelectionListener(handler);
    }
    public void register(JTable t){
        register(t.getSelectionModel());
    }


    public ArrayList<InvoiceHeader> getInvHeaderArray() {
        if (invHeaderArray == null) invHeaderArray = new ArrayList<>();
        return invHeaderArray;
    }
    public void setInvHeaderArray(ArrayList<InvoiceHeader> invHeaderArray) {
        this.invHeaderArray = invHeaderArray;
        invHeaderTableMod = new InvHeaderTableMod(invHeaderArray);
        this.invTable.setModel(invHeaderTableMod);
    }

    public InvLineTableMod getInvLineTableMod() {
        return invLineTableMod;
    }
    public InvHeaderTableMod getInvHeaderTableMod() {
        if (invHeaderTableMod == null) {
            invHeaderTableMod = new InvHeaderTableMod(getInvHeaderArray());
        }
        return invHeaderTableMod;
    }

    public ActionsHandeller getHandler() {
        return handler;
    }

    public JTable getInvTable() {
        return invTable;
    }

    public JTable getInvItemsTable() {
        return invItemsTable;
    }

    SIGMainFrame(){
        super("SIG");
        handler = new ActionsHandeller(this);
        f = new JFrame();
        setSize(1000,700);
        setLayout(new GridLayout(1,2,10,10));
        setLocation(100,50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // setting the menuBar into the frame
        sigMenu = new JMenuBar();
        setJMenuBar(sigMenu);
        fileMenu = new JMenu("File");
        loadFileMitem=new JMenuItem("Load File",'l');
        loadFileMitem.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.CTRL_DOWN_MASK));
        loadFileMitem.setActionCommand("loadFile");
        loadFileMitem.addActionListener(handler);

        saveFileMitem=new JMenuItem("Save File",'s');
        saveFileMitem.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
        saveFileMitem.setActionCommand("saveFile");
        saveFileMitem.addActionListener(handler);
        fileMenu.add(loadFileMitem);
        fileMenu.add(saveFileMitem);
        sigMenu.add(fileMenu);

        // Creating the left-Side Panel

        leftSidePanel = new JPanel();
        leftSidePanel.setSize(500,700);
        leftSidePanel.setVisible(true);

        invTable = new JTable(customerData,customerTableBar);
        DefaultTableModel invModel = new DefaultTableModel();
        invModel.setColumnIdentifiers(customerTableBar);
        models.add(invTable.getSelectionModel());

        invTable.setModel(invModel);
        invTable.getSelectionModel().addListSelectionListener(handler);

        b = BorderFactory.createTitledBorder("Invoice Table");

        invTablePanel=new JPanel();

        invTablePanel.add(new JScrollPane(invTable));
        invTablePanel.setVisible(true);
        invTablePanel.setBorder(b);

        leftSidePanel.add(invTablePanel);

        createNewInvbtn = new JButton("Create New Invoice");
        createNewInvbtn.setActionCommand("createInvBTN");
        createNewInvbtn.addActionListener(handler);
        leftSidePanel.add(createNewInvbtn);

        deleteInvbtn = new JButton("Delete Invoice");
        deleteInvbtn.setActionCommand("delInvBTN");
        deleteInvbtn.addActionListener(handler);
        leftSidePanel.add(deleteInvbtn);

        add(leftSidePanel);

        // Creating the Right-Side Panel

        rightSidePanel = new JPanel();
        rightSidePanel.setVisible(true);
        rightSidePanel.setLayout(new GridLayout(3,1,5,5));
        rightSidePanel.setSize(500,700);
        invoiceDataPanel= new JPanel();
        invoiceDataPanel.setLayout(new GridLayout(4,2,-100,10));

        invoiceDataPanel.add(new JLabel("Invoice Number "));
        invNubmerLable = new JLabel();
        invNubmerLable.setText("");
        invoiceDataPanel.add(invNubmerLable);

        invoiceDataPanel.add(new JLabel("Customer Name"));
        invoiceDateTF = new JTextField(20);

        invoiceDataPanel.add(invoiceDateTF);
        invoiceDataPanel.add(new JLabel("Invoice Date"));
        customerNameTF = new JTextField(20);
        invoiceDataPanel.add(customerNameTF);

        invoiceDataPanel.add(new JLabel("Invoice Total"));
        invTotalLable =new JLabel("");
        invoiceDataPanel.add(invTotalLable);
        rightSidePanel.add(invoiceDataPanel);


        invoiceItemsPanel = new JPanel();
        invoiceItemsPanel.setVisible(true);
        titledBorder =  BorderFactory.createTitledBorder("Invoice Items");
        invoiceItemsPanel.setBorder(titledBorder);
        invoiceItemsPanel.setLayout(new FlowLayout());
        invItemsTable = new JTable(itemData,itemTableBar);
        invItemsTable.getSelectionModel().addListSelectionListener(handler);
        models.add(invItemsTable.getSelectionModel());

        invoiceItemsPanel.add(new JScrollPane(invItemsTable));

        rightSidePanel.add(invoiceItemsPanel);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setVisible(true);
        savebtn=new JButton("Save");
        savebtn.setActionCommand("saveBTN");
        savebtn.addActionListener(handler);
        buttonsPanel.add(savebtn);

        cancelbtn = new JButton("Cancel");
        cancelbtn.setActionCommand("cancelBTN");
        cancelbtn.addActionListener(handler);
        buttonsPanel.add(cancelbtn);
        rightSidePanel.add(buttonsPanel);

        add(rightSidePanel);

    }

    public int getNextInvoiceNumber() {
        int n = 0;

        for (InvoiceHeader invoice : getInvHeaderArray()) {
            if (invoice.getInvNumber() > n) {
                n = invoice.getInvNumber();
            }
        }
        return ++n;
    }

    public JLabel getInvNubmerLable() {
        return invNubmerLable;
    }

    public JTextField getInvoiceDateTF() {
        return invoiceDateTF;
    }

    public JTextField getCustomerNameTF() {
        return customerNameTF;
    }

    public JLabel getInvTotalLable() {
        return invTotalLable;
    }

    public static void main(String[] args) {

        new SIGMainFrame().setVisible(true);

    }
}
