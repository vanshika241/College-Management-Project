
package CollegeApplicationSystem;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateStudent {

    private static final String userName = "DB_username";
    private static final String url = "DB_URL";
    private static final String password = "DB_password";

    UpdateStudent() {
        JFrame frame = new JFrame("Update Student");
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

                // Background color
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, w, h);

                // Vertical and horizontal grid lines
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
        int w = screen.width;

        JLabel heading = new JLabel("Update Student", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 50));
        heading.setForeground(new Color(2, 111, 91));
        heading.setBounds(0, 60, w, 80);
        panel.add(heading);

        int labelX = w / 2 - 450;
        int fieldX = w / 2 - 150;

        JLabel enrollmentLabel = new JLabel("Enrollment Number:");
        enrollmentLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        enrollmentLabel.setForeground(new Color(2, 122, 96));
        enrollmentLabel.setBounds(labelX, 180, 260, 40);
        panel.add(enrollmentLabel);

        JTextField rollNo = new JTextField();
        rollNo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        rollNo.setBounds(fieldX, 180, 320, 40);
        panel.add(rollNo);

        JButton search = new JButton("Search");
        search.setBackground(new Color(2, 165, 143));
        search.setForeground(Color.WHITE);
        search.setFont(new Font("Segoe UI", Font.BOLD, 18));
        search.setFocusPainted(false);
        search.setBorder(BorderFactory.createLineBorder(new Color(2, 122, 96), 2, true));
        search.setUI(new RoundedButtonUI(15));
        search.setBounds(w / 2 + 200, 180, 150, 40);
        panel.add(search);

        String[] cols = {"Student Name", "Class", "Enrollment No"};
        String[][] data = {{"-", "-", "-"}}; // static row (no logic)

        JTable table = new JTable(data, cols);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(30);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(2, 165, 143));
        header.setForeground(Color.WHITE);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(w / 2 - 500, 260, 1000, 180);
        panel.add(tablePane);

        JLabel oldProperty = new JLabel("Field to Update:");
        oldProperty.setFont(new Font("Segoe UI", Font.BOLD, 22));
        oldProperty.setForeground(new Color(2, 122, 96));
        oldProperty.setBounds(labelX, 470, 260, 40);
        panel.add(oldProperty);

        JTextField oldPropName = new JTextField();
        oldPropName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        oldPropName.setBounds(fieldX, 470, 320, 40);
        panel.add(oldPropName);

        JLabel newProperty = new JLabel("New Value:");
        newProperty.setFont(new Font("Segoe UI", Font.BOLD, 22));
        newProperty.setForeground(new Color(2, 122, 96));
        newProperty.setBounds(labelX, 540, 260, 40);
        panel.add(newProperty);

        JTextField newPropValue = new JTextField();
        newPropValue.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        newPropValue.setBounds(fieldX, 540, 320, 40);
        panel.add(newPropValue);

        JButton update = new JButton("Update");
        update.setBackground(new Color(2, 165, 143));
        update.setForeground(Color.WHITE);
        update.setFont(new Font("Segoe UI", Font.BOLD, 20));
        update.setFocusPainted(false);
        update.setBorder(BorderFactory.createLineBorder(new Color(2, 122, 96), 2, true));
        update.setUI(new RoundedButtonUI(15));
        update.setBounds(w / 2 - 100, 620, 250, 50);
        panel.add(update);

        frame.setVisible(true);

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rno = rollNo.getText();
                if (rno.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Enter Enrollment No");
                    return;
                }
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url, userName, password);
                    PreparedStatement pstm = con.prepareStatement("SELECT EnrollMentNo , StudentName , Class  FROM student Where EnrollMentNo = ? ");
                    pstm.setString(1, rno);
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

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldName = oldPropName.getText().trim();
                String newValue = newPropValue.getText().trim();
                String rno = rollNo.getText().trim();
                if (oldName.isEmpty() || newValue.isEmpty() || rno.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Enter Values");
                    return;
                }
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url, userName, password);
                    String query = "UPDATE student SET " + oldName + " = ? WHERE EnrollMentNo = ?";
                    PreparedStatement pstm = con.prepareStatement(query);
                    pstm.setString(1, newValue);
                    pstm.setString(2, rno);
                    pstm.executeUpdate();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        new UpdateStudent();
    }
}

