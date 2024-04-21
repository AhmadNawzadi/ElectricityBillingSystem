package electricity.system.user;

import electricity.system.Database;
import electricity.system.Functions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserBills extends JFrame implements ActionListener {

    int id;
    JTable table;
    JTextField searchByBillNo;
    JButton searchButton, print, payBill;
    DefaultTableModel model;

    Database db = new Database();
    Connection con = db.connection;

    public UserBills(int id) {

        this.id = id;

        setTitle("User's Bills");
        setSize(800, 600);
        setLocation(200, 100);
        setLayout(null);
        getContentPane().setBackground(new Color(26, 52, 111));
        setForeground(Color.white);
        Functions functions = new Functions();

        //LABELS
        JLabel bills = functions.createTitleLabel("Bills", 350, 15);
        JLabel searchBillLabel = functions.createLabel("Search by bill's number", 30, 70);

        add(bills);
        add(searchBillLabel);

        searchByBillNo = functions.createTextField(200,70);
        add(searchByBillNo);

        //BUTTONS
        searchButton = functions.createButton("Search", 30, 110, 70);
        payBill = functions.createButton("Pay Bill", 700, 70, 70);
        print = functions.createButton("Print", 700, 110, 70);

        searchButton.addActionListener(this);
        payBill.addActionListener(this);
        print.addActionListener(this);

        add(searchButton);
        add(print);
        add(payBill);

        //CREATING TABLE
        model = new DefaultTableModel();

        model.addColumn("Bill's No.");
        model.addColumn("Month");
        model.addColumn("Unit");
        model.addColumn("Total bill");
        model.addColumn("Status");

        table = new JTable(model);
        table.setBackground(Color.gray);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(0, 200, 800, 400);
        add(scroll);

        try {
            var st = con.prepareStatement("SELECT * FROM bill where customer_id = ?");
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                Object[] row = {
                    result.getString("id"),
                    result.getString("month"),
                    result.getString("unit"),
                    result.getString("total_bill"),
                    result.getString("status")
                };
                model.addRow(row);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == searchButton) {
            try {
                var st = con.prepareStatement("SELECT * FROM bill WHERE id = ?");

                st.setString(1, searchByBillNo.getText());
                ResultSet result = st.executeQuery();

                while (result.next()) {
                    Object[] row = {
                        result.getInt("id"),
                        result.getString("month"),
                        result.getString("unit"),
                        result.getString("total_bill"),
                        result.getString("status"),
                    };
                    // Clear previous table data
                    model.setRowCount(0);
                    model.addRow(row);
                }
            }
            catch (Exception E) {
                E.printStackTrace();
            }
        }
        else if (e.getSource() == payBill) {
            setVisible(false);
            new PayBill(id);
        }
        else if(e.getSource() == print){
            try {
                table.print();
            } catch (PrinterException ex) {
                throw new RuntimeException(ex);
            }
        }
    }




}


