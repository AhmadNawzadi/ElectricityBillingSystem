package electricity.system.admin;

import electricity.system.Database;
import electricity.system.Functions;
import electricity.system.admin.AddCustomer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MeterInfo extends JFrame implements ActionListener {

    JButton submit, cancel;
    JLabel meterNo;
    Choice location, meterType, phaseCode, billType;

    long meterNumber;

    public MeterInfo(long meterNumber){

        this.meterNumber = meterNumber;
        //FRAME SETTINGS
        setSize(750, 500);
        setLocation(300, 100);
        setTitle("Meter Information");
        setLayout(null);
        Functions functions = new Functions();

        //ADD IMAGE
        String meterImageUrl = "icons/m.png";
        JLabel image = functions.addImage(meterImageUrl, 20,100, 250, 300);
        add(image);

        JPanel panel = new JPanel();
        panel.setSize(600, 700);
        panel.setBounds(300, 0, 600, 700);
        panel.setBackground(Color.blue);
        panel.setLayout(null);
        add(panel);

        //ALL LABELS
        JLabel meterInfo = functions.createTitleLabel("Meter Information", 150, 15);
        panel.add(meterInfo);

        meterNo = functions.createLabel("%s".formatted(meterNumber), 250, 100);
        panel.add(meterNo);

        int y = 100;
        for(int i = 0; i < 5; i++){
            String[] customerInfo = {"Meter No", "Meter Location", "Meter Type", "Phase Code", "Bill Type"};
            JLabel name = functions.createLabel(customerInfo[i], 50, y);
            panel.add(name);
            y += 50;
        }

        //CHOICES
        location = functions.addChoice(200,150,"Inside", "Outside");
        meterType = functions.addChoice(200,200,"Electric Meter", "Solar meter", "Smart Meter");
        phaseCode = functions.addChoice(200, 250,"001", "002", "003");
        billType = functions.addChoice(200, 300,"Monthly", "Each Two Months");

        panel.add(location);
        panel.add(meterType);
        panel.add(phaseCode);
        panel.add(billType);


        //BUTTONS
        submit = functions.createButton("Submit", 50, 370, 120);
        cancel = functions.createButton("Cancel", 250, 370, 120);

        panel.add(submit);
        panel.add(cancel);

        submit.addActionListener(this);
        cancel.addActionListener(this);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){
            System.out.println("BUTTON CLICKED ");
            Database db = new Database();
            String query = "INSERT INTO meter (number, location, type, phase_code, bill_type, customer_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            String query1 = "select * from customer where meter_no = ?";

            try {
                var ps1 = db.connection.prepareStatement(query1);
                ps1.setString(1, meterNo.getText());
                System.out.println("THE METER NO IS :" + meterNo.getText());
                ResultSet resultSet = ps1.executeQuery();
                int id = 0;
                if(resultSet.next()){
                    id = resultSet.getInt(1);
                    System.out.println("ID IS :" + id);
                }

                var ps = db.connection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(meterNo.getText()));
                ps.setString(2, location.getSelectedItem());
                ps.setString(3, meterType.getSelectedItem());
                ps.setString(4, phaseCode.getSelectedItem());
                ps.setString(5, billType.getSelectedItem());
                ps.setInt(6, id);

                ps.executeUpdate();
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Meter info added successfully.");

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else{
            setVisible(false);
        }
    }
}
