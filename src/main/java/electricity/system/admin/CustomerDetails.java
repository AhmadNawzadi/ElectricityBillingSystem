package electricity.system.admin;

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

public class CustomerDetails extends JFrame implements ActionListener {

    JTable table;
    Choice searchByName, searchByMeterNo;
    JButton searchButton1, searchButton2, print;
    DefaultTableModel model;
    Functions functions = new Functions();

    PreparedStatement customers;

    Database db = new Database();
    Connection con = db.connection;

    public CustomerDetails(){

        setTitle("Customers' Details");
        setSize(800, 600);
        setLocation(200, 100);
        setLayout(null);
        getContentPane().setBackground(new Color(26, 52, 111));
        setForeground(Color.white);

        //LABELS
        JLabel searchName = functions.createLabel("Search by name", 30, 30);
        add(searchName);

        JLabel searchMeter = functions.createLabel("Search by Meter", 430, 30);
        add(searchMeter);

        //BUTTONS
        searchButton1 = functions.createButton("Search", 30, 70, 120);
        searchButton1.addActionListener(this);
        add(searchButton1);

        searchButton2 =  functions.createButton("Search", 430, 70, 120);
        searchButton2.addActionListener(this);
        add(searchButton2);

        print =  functions.createButton("Print", 30, 150, 70);
        print.addActionListener(this);
        add(print);

        //CREATING TABLE
        model = new DefaultTableModel();

        model.addColumn("Name");
        model.addColumn("Surname");
        model.addColumn("Meter No");
        model.addColumn("City");
        model.addColumn("Email");
        model.addColumn("Phone");

        table = new JTable(model);
        table.setBackground(Color.gray);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(0,200, 800, 400);
        add(scroll);

        try {
            var getNames = con.prepareStatement("SELECT name FROM customer");
            ResultSet names = getNames.executeQuery();

            searchByName = functions.addChoice(170,35);
            while (names.next()){
                String name = names.getString("name");
                searchByName.addItem(name);
            }
            add(searchByName);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            var getMeterNo = con.prepareStatement("SELECT meter_no FROM customer");
            ResultSet meterNos = getMeterNo.executeQuery();

            searchByMeterNo = functions.addChoice(580,35);
            while(meterNos.next()){
                String meterNo = meterNos.getString("meter_no");
                searchByMeterNo.addItem(meterNo);
            }
            add(searchByMeterNo);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            customers =  con.prepareStatement("SELECT * FROM customer");
            ResultSet result = customers.executeQuery();

            while(result.next()){
                Object[] row = {
                    result.getString(2),
                    result.getString(3),
                    result.getString(10),
                    result.getString(5),
                    result.getString(7),
                    result.getString(8)
                };
                model.addRow(row);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == searchButton1) {
           String name =  searchByName.getSelectedItem();
            try {
                var st = con.prepareStatement("SELECT * FROM customer where name = ?");
                st.setString(1, name);
                ResultSet result = st.executeQuery();
                while(result.next()){
                    Object[] row = {
                        result.getString(2),
                        result.getString(3),
                        result.getString(10),
                        result.getString(5),
                        result.getString(7),
                        result.getString(8)
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
        else if(e.getSource() == searchButton2) {
            String meter =  searchByMeterNo.getSelectedItem();
            try {
                var st = con.prepareStatement("SELECT * FROM customer where meter_no = ?");
                st.setString(1, meter);
                ResultSet result = st.executeQuery();
                while(result.next()){
                    Object[] row = {
                            result.getString(2),
                            result.getString(3),
                            result.getString(10),
                            result.getString(5),
                            result.getString(7),
                            result.getString(8)
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
        else if(e.getSource() == print){
            try {
                table.print();
            } catch (PrinterException ex) {
                throw new RuntimeException(ex);
            }
        }
    }




}

