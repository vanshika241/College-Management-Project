
package CollegeApplicationSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLogin {

    private static final String username = "DB_username";
    private static final String urls = "DB_URL";
    private static final String passwords = "DB_password";

    AdminLogin() {
        JFrame frame = new JFrame("Admin Login");
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

        JLabel heading = new JLabel("Admin Login", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 50));
        heading.setForeground(new Color(2, 111, 91));
        heading.setBounds(0, 80, screen.width, 80);
        panel.add(heading);

        int labelX = screen.width / 2 - 300;
        int fieldX = screen.width / 2 - 100;

        JLabel userNameLabel = new JLabel("Username:");
        userNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        userNameLabel.setForeground(new Color(2, 122, 96));
        userNameLabel.setBounds(labelX, 230, 200, 30);
        panel.add(userNameLabel);

        JTextField enterName = new JTextField();
        enterName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        enterName.setBounds(fieldX, 225, 320, 40);
        panel.add(enterName);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        passwordLabel.setForeground(new Color(2, 122, 96));
        passwordLabel.setBounds(labelX, 300, 200, 30);
        panel.add(passwordLabel);

        JPasswordField enterPass = new JPasswordField();
        enterPass.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        enterPass.setBounds(fieldX, 295, 320, 40);
        panel.add(enterPass);

        JButton login = new JButton("Login");
        login.setBackground(new Color(2, 165, 143));
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Segoe UI", Font.BOLD, 20));
        login.setFocusPainted(false);
        login.setBorder(BorderFactory.createLineBorder(new Color(2, 122, 96), 2, true));
        login.setUI(new RoundedButtonUI(15));
        login.setBounds(screen.width / 2 - 125, 380, 250, 50);
        panel.add(login);

        frame.setVisible(true);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String uname = enterName.getText().trim();
                String pass = enterPass.getText().trim();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(urls, username, passwords);

                    PreparedStatement pstm = con.prepareStatement("SELECT AdminName , password FROM Admin WHERE AdminName = ?");
                    pstm.setString(1, uname);
                    ResultSet res = pstm.executeQuery();
                    if (res.next()) {
                        String password = res.getString("password");
                        if (password.equals(pass)) {
                            new AdminOperations();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Wrong Password");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Wrong Enter Values");
                        return;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }

                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}

