package electricity.system.auth;

import electricity.system.Database;
import electricity.system.Functions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp extends JFrame implements ActionListener {
    Choice createAccountAs;
    JButton create, back;

    JTextField meterId, nameField, emailField, passwordField, surnameField,
            addressField, cityField, postalCodeField, phoneField, accTypeField;
    public SignUp(){
        setTitle("Sign Up");
        setSize(800, 800);
        setLayout(null);
        getContentPane().setBackground(new Color(26, 52, 111));
        setForeground(Color.white);
        Functions functions = new Functions();

        // CHOICE
        createAccountAs = functions.addChoice(50,40, "Admin", "User");
        add(createAccountAs);

        //LABELS
        JLabel accountAs = functions.createLabel("Create Acc. As", 40, 40);
        add(accountAs);

        JLabel employerLabel = functions.createLabel("Employer ID", 40, 80);
        add(employerLabel);

        JLabel meterNo = functions.createLabel("Meter No", 40, 80);
        add(meterNo);
        meterNo.setVisible(false);

        JLabel userName = functions.createLabel("UserName", 40, 120);
        add(userName);

        JLabel name = functions.createLabel("Name", 40, 160);
        add(name);

        JLabel password = functions.createLabel("Password", 40, 200);
        add(password);

        // TEXT FIELDS
        JTextField employerId =  functions.createTextField(220, 80);
        add(employerId);

        meterId =  functions.createTextField(220, 80);
        meterId.setVisible(false);
        add(meterId);

        nameField = functions.createTextField(220, 120);
        add(nameField);

        emailField = functions.createTextField(220, 160);
        add(emailField);

        passwordField = functions.createTextField(220, 200);
        add(passwordField);

        create = functions.createButton("Create", 40, 270, 150);
        create.addActionListener(this);
        add(create);

        back =  functions.createButton("Back", 250, 270, 150);
        back.addActionListener(this);
        add(back);

        String url = "icons/signup.png";
        JLabel imgLabel = functions.addImage(url, 480, 20, 300, 300);
        add(imgLabel);
        setVisible(true);

        createAccountAs.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String choice = createAccountAs.getSelectedItem();
                if(choice.equals("Admin")){
                    meterNo.setVisible(false);
                    meterId.setVisible(false);
                    employerLabel.setVisible(true);
                    employerId.setVisible(true);
                }else {
                    meterNo.setVisible(true);
                    meterId.setVisible(true);
                    employerLabel.setVisible(false);
                    employerId.setVisible(false);
                }
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            setVisible(false);
            new Login();
        }
        else{
            Database database = new Database();

            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String surname = surnameField.getText();
            String address = addressField.getText();
            String city = cityField.getText();
            String postalCode = postalCodeField.getText();
            String phone = phoneField.getText();
            String accType = accTypeField.getText();
            String meterNo = meterId.getText();


            String sql = "INSERT INTO customer ('name', 'surname', 'address', 'city', 'postal_code', 'email', 'phone', 'password', 'acc_type', 'date', 'meter_no')" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), ?)";

            try {
                PreparedStatement ps = database.connection.prepareStatement(sql);

                ps.setString(1, name);
                ps.setString(2, surname);
                ps.setString(3, address);
                ps.setString(4, city);
                ps.setString(5, postalCode);
                ps.setString(6, email);
                ps.setString(7, phone);
                ps.setString(8, password);
                ps.setString(9, accType);
                ps.setString(10, meterNo);
                ps.execute();

                JOptionPane.showMessageDialog(null, "Added successfully!");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
