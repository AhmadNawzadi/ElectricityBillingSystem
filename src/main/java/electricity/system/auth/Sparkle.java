package electricity.system.auth;

import electricity.system.Functions;

import javax.swing.*;
import java.awt.*;

public class Sparkle extends JFrame {

   public Sparkle(){

       //String url = "/src/icons/p.png";
       setTitle("Electricity Billing System");
       setSize(600, 400);
       setLocation(300,100);

       Functions functions = new Functions();

       String url = "icons/p.png";

       JLabel imageLabel = functions.addImage(url, 0,0, 600, 400);

//       ImageIcon icon = new ImageIcon(url);
//       Image image1 = icon.getImage().getScaledInstance(600,400,Image.SCALE_DEFAULT);
//       ImageIcon icon2 = new ImageIcon( image1);
//       JLabel imageLabel = new JLabel(icon2);
       //add(imageLabel);

//       ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/bill.png"));
//       Image imageOne = imageIcon.getImage().getScaledInstance(600,400, Image.SCALE_DEFAULT);
//       ImageIcon imageIcon2 = new ImageIcon( imageOne);
//       JLabel imageLabel = new JLabel(imageIcon2);
//       add(imageLabel);

       add(imageLabel);

       setVisible(true);

       try {
           Thread.sleep(1000);
           setVisible(false);
           new Login();
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }

   }
}
