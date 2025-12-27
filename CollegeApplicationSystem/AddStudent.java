
package CollegeApplicationSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddStudent {

    private static final String userName = "DB_username";
    private static final String url = "DB_URL";
    private static final String password = "DB_password";

    AddStudent() {
        JFrame frame = new JFrame("Add Student");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // FULL SCREEN
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int w = getWidth();
                int h = getHeight();
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, w, h);


                g2.setColor(new Color(71, 85, 105, 50)); // light gray/blue semi-transparent
                int gridSize = 32;
                for (int x = 0; x <= w; x += gridSize) {
                    g2.drawLine(x, 0, x, h);
                }
                for (int y = 0; y <= h; y += gridSize) {
                    g2.drawLine(0, y, w, y);
                }


                int radius = Math.min(w, h);
                Color sphereColor = new Color(139, 92, 246, 25); // purpleish, transparent
                GradientPaint gp = new GradientPaint(w / 2, h / 2, sphereColor, w / 2, h / 2 + radius / 2, new Color(0, 0, 0, 0), true);
                g2.setPaint(gp);
                g2.fillOval(w / 2 - radius / 2, h / 2 - radius / 2, radius, radius);
            }
        };
        panel.setLayout(null);
        frame.add(panel);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();


        JLabel heading = new JLabel("Add Student", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 50));
        heading.setForeground(new Color(2, 111, 91));
        heading.setBounds(0, 80, screen.width, 80);
        panel.add(heading);


        int labelX = screen.width / 2 - 300;
        int fieldX = screen.width / 2 - 100;


        JLabel stName = new JLabel("Student Name:");
        stName.setFont(new Font("Segoe UI", Font.BOLD, 22));
        stName.setForeground(new Color(2, 122, 96));
        stName.setBounds(labelX, 230, 250, 30);
        panel.add(stName);

        JTextField sname = new JTextField();
        sname.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        sname.setBounds(fieldX, 225, 320, 40);
        panel.add(sname);


        JLabel stClass = new JLabel("Class:");
        stClass.setFont(new Font("Segoe UI", Font.BOLD, 22));
        stClass.setForeground(new Color(2, 122, 96));
        stClass.setBounds(labelX, 300, 250, 30);
        panel.add(stClass);

        JTextField sclass = new JTextField();
        sclass.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        sclass.setBounds(fieldX, 295, 320, 40);
        panel.add(sclass);


        JLabel stRollNo = new JLabel("Enrollment Number:");
        stRollNo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        stRollNo.setForeground(new Color(2, 122, 96));
        stRollNo.setBounds(labelX, 370, 250, 30);
        panel.add(stRollNo);

        JTextField sRol = new JTextField();
        sRol.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        sRol.setBounds(fieldX, 365, 320, 40);
        panel.add(sRol);


        JButton btn = new JButton("Add Student");
        btn.setBackground(new Color(2, 165, 143));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(2, 122, 96), 2, true));
        btn.setUI(new RoundedButtonUI(15));
        btn.setBounds(screen.width / 2 - 150, 460, 300, 50);
        panel.add(btn);

        frame.setVisible(true);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rno = sRol.getText();
                String name = sname.getText();
                String clas = sclass.getText();

                if (rno.isEmpty() || name.isEmpty() || clas.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Enter Values");
                    return;
                }
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url, userName, password);
                    PreparedStatement pstm = con.prepareStatement("INSERT INTO student VALUES(? , ? , ?)");
                    pstm.setString(1, rno);
                    pstm.setString(2, name);
                    pstm.setString(3, clas);
                    pstm.executeUpdate();
                    con.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }

            }
        });
    }

    public static void main(String[] args) {
        new AddStudent();
    }
}

