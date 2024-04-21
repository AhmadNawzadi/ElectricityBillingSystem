package electricity.system.admin;

import electricity.system.Database;
import electricity.system.Functions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCustomer extends JFrame implements ActionListener {

    public static long  meterNumber = 23456789;
    JButton next, cancel;
    JTextField name, surname, address, city, postal_code, email, phone, password, accType;
    long meterNoLong;
    Choice createAccountAs;
    JLabel meterNoLabel;
    Database db = new Database();

    public AddCustomer(){

        setSize(750, 730);
        setLocation(300, 100);
        setTitle("Add New Customer");
        setLayout(null);
        Functions functions = new Functions();

        String boyImageUrl = "icons/boy.png";
        JLabel image = functions.addImage(boyImageUrl, 0,150, 300, 300);
        add(image);

        JPanel panel = new JPanel();
        panel.setSize(600, 700);
        panel.setBounds(300, 0, 600, 700);
        panel.setBackground(Color.blue);
        panel.setLayout(null);
        add(panel);

        //ALL LABELS
        JLabel newCustomer = functions.createTitleLabel("New Customer", 150, 15);
        panel.add(newCustomer);

        int y = 100;
        for(int i = 0; i < 9; i++){
            String[] customerInfo = {"Create Acc. As", "Name", "Surname", "Address", "City", "Postal Code", "Email", "Phone", "Password"};
            JLabel name = functions.createLabel(customerInfo[i], 50, y);
            panel.add(name);
            y += 50;
        }

        createAccountAs = functions.addChoice(200,100,  "User", "Admin");
        name = functions.createTextField(200, 150);
        surname = functions.createTextField(200, 200);
        address = functions.createTextField(200, 250);
        city = functions.createTextField(200, 300);
        postal_code = functions.createTextField(200, 350);
        email = functions.createTextField(200, 400);
        phone = functions.createTextField(200, 450);
        password = functions.createTextField(200, 500);

        panel.add(createAccountAs);
        panel.add(name);
        panel.add(surname);
        panel.add(address);
        panel.add(city);
        panel.add(postal_code);
        panel.add(email);
        panel.add(phone);
        panel.add(password);

        JLabel meterNumLabel = functions.createLabel("Meter No : ", 50, 550);
        meterNoLabel = functions.createLabel("", 200, 550);

        panel.add(meterNoLabel);
        panel.add(meterNumLabel);

        //BUTTONS NEXT AND CANCEL
        next = functions.createButton("Next", 50, 600, 120);
        cancel = functions.createButton("Cancel", 250, 600, 120);

        panel.add(next);
        panel.add(cancel);

        next.addActionListener(this);
        cancel.addActionListener(this);

        try {
            var st = db.connection.prepareStatement("SELECT max(meter_no) FROM customer ");
            ResultSet result = st.executeQuery();

            while(result.next()) {
                String meterN = result.getString(1);
                meterNoLong = Long.parseLong(meterN);
                meterNoLong++;
                meterNoLabel.setText(""+ meterNoLong);
            }
        }
        catch (SQLException E){
            E.printStackTrace();
            System.out.println("PROBLEM WITH SQL");
        }

        setVisible(true);

        createAccountAs.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String choice = createAccountAs.getSelectedItem();
                if(choice.equals("Admin")){
                    meterNumLabel.setVisible(false);
                    meterNoLabel.setVisible(false);
                }else {
                    meterNumLabel.setVisible(true);
                    meterNoLabel.setVisible(true);

                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == next){
            if(createAccountAs.getSelectedItem().equals("Admin")){
                String sql = "INSERT INTO admin(name, surname, address, city, postal_code, email, phone, password, createdAt)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, CURDATE())";
                try {
                    PreparedStatement ps = db.connection.prepareStatement(sql);
                    ps.setString(1, name.getText());
                    ps.setString(2, surname.getText());
                    ps.setString(3, address.getText());
                    ps.setString(4, city.getText());
                    ps.setString(5, postal_code.getText());
                    ps.setString(6, email.getText());
                    ps.setString(7, phone.getText());
                    ps.setString(8, password.getText());

                    ps.executeUpdate();
                    setVisible(false);
                    JOptionPane.showMessageDialog(null, "Record added successfully.");
                    Thread.sleep(2000);
                    //new MeterInfo(meterNoLong);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if (createAccountAs.getSelectedItem().equals("User")) {
                String sql = "INSERT INTO customer (name, surname, address, city, postal_code, email, phone, password, created_at, meter_no)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), ?)";

                try {
                    PreparedStatement ps = db.connection.prepareStatement(sql);
                    ps.setString(1, name.getText());
                    ps.setString(2, surname.getText());
                    ps.setString(3, address.getText());
                    ps.setString(4, city.getText());
                    ps.setString(5, postal_code.getText());
                    ps.setString(6, email.getText());
                    ps.setString(7, phone.getText());
                    ps.setString(8, password.getText());
                    ps.setLong(9, meterNoLong);

                    ps.executeUpdate();
                    setVisible(false);
                    JOptionPane.showMessageDialog(null, "Record added successfully.");
                    Thread.sleep(1000);
                    new MeterInfo(meterNoLong);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (e.getSource() == cancel) {
            setVisible(false);
        }
    }
}
