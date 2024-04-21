package electricity.system.admin;

import electricity.system.Database;
import electricity.system.Functions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class CalculateBill extends JFrame implements ActionListener{
    Choice createAccountAs, month;
    JLabel nameLabel, addressLabel;
    JButton submitButton, cancelButton;
    JTextField KWConsumed;
    Database db = new Database();

    final static double KW_PRICE = 0.5;

    int customerId;
    public CalculateBill(){

        setTitle("Calculate Electricity Bill");
        setSize(700, 450);
        setLocation(200, 100);
        setLayout(null);
        getContentPane().setBackground(new Color(26, 52, 111));
        setForeground(Color.white);
        Functions functions = new Functions();

        JPanel panel = new JPanel();
        panel.setSize(430, 700);
        panel.setBounds(0, 0, 430, 700);
        panel.setBackground(Color.blue);
        panel.setLayout(null);
        add(panel);

        String budgetImageUrl = "icons/budget.png";
        JLabel image = functions.addImage(budgetImageUrl, 450,100, 220, 250);
        add(image);

        JLabel newCustomer = functions.createTitleLabel("Calculate Electricity Bill", 70, 15);
        panel.add(newCustomer);

        int y = 80;
        for(int i = 0; i < 5; i++){
            String[] customerInfo = {"Meter No.", "Name", "Address", "KW Consumed", "Month"};
            JLabel name = functions.createLabel(customerInfo[i], 50, y);
            panel.add(name);
            y += 50;
        }

        String[] months = {"January","February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        nameLabel = functions.createLabel("", 200, 130);
        addressLabel = functions.createLabel("",200, 180);
        KWConsumed = functions.createTextField(200, 230);
        month = functions.addChoice(200, 280, months);

        panel.add(nameLabel);
        panel.add(addressLabel);
        panel.add(KWConsumed);
        panel.add(month);

        submitButton = functions.createButton("Submit", 40, 350, 100);
        cancelButton =  functions.createButton("Cancel", 200, 350, 100);

        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);

        panel.add(submitButton);
        panel.add(cancelButton);

        try {
            var statement = db.connection.prepareStatement("SELECT meter_no FROM customer");
            ResultSet resultSet = statement.executeQuery();
            createAccountAs = functions.addChoice(200,80);

            while(resultSet.next()){
                String item = resultSet.getString("meter_no");
                createAccountAs.addItem(item);
            }
            panel.add(createAccountAs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        createAccountAs.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String query = "SELECT id, name, address FROM customer WHERE meter_no = ?";

                try {
                    var getInfo = db.connection.prepareStatement(query);
                    getInfo.setString(1, createAccountAs.getSelectedItem());
                    ResultSet result = getInfo.executeQuery();

                    while(result.next()) {
                        customerId = result.getInt("id");
                        nameLabel.setText(result.getString("name"));
                        addressLabel.setText(result.getString("address"));
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submitButton){
            try{
                String query = "INSERT INTO bill(meter_no, month, unit, total_bill, status, customer_id)" +
                        " VALUES(?, ?, ?, ?, ?, ?)";
                var st = db.connection.prepareStatement(query);
                st.setString(1, createAccountAs.getSelectedItem());
                st.setString(2, month.getSelectedItem());
                st.setString(3, KWConsumed.getText());
                st.setDouble(4,  (Double.parseDouble(KWConsumed.getText())) * KW_PRICE );
                st.setString(5, "Unpaid");
                st.setInt(6, customerId);
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Bill added successfully.");
            }
            catch (Exception E){
                E.printStackTrace();
            }
        }
        else{
            setVisible(false);
        }
    }
}
