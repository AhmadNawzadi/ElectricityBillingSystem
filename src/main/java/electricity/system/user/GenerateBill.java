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
import java.sql.SQLException;

public class GenerateBill extends JFrame{

    JButton printButton;
    Database db = new Database();

    JLabel meterNo, billNo, name, surname, address, city, postalCode, email, phone,
            mLocation, mType, phaseCode, billType, unit, period, totalBill, status;

    JLabel[] labels;
    public GenerateBill(){
        setSize(400, 730);
        setLocation(300, 100);
        setTitle("Generate Bill");
        setLayout(null);
        Functions functions = new Functions();

        JLabel genBill = functions.createLabel("Generate Bill", 50, 15);
        genBill.setForeground(Color.BLACK);
        add(genBill);

        String[] months = {"January","February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        Choice choice = functions.addChoice(200, 15, months);
        add(choice);

        int y = 100;
        for(int i = 0; i < 17; i++){
            String[] customerInfo = {"Meter No.", "Bill No.",  "Name", "Surname", "Address",
                    "City", "Postal Code", "Email", "Phone","Meter Location",
                    "Meter Type", "Phase Code", "Bill Type", "Period",
                    "Unit Consumed", "Total Bill", "Status"};
            JLabel label = functions.createLabel(customerInfo[i], 50, y);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("poppins", Font.PLAIN, 10));
            add(label);
            y += 30;
        }

        meterNo = functions.createBlackLabel("", 200, 100);
        billNo = functions.createBlackLabel("", 200, 130);
        name = functions.createBlackLabel("", 200, 160);
        surname = functions.createBlackLabel("", 200, 190);
        address = functions.createBlackLabel("", 200, 220);
        city = functions.createBlackLabel("", 200, 250);
        postalCode = functions.createBlackLabel("", 200, 280);
        email = functions.createBlackLabel("", 200, 310);
        phone = functions.createBlackLabel("", 200, 340);
        mLocation = functions.createBlackLabel("", 200, 370);
        mType = functions.createBlackLabel("", 200, 400);
        phaseCode = functions.createBlackLabel("", 200, 430);
        billType = functions.createBlackLabel("", 200, 460);
        unit = functions.createBlackLabel("", 200, 490);
        period = functions.createBlackLabel("", 200, 520);
        totalBill = functions.createBlackLabel("", 200, 550);
        status = functions.createBlackLabel("", 200, 580);


        JLabel monthBill = functions.createLabel("", 50, 45);
        monthBill.setForeground(Color.BLACK);
        add(monthBill);

        choice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    String query = "SELECT * from customer c join meter m on c.id = m.customer_id join " +
                            "bill b on m.customer_id = b.customer_id where b.month = ?";
                    var st = db.connection.prepareStatement(query);
                    st.setString(1, choice.getSelectedItem());
                    ResultSet result = st.executeQuery();
                    System.out.println("TEST CHOICE" + choice.getSelectedItem());

                    meterNo.setText("");
                    billNo.setText("");
                    name.setText("");
                    surname.setText("");
                    address.setText("");
                    city.setText("");
                    postalCode.setText("");
                    email.setText("");
                    phone.setText("");
                    mLocation.setText("");
                    mType.setText("");
                    phaseCode.setText("");
                    billType.setText("");
                    unit.setText("");
                    period.setText("");
                    totalBill.setText("");
                    status.setText("");
                    monthBill.setText("");

                    if(result.next()){
                        meterNo.setText(result.getString("meter_no"));
                        billNo.setText(result.getString("id"));
                        name.setText(result.getString("name"));
                        surname.setText(result.getString("surname"));
                        address.setText(result.getString("address"));
                        city.setText(result.getString("city"));
                        postalCode.setText(result.getString("postal_code"));
                        email.setText(result.getString("email"));
                        phone.setText(result.getString("phone"));
                        mLocation.setText(result.getString("location"));
                        mType.setText(result.getString("type"));
                        phaseCode.setText(result.getString("phase_code"));
                        billType.setText(result.getString("type"));
                        unit.setText(result.getString("unit"));
                        period.setText(result.getString("month"));
                        totalBill.setText(result.getString("total_bill"));
                        status.setText(result.getString("status"));

                        monthBill.setText("Month : %s ".formatted(choice.getSelectedItem()));

                    }
                }catch (SQLException E){
                    E.printStackTrace();
                }
            }
        });

        add(meterNo);
        add(billNo);
        add(name);
        add(surname);
        add(address);
        add(city);
        add(postalCode);
        add(email);
        add(phone);
        add(mLocation);
        add(mType);
        add(phaseCode);
        add(billType);
        add(unit);
        add(period);
        add(totalBill);
        add(status);

        setVisible(true);
    }

}
