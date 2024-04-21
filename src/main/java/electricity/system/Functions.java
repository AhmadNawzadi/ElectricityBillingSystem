package electricity.system;

import javax.swing.*;
import java.awt.*;


public class Functions extends JFrame {

    int fieldWidth = 180;
    int fieldHeight = 30;
    int labelWidth = 150;
    int labelHeight = 30;

    public JButton createButton(String buttonName, int x, int y, int width){
        JButton button = new JButton(buttonName);
        button.setBounds(x, y, width, 40);

        return button;
    }

    public JTextField createTextField(int x, int y){
        JTextField textField = new JTextField();
        textField.setBounds(x, y, fieldWidth, fieldHeight);

        return textField;
    }

    public JLabel createLabel(String labelName, int x, int y){
        JLabel label = new JLabel(labelName);
        label.setBounds(x,y, labelWidth, labelHeight);
        label.setFont(new Font("poppins", Font.BOLD, 12));
        label.setForeground(Color.WHITE);

        return label;
    }

    public JLabel createBlackLabel(String name, int x, int y){
        JLabel label = new JLabel(name);
        label.setBounds(x,y, labelWidth, labelHeight);
        label.setFont(new Font("poppins", Font.PLAIN, 10));
        label.setForeground(Color.BLACK);

        return label;
    }

    public JLabel createTitleLabel(String name, int x, int y){
        JLabel label = new JLabel(name);
        label.setBounds(x,y, 300, fieldHeight);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("serif", Font.BOLD, 20));

        return label;
    }

    public Choice addChoice(int x, int y, String... args){
        Choice choice = new Choice();
        choice.setBounds (x, y, fieldWidth, fieldHeight);
        for(String s : args){
            choice.addItem(s);
        }
        return choice;
    }

    public Choice addChoice(int x, int y){
        Choice choice = new Choice();
        choice.setBounds (x, y, fieldWidth, fieldHeight);

        return choice;
    }

    public JLabel addImage(String url, int x, int y, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource(url));
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon imageIcon2 = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon2);
        imageLabel.setBounds(x, y, width, height);

        return imageLabel;
    }

    public JMenuItem addMenuItem(String name, JMenu menu, String iconUrl){
        JMenuItem menuItem = new JMenuItem(name);
        menu.add(menuItem);
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource(iconUrl));
        Image image = imageIcon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT);
        ImageIcon icon2 = new ImageIcon(image);
        menuItem.setIcon(icon2);

        return menuItem;
    }

}
