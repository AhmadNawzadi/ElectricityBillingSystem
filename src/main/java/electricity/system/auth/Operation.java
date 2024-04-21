package electricity.system.auth;

import electricity.system.Functions;
import electricity.system.admin.AddCustomer;
import electricity.system.admin.BillDetails;
import electricity.system.admin.CalculateBill;
import electricity.system.admin.CustomerDetails;
import electricity.system.user.GenerateBill;
import electricity.system.user.UpdateInformation;
import electricity.system.user.UserBills;
import electricity.system.user.ViewInformation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Operation extends JFrame implements ActionListener {

    private String accType;
    private int id;
    public Operation(String accTpye, int id){
        this.accType = accTpye;
        this.id = id;

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Functions f = new Functions();

        String bgImageUrl = "icons/electricity.png";
        JLabel label = f.addImage(bgImageUrl,0,0,1800, 900);
        add(label);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //SET ICONS
        String newCusIcon = "icons/newcustomer.png";
        String customerDetailIcon = "icons/information.png";
        String depositDetailIcon = "icons/depositDetails.png";
        String calIcon = "icons/calculatorbills.png";
        String updateInfoIcon = "icons/update.jpg";
        String viewInfoIcon = "icons/detail.png";
        String billDetailIcon = "icons/billDetails.png";
        String genBillIcon = "icons/bill.png";
        String notepadIcon = "icons/notepad.png";
        String calculatorIcon = "icons/calculator.png";
        String exitIcon = "icons/exit.png";

        JMenu menu = new JMenu("Menu");
        JMenu info = new JMenu("Information");
        JMenu bill = new JMenu("Bill");
        JMenu utility = new JMenu("Utility");
        JMenu exit = new JMenu("Exit");

        JMenuItem new_customer = f.addMenuItem("New Customer", menu, newCusIcon);
        JMenuItem customerDetails = f.addMenuItem("Customer Details", menu, customerDetailIcon);
        JMenuItem billsDetails = f.addMenuItem("Bills' Details", menu, depositDetailIcon);
        JMenuItem calculateBill = f.addMenuItem("Calculate Bill", menu, calIcon);
        JMenuItem updateInfo = f.addMenuItem("Update Information", info, updateInfoIcon);
        JMenuItem viewInfo = f.addMenuItem("View Information", info, viewInfoIcon);
        JMenuItem billDetails = f.addMenuItem("Bill Details", bill, billDetailIcon);
        JMenuItem billGenerator = f.addMenuItem("Bill Generator", bill, genBillIcon);
        JMenuItem notepad = f.addMenuItem("Notepad", utility, notepadIcon);
        JMenuItem calculator = f.addMenuItem("Calculator", utility, calculatorIcon);
        JMenuItem exit1 = f.addMenuItem("Exit", exit, exitIcon);

        new_customer.addActionListener(this);
        customerDetails.addActionListener(this);
        billsDetails.addActionListener(this);
        calculateBill.addActionListener(this);
        updateInfo.addActionListener(this);
        viewInfo.addActionListener(this);
        billDetails.addActionListener(this);
        billGenerator.addActionListener(this);
        notepad.addActionListener(this);
        calculator.addActionListener(this);
        exit1.addActionListener(this);

        if(accTpye.equals("Admin")){
            menuBar.add(menu);
        }
        else if (accTpye.equals("User")) {
            menuBar.add(bill);
            menuBar.add(info);
        }
        menuBar.add(utility);
        menuBar.add(exit);

        setLayout(new FlowLayout());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if(action.equals("Customer Details")){
            new CustomerDetails();
        }
        else if (action.equals("New Customer")) {
            new AddCustomer();
        }
        else if (action.equals("Bills' Details")) {
            new BillDetails();
        }
        else if (action.equals("Calculate Bill")) {
            new CalculateBill();
        }
        else if (action.equals("View Information")) {
            new ViewInformation(id);
        }
        else if (action.equals("Update Information")) {
            new UpdateInformation(id);
        }
        else if (action.equals("Bill Details")) {
            new UserBills(id);
        }
        else if (action.equals("Calculator")) {
            try {
                Runtime.getRuntime().exec("open -a Calculator");
            }catch (Exception E){
                E.printStackTrace();
            }
        }
        else if (action.equals("Notepad")) {
            try {
                Runtime.getRuntime().exec("open -a TextEdit");
            }catch (Exception E){
                E.printStackTrace();
            }
        }
        else if(action.equals("Bill Generator")){
            new GenerateBill();
        }
        else if(action.equals("Exit")){
            setVisible(false);
            new Login();
        }
    }
}
