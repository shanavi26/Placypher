import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PlacementApp extends JFrame {

    private JTextField tenthField, twelfthField, cgpaField;
    private JTextField problemsField, badgesField;
    private JTextField numInternField, projectsField, certField, compField;
    private JComboBox<String> internshipBox, hackathonBox;
    private JLabel overallScoreLabel, resultLabel;

    public PlacementApp() {
        setTitle("Placement Score Calculator - Placypher");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0,2,10,10));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(20,25,35));

        // Sample fields (can expand for full GUI)
        tenthField = new JTextField(); add(makeLabel("10th %")); add(tenthField);
        twelfthField = new JTextField(); add(makeLabel("12th %")); add(twelfthField);
        cgpaField = new JTextField(); add(makeLabel("CGPA")); add(cgpaField);

        problemsField = new JTextField(); add(makeLabel("Coding Problems")); add(problemsField);
        badgesField = new JTextField(); add(makeLabel("Badges")); add(badgesField);

        numInternField = new JTextField(); add(makeLabel("Number of Internships")); add(numInternField);
        internshipBox = new JComboBox<>(new String[]{"None","Startup","MNC","Govt"});
        add(makeLabel("Internship Type")); add(internshipBox);

        projectsField = new JTextField(); add(makeLabel("Projects")); add(projectsField);
        certField = new JTextField(); add(makeLabel("Certifications")); add(certField);

        hackathonBox = new JComboBox<>(new String[]{"1st Place","2nd Place","3rd Place"});
        add(makeLabel("Hackathon")); add(hackathonBox);
        compField = new JTextField(); add(makeLabel("Coding Competitions")); add(compField);

        JButton calcBtn = new JButton("Calculate");
        add(calcBtn);

        overallScoreLabel = new JLabel("0.0 / 100"); add(overallScoreLabel);
        resultLabel = new JLabel("Enter details"); add(resultLabel);

        calcBtn.addActionListener(e -> calculateScore());
    }

    private JLabel makeLabel(String text){
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.CYAN);
        return lbl;
    }

    private void calculateScore(){
        try{
            double tenth = Double.parseDouble(tenthField.getText());
            double twelfth = Double.parseDouble(twelfthField.getText());
            double cgpa = Double.parseDouble(cgpaField.getText());

            double acad = ((tenth+twelfth)/2*0.4)+(cgpa*10*0.6);
            double finalScore = acad; // simplified for demo

            overallScoreLabel.setText(String.format("%.2f / 100",finalScore));
            resultLabel.setText("Calculation Done");

            // Insert into DB
            String url = "jdbc:mysql://localhost:3306/placement_db";
            String user = "root";
            String pass = "password";
            Connection conn = DriverManager.getConnection(url,user,pass);
            String sql = "INSERT INTO user_scores(tenth,twelfth,cgpa,final_score) VALUES(?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1,tenth);
            stmt.setDouble(2,twelfth);
            stmt.setDouble(3,cgpa);
            stmt.setDouble(4,finalScore);
            stmt.executeUpdate();
            stmt.close();
            conn.close();

        }catch(Exception ex){ resultLabel.setText("Error: "+ex.getMessage()); }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new PlacementApp().setVisible(true));
    }
}