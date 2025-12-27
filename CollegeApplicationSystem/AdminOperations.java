
package CollegeApplicationSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminOperations {

    AdminOperations() {
        JFrame frame = new JFrame("Admin Operations");
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
        JLabel operationAsk = new JLabel("What Operations Do You Want To Perform?", SwingConstants.CENTER);
        operationAsk.setFont(new Font("Segoe UI", Font.BOLD, 40));
        operationAsk.setForeground(new Color(2, 111, 91));
        operationAsk.setBounds(0, 80, screen.width, 80);
        panel.add(operationAsk);

        Font btnFont = new Font("Segoe UI", Font.BOLD, 22);
        Color btnBg = new Color(2, 165, 143);
        Color btnFg = Color.WHITE;
        Color btnBorder = new Color(2, 122, 96);

        int btnWidth = 300;
        int btnHeight = 60;

        int leftX = screen.width / 2 - 350;   // left button
        int rightX = screen.width / 2 + 50;   // right button

        int y1 = 220;
        int y2 = 320;

        // Add Student
        JButton add = new JButton("Add Student");
        add.setFont(btnFont);
        add.setBackground(btnBg);
        add.setForeground(btnFg);
        add.setFocusPainted(false);
        add.setBorder(BorderFactory.createLineBorder(btnBorder, 2, true));
        add.setUI(new RoundedButtonUI(15));
        add.setBounds(leftX, y1, btnWidth, btnHeight);
        panel.add(add);

        // Delete Student
        JButton deleteStudent = new JButton("Delete Student");
        deleteStudent.setFont(btnFont);
        deleteStudent.setBackground(btnBg);
        deleteStudent.setForeground(btnFg);
        deleteStudent.setFocusPainted(false);
        deleteStudent.setBorder(BorderFactory.createLineBorder(btnBorder, 2, true));
        deleteStudent.setUI(new RoundedButtonUI(15));
        deleteStudent.setBounds(rightX, y1, btnWidth, btnHeight);
        panel.add(deleteStudent);

        // Update Student
        JButton updateStudent = new JButton("Update Student");
        updateStudent.setFont(btnFont);
        updateStudent.setBackground(btnBg);
        updateStudent.setForeground(btnFg);
        updateStudent.setFocusPainted(false);
        updateStudent.setBorder(BorderFactory.createLineBorder(btnBorder, 2, true));
        updateStudent.setUI(new RoundedButtonUI(15));
        updateStudent.setBounds(leftX, y2, btnWidth, btnHeight);
        panel.add(updateStudent);

        // Search Student
        JButton searchStudent = new JButton("Search Student");
        searchStudent.setFont(btnFont);
        searchStudent.setBackground(btnBg);
        searchStudent.setForeground(btnFg);
        searchStudent.setFocusPainted(false);
        searchStudent.setBorder(BorderFactory.createLineBorder(btnBorder, 2, true));
        searchStudent.setUI(new RoundedButtonUI(15));
        searchStudent.setBounds(rightX, y2, btnWidth, btnHeight);
        panel.add(searchStudent);

        frame.setVisible(true);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddStudent();
                frame.dispose();
            }
        });

        deleteStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteStudent();
                frame.dispose();
            }
        });

        searchStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchStudent();
                frame.dispose();
            }
        });

        updateStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateStudent();
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        new AdminOperations();
    }
}

