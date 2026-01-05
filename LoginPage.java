import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {

    private JTextField nameField, emailField, userField;
    private JButton loginBtn;

    public LoginPage() {
        setTitle("PlacementApp - Login");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0,1,10,10));
        getContentPane().setBackground(new Color(20,25,35));

        JLabel title = new JLabel("Welcome to PlacementApp", SwingConstants.CENTER);
        title.setForeground(Color.CYAN);
        title.setFont(new Font("Segoe UI Bold", Font.BOLD, 18));
        add(title);

        nameField = createField("Name");
        emailField = createField("Email");
        userField = createField("Username");

        loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(40,180,120));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(loginBtn);

        loginBtn.addActionListener(e -> loginAction());
    }

    private JTextField createField(String placeholder){
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(250,30));
        field.setBackground(new Color(50,60,90));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(100,100,120),2,true));
        field.setToolTipText(placeholder);
        add(field);
        return field;
    }

    private void loginAction() {
        String name = nameField.getText();
        String email = emailField.getText();
        String username = userField.getText();

        if(name.isEmpty() || email.isEmpty() || username.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please fill all fields!","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            String url = "jdbc:mysql://localhost:3306/placement_db";
            String user = "root";
            String pass = "password";

            Connection conn = DriverManager.getConnection(url,user,pass);
            String sql = "INSERT INTO users(name,email,username) VALUES(?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,name);
            stmt.setString(2,email);
            stmt.setString(3,username);
            stmt.executeUpdate();
            stmt.close();
            conn.close();

            JOptionPane.showMessageDialog(this,"Login Successful!","Success",JOptionPane.INFORMATION_MESSAGE);

            new PlacementApp().setVisible(true);
            this.dispose();

        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Database Error: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}