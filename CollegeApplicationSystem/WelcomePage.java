
package CollegeApplicationSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage {

    public static void main(String[] args) {

        JFrame frame = new JFrame("College Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);

        // Custom panel with grid-style background
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
                GradientPaint gp = new GradientPaint(w/2, h/2, sphereColor, w/2, h/2 + radius/2, new Color(0,0,0,0), true);
                g2.setPaint(gp);
                g2.fillOval(w/2 - radius/2, h/2 - radius/2, radius, radius);
            }
        };
        panel.setLayout(null);
        frame.add(panel);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        JLabel heading = new JLabel("College Management System", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 50));
        heading.setForeground(new Color(2, 111, 91));
        heading.setBounds(0, 60, screen.width, 80);
        panel.add(heading);

        JLabel welHeading = new JLabel("Welcome", SwingConstants.CENTER);
        welHeading.setFont(new Font("Segoe UI", Font.BOLD, 35));
        welHeading.setForeground(new Color(2, 122, 96));
        welHeading.setBounds(0, 160, screen.width, 50);
        panel.add(welHeading);

        int buttonWidth = 320;
        int buttonHeight = 55;

        JButton Admin = new JButton("Admin");
        Admin.setBackground(new Color(2, 165, 143));
        Admin.setForeground(Color.WHITE);
        Admin.setFont(new Font("Segoe UI", Font.BOLD, 20));
        Admin.setFocusPainted(false);
        Admin.setBorder(BorderFactory.createLineBorder(new Color(2, 122, 96), 2, true));
        Admin.setBounds((screen.width - buttonWidth) / 2, 260, buttonWidth, buttonHeight);
        Admin.setUI(new RoundedButtonUI(15));
        panel.add(Admin);

        JButton Search = new JButton("Search");
        Search.setBackground(new Color(2, 165, 143));
        Search.setForeground(Color.WHITE);
        Search.setFont(new Font("Segoe UI", Font.BOLD, 20));
        Search.setFocusPainted(false);
        Search.setBorder(BorderFactory.createLineBorder(new Color(2, 122, 96), 2, true));
        Search.setBounds((screen.width - buttonWidth) / 2, 340, buttonWidth, buttonHeight);
        Search.setUI(new RoundedButtonUI(15));
        panel.add(Search);

        frame.setVisible(true);

        Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLogin();
                frame.dispose();
            }
        });

        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchStudent();
                frame.dispose();
            }
        });
    }
}

// RoundedButtonUI remains same
class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
    private int radius;

    public RoundedButtonUI(int radius) {
        this.radius = radius;
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(b.getBackground());
        g2.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), radius, radius);
        super.paint(g2, c);
        g2.dispose();
    }
}
