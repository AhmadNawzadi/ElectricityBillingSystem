package electricity.system.auth;

import electricity.system.Database;
import electricity.system.Functions;
import electricity.system.admin.AddCustomer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {

    JButton signInButton, cancelButton, signUpButton;
    JTextField emailField, passwordField;
    Choice loginAs;
    Database db = new Database();
    Login(){
        setTitle("Login");
        setSize(700, 350);
        setLocation(300,100);
        setLayout(null);
        Functions functions = new Functions();

        JPanel panel = new JPanel();
        panel.setSize(400, 350);
        panel.setBounds(0, 0, 400, 350);
        panel.setBackground(Color.blue);
        panel.setLayout(null);
        add(panel);

        JLabel login = functions.createTitleLabel("Login to your account", 100, 15);
        panel.add(login);

        JLabel email = functions.createLabel("Email", 50, 70);
        JLabel password = functions.createLabel("Password", 50, 120);
        JLabel loginAsLabel = functions.createLabel("Login As", 50, 170);

        panel.add(email);
        panel.add(password);
        panel.add(loginAsLabel);

        emailField = functions.createTextField(200, 70);
        passwordField = functions.createTextField(200, 120);
        loginAs = functions.addChoice(200, 170, "Admin", "User");

        panel.add(emailField);
        panel.add(passwordField);
        panel.add(loginAs);


        signInButton = functions.createButton("Sign In", 50,230, 80);
        cancelButton = functions.createButton("Cancel", 160,230, 80);
        signUpButton = functions.createButton("Sign Up", 270,230, 80);

        signInButton.addActionListener(this);
        cancelButton.addActionListener(this);
        signUpButton.addActionListener(this);

        panel.add(signInButton);
        panel.add(cancelButton);
        panel.add(signUpButton);

        String imagePath = "icons/login.jpg";
        JLabel imageLabel = functions.addImage(imagePath, 420,40, 250,250);
        add(imageLabel);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancelButton){
            setVisible(false);
        }
        else if(e.getSource() == signUpButton){
            setVisible(false);
            new AddCustomer();
        }
        else {
            String email = emailField.getText();
            String password = passwordField.getText();
            String choice = loginAs.getSelectedItem();
            String query = null;

            if(choice.equals("Admin")){
                query = "SELECT * FROM admin WHERE email= ? AND password = ?";
            }
            else if(choice.equals("User")) {
                query = "SELECT * FROM customer WHERE email= ? AND password = ?";
            }

            try {
                PreparedStatement statement = db.connection.prepareStatement(query);
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()) {
                    setVisible(false);
                    String accType = loginAs.getSelectedItem();
                    int id = resultSet.getInt("id");
                    new Operation(accType, id);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password.");
                }
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }



}

