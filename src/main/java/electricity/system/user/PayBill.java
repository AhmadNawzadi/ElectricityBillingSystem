package electricity.system.user;

import electricity.system.Database;
import electricity.system.Functions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class PayBill extends JFrame implements ActionListener {

    JButton pay, cancel;
    Database db = new Database();
    int id;
    Choice choice;

    public PayBill(int id){
        this.id = id;

        setTitle("User's Bills");
        setSize(420, 400);
        setLocation(200, 100);
        setLayout(null);
        getContentPane().setBackground(new Color(26, 52, 111));
        setForeground(Color.white);

        Functions functions = new Functions();

        JLabel payBillLabel = functions.createTitleLabel("Pay Bill", 150, 15);
        add(payBillLabel);

        int y = 80;
        for(int i = 0; i < 5; i++){
            String[] customerInfo = {"Month ", "Bill No. ", "Unit", "Total Amount", "Status"};
            JLabel label = functions.createLabel(customerInfo[i], 50, y);
            add(label);
            y += 40;
        }

        String[] months = {"January","February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        choice = functions.addChoice(200, 80, months);

        JLabel billNoLabel = functions.createLabel("", 200, 120);
        JLabel unitLabel = functions.createLabel("", 200, 160);
        JLabel totalAmountLabel = functions.createLabel("", 200, 200);
        JLabel statusLabel = functions.createLabel("", 200, 240);

        add(choice);
        add(billNoLabel);
        add(unitLabel);
        add(totalAmountLabel);
        add(statusLabel);

        choice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selectedItem = e.getItem().toString();
                try {
                    String query = "SELECT * FROM bill WHERE month = ? AND customer_id = ?";
                    var st = db.connection.prepareStatement(query);
                    st.setString(1, selectedItem);
                    st.setInt(2, id);
                    ResultSet result = st.executeQuery();

                    //make empty all labels
                    billNoLabel.setText("");
                    unitLabel.setText("");
                    totalAmountLabel.setText("");
                    statusLabel.setText("");

                    while (result.next()){
                        //choice.select(1);
                        billNoLabel.setText(result.getString("id"));
                        unitLabel.setText(result.getString("unit"));
                        totalAmountLabel.setText(result.getString("total_bill"));
                        statusLabel.setText(result.getString("status"));
                    }
                }
                catch (Exception E){
                    E.printStackTrace();
                }
            }
        });

        pay = functions.createButton("Pay", 50, 300, 120);
        cancel = functions.createButton("Cancel", 250, 300, 120);

        add(pay);
        add(cancel);

        pay.addActionListener(this);
        cancel.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancel){
            new UserBills(id);
        }
        else if(e.getSource() == pay){
            String query = "UPDATE bill SET status = 'Paid' WHERE month = ?";

            try {
                var st = db.connection.prepareStatement(query);
                st.setString(1, choice.getSelectedItem());
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Bill paid successfully.");
            }
            catch (Exception E){
                E.printStackTrace();
            }
        }
    }
}
