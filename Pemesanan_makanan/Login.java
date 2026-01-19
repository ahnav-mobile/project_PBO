package Pemesanan_makanan;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public Login() {
        setTitle("Login - Pemesanan Makanan");
        setSize(420, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== BACKGROUND =====
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 242, 245));

        // ===== CARD =====
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(320, 220));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // ===== TITLE =====
        JLabel lblTitle = new JLabel("PEMESANAN MAKANAN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Silakan login");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(5));
        card.add(lblSub);
        card.add(Box.createVerticalStrut(15));

        // ===== INPUT =====
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        styleField(txtUsername, "Username");
        styleField(txtPassword, "Password");

        card.add(txtUsername);
        card.add(Box.createVerticalStrut(10));
        card.add(txtPassword);
        card.add(Box.createVerticalStrut(15));

        // ===== BUTTON =====
        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setBackground(new Color(52, 152, 219));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setFocusPainted(false);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        card.add(btnLogin);

        mainPanel.add(card);
        add(mainPanel);

        // ===== ACTION =====
        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void styleField(JTextField field, String title) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createTitledBorder(title));
    }

    private void login() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pemesanan_makanan?useSSL=false&serverTimezone=UTC",
                    "root",
                    "");

            String sql = "SELECT id_user, role FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtUsername.getText());
            ps.setString(2, new String(txtPassword.getPassword()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getString("role").equalsIgnoreCase("admin")) {
                    new AdminFrame();
                } else {
                    new UserFrame(rs.getInt("id_user"));
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Username atau password salah",
                        "Login Gagal",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Koneksi database gagal",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
