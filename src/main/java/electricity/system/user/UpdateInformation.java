package electricity.system.user;

import electricity.system.Database;
import electricity.system.Functions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateInformation extends JFrame implements ActionListener {
    int id;
    JTextField nameField, surnameField, addressField,
            cityField, postalCodeField, emailField, phoneField;
    JButton submit, cancel;
    Database db = new Database();

    public UpdateInformation(int id){
        this.id = id;

        setTitle("Update Information");
        setSize(700, 500);
        setLocation(200, 100);
        setLayout(null);
        getContentPane().setBackground(new Color(26, 52, 111));
        setForeground(Color.white);
        Functions functions = new Functions();

        String updateImageUrl = "icons/update.jpg";
        JLabel image = functions.addImage(updateImageUrl, 500,0, 250, 500);
        add(image);

        JLabel newCustomer = functions.createTitleLabel("Update Information", 150, 15);
        add(newCustomer);

        int y = 70;
        for(int i = 0; i < 7; i++){
            String[] customerInfo = {"Name :", "Surname :", "Address :", "City :", "Postal Code :", "Email :", "Phone :"};
            JLabel label = functions.createLabel(customerInfo[i], 50, y);
            add(label);
            y += 40;
        }

        nameField = functions.createTextField(200, 70);
        surnameField = functions.createTextField(200, 110);
        //meterNoField = functions.createTextField( 200, 150);
        addressField = functions.createTextField(200, 150);
        cityField = functions.createTextField(200, 190);
        postalCodeField = functions.createTextField(200, 230);
        emailField = functions.createTextField(200, 270);
        phoneField = functions.createTextField(200, 310);
        //createdAtField = functions.createTextField(200, 390);

        add(nameField);
        add(surnameField);
        add(addressField);
        add(cityField);
        add(postalCodeField);
        add(emailField);
        add(phoneField);

        submit = functions.createButton("Submit", 50, 380, 120);
        cancel = functions.createButton("Cancel", 250, 380, 120);

        add(submit);
        add(cancel);

        submit.addActionListener(this);
        cancel.addActionListener(this);


        try {
            var st = db.connection.prepareStatement("SELECT * FROM customer WHERE id = ?");
            st.setInt(1, id);
            ResultSet result = st.executeQuery();

            while (result.next()){
                nameField.setText(result.getString("name"));
                surnameField.setText(result.getString("surname"));
                addressField.setText(result.getString("address"));
                postalCodeField.setText(result.getString("postal_code"));
                cityField.setText(result.getString("city"));
                emailField.setText(result.getString("email"));
                phoneField.setText(result.getString("phone"));
            }
            System.out.println("TEST 111");
        }
        catch (Exception e){
            System.out.println("ERROR IN SQL");
            e.printStackTrace();
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){
            String query = "UPDATE customer SET name = ?, surname = ?, address = ?, postal_code = ? " +
                    ", city = ?, email = ?, phone = ? WHERE id = ?";

            try {
                var st = db.connection.prepareStatement(query);
                st.setString(1, nameField.getText());
                st.setString(2, surnameField.getText());
                st.setString(3, addressField.getText());
                st.setString(4, postalCodeField.getText());
                st.setString(5, cityField.getText());
                st.setString(6, emailField.getText());
                st.setString(7, phoneField.getText());
                st.setInt(8, id);

                int resultSet = st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updated Successfully.");

            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == cancel) {
            setVisible(false);
        }
    }
}

