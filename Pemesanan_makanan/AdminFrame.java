package Pemesanan_makanan;

import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {

    private JPanel contentPanel;
    private JLabel lblTitle;

    public AdminFrame() {
        setTitle("Admin Panel - Pemesanan Makanan");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(45, 52, 54));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel lblAdmin = new JLabel("ADMIN");
        lblAdmin.setForeground(Color.WHITE);
        lblAdmin.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAdmin.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        sidebar.add(lblAdmin);

        JButton btnMenu = createMenuButton("Menu Makanan");
        JButton btnPesanan = createMenuButton("Data Pesanan");
        JButton btnPenjualan = createMenuButton("Data Penjualan");
        JButton btnKeluar = createMenuButton("Keluar");

        sidebar.add(btnMenu);
        sidebar.add(btnPesanan);
        sidebar.add(btnPenjualan);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnKeluar);

        add(sidebar, BorderLayout.WEST);

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(241, 242, 246));
        header.setPreferredSize(new Dimension(0, 60));

        lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        header.add(lblTitle, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // ===== CONTENT =====
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        showDashboard();

        // ===== ACTION =====
        btnMenu.addActionListener(e -> showMenuMakanan());
        btnPesanan.addActionListener(e -> showDataPesanan());
        btnPenjualan.addActionListener(e -> showDataPenjualan());
        btnKeluar.addActionListener(e -> logout());

        setVisible(true);
    }

    // ===== BUTTON STYLE =====
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 52, 54));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(99, 110, 114));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(45, 52, 54));
            }
        });

        return btn;
    }

    // ===== CONTENT VIEW =====
    private void showDashboard() {
        lblTitle.setText("Dashboard");
        contentPanel.removeAll();
        contentPanel.add(createCenterLabel("Selamat Datang di Dashboard Admin"));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showMenuMakanan() {
        lblTitle.setText("Menu Makanan");
        contentPanel.removeAll();
        contentPanel.add(new MenuPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();

    }

    private void showDataPesanan() {
        lblTitle.setText("Data Pesanan");
        contentPanel.removeAll();
        contentPanel.add(new PesananPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showDataPenjualan() {
        lblTitle.setText("Data Penjualan");
        contentPanel.removeAll();
        contentPanel.add(new PenjualanPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin keluar?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            new Login();
            dispose();
        }
    }

    private JLabel createCenterLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return label;
    }
}
