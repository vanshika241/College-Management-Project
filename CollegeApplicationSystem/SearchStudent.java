

package CollegeApplicationSystem;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SearchStudent {
    private static final String userName = "DB_username";
    private static final String url = "DB_URL";
    private static final String password = "DB_password";

    SearchStudent() {
        JFrame frame = new JFrame("Search Student");
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

        // Heading
        JLabel heading = new JLabel("Search Student", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 50));
        heading.setForeground(new Color(2, 111, 91));
        heading.setBounds(0, 60, screen.width, 80);
        panel.add(heading);

        // Label + Input
        JLabel enrollmentNo = new JLabel("Enrollment Number:");
        enrollmentNo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        enrollmentNo.setForeground(new Color(2, 122, 96));

        JTextField rollNo = new JTextField();
        rollNo.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        int labelX = screen.width / 2 - 350;
        int fieldX = screen.width / 2 - 50;

        enrollmentNo.setBounds(labelX, 170, 300, 30);
        rollNo.setBounds(fieldX, 165, 350, 40);

        panel.add(enrollmentNo);
        panel.add(rollNo);

        // Search Button
        JButton search = new JButton("Search");
        search.setBackground(new Color(2, 165, 143));
        search.setForeground(Color.WHITE);
        search.setFont(new Font("Segoe UI", Font.BOLD, 20));
        search.setFocusPainted(false);
        search.setBorder(BorderFactory.createLineBorder(new Color(2, 122, 96), 2, true));
        search.setUI(new RoundedButtonUI(15));
        search.setBounds(screen.width / 2 - 100, 230, 200, 45);
        panel.add(search);

        // Table with 3 columns
        String[] columns = {"Student Name", "Class", "Enrollment No"};
        String[][] data = {
                {"", "", ""}   // placeholder row
        };

        JTable table = new JTable(data, columns);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.setRowHeight(30);

        // Table Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setBackground(new Color(2, 165, 143));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(screen.width / 2 - 350, 320, 700, 150);
        panel.add(scrollPane);

        // Initialize table values
        table.setValueAt("", 0, 0);
        table.setValueAt("", 0, 1);
        table.setValueAt("", 0, 2);

        frame.setVisible(true);

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String rolln = rollNo.getText();
                if (rolln.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Enter Enrollment Number");
                    return;
                }

                //Load Driver
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url, userName, password);

                    PreparedStatement pstm = con.prepareStatement("SELECT EnrollMentNo , StudentName , Class  FROM student Where EnrollMentNo = ? ");
                    pstm.setString(1, rolln);

                    ResultSet res = pstm.executeQuery();

                    if (res.next()) {
                        String name = res.getString("StudentName");
                        String cls = res.getString("Class");
                        String enrollno = res.getString("EnrollMentNo");
                        table.setValueAt(name, 0, 0);
                        table.setValueAt(cls, 0, 1);
                        table.setValueAt(enrollno, 0, 2);
                    } else {
                        JOptionPane.showMessageDialog(frame, "No Student Found");
                    }
                    System.out.println("Query Run successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        new SearchStudent();
    }
}

