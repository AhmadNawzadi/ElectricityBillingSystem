package electricity.system.user;

import electricity.system.Database;
import electricity.system.Functions;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class ViewInformation extends JFrame{
    int id;

    public ViewInformation(int id){
        this.id = id;

        setTitle("View Information");
        setSize(700, 500);
        setLocation(200, 100);
        setLayout(null);
        getContentPane().setBackground(new Color(26, 52, 111));
        setForeground(Color.white);
        Functions functions = new Functions();

        String infoImageUrl = "icons/in.jpeg";
        JLabel image = functions.addImage(infoImageUrl, 450,0, 250, 500);
        add(image);

        JLabel newCustomer = functions.createTitleLabel("Information", 150, 15);
        add(newCustomer);

        int y = 70;
        for(int i = 0; i < 9; i++){
            String[] customerInfo = {"Name :", "Surname :", "Meter No. :", "Address :", "City :", "Postal Code :", "Email :", "Phone :", "Create Date :"};
            JLabel label = functions.createLabel(customerInfo[i], 50, y);
            add(label);
            y += 40;
        }

        JLabel nameLabel = functions.createLabel("", 200, 70);
        JLabel surnameLabel = functions.createLabel("", 200, 110);
        JLabel meterNoLabel = functions.createLabel("", 200, 150);
        JLabel addressLabel = functions.createLabel("", 200, 190);
        JLabel cityLabel = functions.createLabel("", 200, 230);
        JLabel postalCodeLabel = functions.createLabel("", 200, 270);
        JLabel emailLabel = functions.createLabel("", 200, 310);
        JLabel phoneLabel = functions.createLabel("", 200, 350);
        JLabel createdAtLabel = functions.createLabel("", 200, 390);

        add(nameLabel);
        add(surnameLabel);
        add(meterNoLabel);
        add(addressLabel);
        add(cityLabel);
        add(postalCodeLabel);
        add(emailLabel);
        add(phoneLabel);
        add(createdAtLabel);


        try {
            Database db = new Database();
            var st = db.connection.prepareStatement("SELECT * FROM customer WHERE id = ?");
            st.setInt(1, id);
            ResultSet result = st.executeQuery();

            while (result.next()){
                nameLabel.setText(result.getString("name"));
                surnameLabel.setText(result.getString("surname"));
                meterNoLabel.setText(result.getString("meter_no"));
                addressLabel.setText(result.getString("address"));
                postalCodeLabel.setText(result.getString("postal_code"));
                cityLabel.setText(result.getString("city"));
                emailLabel.setText(result.getString("email"));
                phoneLabel.setText(result.getString("phone"));
                createdAtLabel.setText(result.getString("created_at"));
            }
            System.out.println("TEST 111");
        }
        catch (Exception e){
            System.out.println("ERROR IN SQL");
            e.printStackTrace();
        }

        setVisible(true);
    }

}
