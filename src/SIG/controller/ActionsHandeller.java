package SIG.controller;

import SIG.model.InvHeaderTableMod;
import SIG.view.SIGMainFrame;
import SIG.view.InvoiceDialog;
import SIG.view.LinesDialog;

import SIG.model.InvLineTableMod;
import SIG.model.InvoiceHeader;
import SIG.model.InvoiceLine;
//import SIG.model.FileOperations;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionsHandeller implements ActionListener, ListSelectionListener {


    private InvoiceDialog invDialog;
    private LinesDialog invLineDialog;

    private SIGMainFrame frame;

    public ActionsHandeller(SIGMainFrame frame){
        this.frame=frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    switch (actionCommand){
        case "loadFile":
            loadFile();
            break;
        case"saveFile":
            saveFile();
            break;
        case"createInvBTN":
            createNewInvoice();
            break;
        case"delInvBTN":
            deleteInvoice();
            break;
        case"saveBTN":
            saveItemsData();
            break;
        case"cancelBTN":
            cancelItemsData();
            break;
        case "CancelCreationInv":
            CancelCreationInv();
            break;
        case "createInvoice":
            createInvoice();
            break;
        case "createLineOK":
            createLineOK();
            break;
        case "cancelCreationLine":
            cancelCreationLine();
            break;
    }
}


    @Override
    public void valueChanged(ListSelectionEvent e) {
        int sellectedRow = frame.getInvTable().getSelectedRow();
        if(sellectedRow !=-1){
            InvoiceHeader sellectedInv =frame.getInvHeaderArray().get(sellectedRow);
            //Converting the Number from Integer to String
            frame.getInvNubmerLable().setText(""+sellectedInv.getInvNumber());
            frame.getInvoiceDateTF().setText(sellectedInv.getInvDate());
            frame.getCustomerNameTF().setText(sellectedInv.getCustomerName());
            //Converting the total from double to String
            frame.getInvTotalLable().setText(""+sellectedInv.getInvTotal());
            InvLineTableMod invLineTableMod=new InvLineTableMod(sellectedInv.getLines());
            frame.getInvItemsTable().setModel(invLineTableMod);
            invLineTableMod.fireTableDataChanged();
        }
    }
    private void loadFile() {
        try{
            JFileChooser fileChooser= new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if(result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fileChooser.getSelectedFile();
                String hPath = headerFile.getAbsolutePath();
                Path hp = Paths.get(hPath);
                List<String> hLines= Files.lines(hp).collect(Collectors.toList());
                ArrayList<InvoiceHeader> invHeaderArray = new ArrayList<>();
                for(String hLine : hLines) {
                    String[] invContent = hLine.split(",");

                    int id = Integer.parseInt(invContent[0]);
                    InvoiceHeader invHead = new InvoiceHeader(id,invContent[1],invContent[2]);
                    invHeaderArray.add(invHead);
                }
                result = fileChooser.showOpenDialog(frame);
                if(result == JFileChooser.APPROVE_OPTION){
                    String lPath = fileChooser.getSelectedFile().getAbsolutePath();
                    Path lp = Paths.get(lPath);
                    List<String> lLines = Files.lines(lp).collect(Collectors.toList());
                    for(String lLine : lLines){
                        String[] itmContent = lLine.split(",");
                        int itmID = Integer.parseInt(itmContent[0]);
                        double itmPrice = Double.parseDouble(itmContent[2]);
                        int itmCount = Integer.parseInt(itmContent[3]);
                        InvoiceHeader header = getInvoiceHeaderUsingID(invHeaderArray,itmID);
                        InvoiceLine invLine = new InvoiceLine(itmContent[1],itmPrice,itmCount,header);
                        header.getLines().add(invLine);
                    }
                    frame.setInvHeaderArray(invHeaderArray);
                }
            }}
        catch(IOException e){
            e.printStackTrace();
        }

    }

    private void saveFile() {
        ArrayList<InvoiceHeader> invoiceHeaders = frame.getInvHeaderArray();
        String invHeaders = "";
        String invLines = "";

        for (InvoiceHeader invoice : invoiceHeaders) {
            String invHeadersFormat = invoice.getAsCSV();
            invHeaders += invHeadersFormat;
            invHeaders += "\n";

            for (InvoiceLine inv : invoice.getLines()) {
                String linessFormat = inv.getAsCSV();
                invLines += linessFormat;
                invLines += "\n";
            }
        }
        try {
            JFileChooser fileChooser = new JFileChooser();
            int fileSaver = fileChooser.showSaveDialog(frame);
            if (fileSaver == JFileChooser.APPROVE_OPTION) {
                File invFile = fileChooser.getSelectedFile();
                FileWriter invFileWriter = new FileWriter(invFile);
                invFileWriter.write(invHeaders);
                invFileWriter.flush();
                invFileWriter.close();

                fileSaver = fileChooser.showSaveDialog(frame);
            }
            if (fileSaver == JFileChooser.APPROVE_OPTION) {
                File lineFile = fileChooser.getSelectedFile();
                FileWriter lineFileWriter = new FileWriter(lineFile);
                lineFileWriter.write(invLines);
                lineFileWriter.flush();
                lineFileWriter.close();
            }
        } catch (Exception e) {
        }

    }


    private void createNewInvoice() {
        invDialog = new InvoiceDialog(frame);
        invDialog.setVisible(true);
    }


    private void deleteInvoice() {
        int selectionRow = frame.getInvTable().getSelectedRow();
        if (selectionRow != -1) {
            frame.getInvHeaderArray().remove(selectionRow);
            frame.getInvHeaderTableMod().fireTableDataChanged();
        }
    }

    private void createInvoice() {
        String dateInv = invDialog.getInvDateField().getText();
        String customerName = invDialog.getCustNameField().getText();
        int numberInv = frame.getNextInvoiceNumber();
        try {
            String[] dateSplits = dateInv.split("-");
            if (dateSplits.length < 3) {

                JOptionPane.showMessageDialog(
                        frame, "Please enter a valid Date ", "Error", JOptionPane.ERROR_MESSAGE);

            } else {
                int dayDate = Integer.parseInt(dateSplits[0]);
                int monthDate = Integer.parseInt(dateSplits[1]);
                int yearDate = Integer.parseInt(dateSplits[2]);
                // Validate the Date and putting our Constrains
                if (dayDate > 31 || monthDate > 12 || yearDate > 3000||yearDate < 1000) {
                    JOptionPane.showMessageDialog(
                            frame, "Please enter a valid Date ", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    InvoiceHeader invoice = new InvoiceHeader(numberInv, customerName, dateInv);
                    ArrayList<InvoiceHeader> invh = frame.getInvHeaderArray();
                    invh.add(invoice);
                    frame.setInvHeaderArray(invh);
                    frame.getInvHeaderTableMod().fireTableDataChanged();
                    invDialog.setVisible(false);
                    invDialog.dispose();
                    invDialog = null;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    frame, "Please enter a valid Date ", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void CancelCreationInv() {
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog = null;
    }

    private void saveItemsData() {
        invLineDialog = new LinesDialog(frame);
        invLineDialog.setVisible(true);
    }

    private void cancelItemsData() {
        int selectedRow = frame.getInvItemsTable().getSelectedRow();
        if (selectedRow != -1) {
            System.out.println(selectedRow);
            InvLineTableMod linesTableModel = (InvLineTableMod) frame.getInvItemsTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            frame.getInvHeaderTableMod().fireTableDataChanged();
        }

    }

    private void createLineOK() {
        String newItem = invLineDialog.getItemNameField().getText();
        String count = invLineDialog.getItemCountField().getText();
        String nwPrice = invLineDialog.getItemPriceField().getText();
        int counts = Integer.parseInt(count);
        double prices = Double.parseDouble(nwPrice);
        int selectedInvoice = frame.getInvTable().getSelectedRow();
        if (selectedInvoice != -1) {

            InvoiceHeader newInvoice = frame.getInvHeaderArray().get(selectedInvoice);
            InvoiceLine newLine = new InvoiceLine(newItem, prices, counts, newInvoice);
            newInvoice.getLines().add(newLine);
            InvLineTableMod linesTableModel = (InvLineTableMod) frame.getInvItemsTable().getModel();
            linesTableModel.fireTableDataChanged();
            frame.getInvHeaderTableMod().fireTableDataChanged();
        }
        invLineDialog.setVisible(false);
        invLineDialog.dispose();
        invLineDialog = null;

    }

    private void cancelCreationLine() {
        invLineDialog.setVisible(false);
        invLineDialog.dispose();
        invLineDialog = null;
    }


    private InvoiceHeader getInvoiceHeaderUsingID(ArrayList<InvoiceHeader> invoices,int id){
        for(InvoiceHeader invoice : invoices){
            if(invoice.getInvNumber() == id){
                return invoice;
            }
        }
        return null;
    }

}
