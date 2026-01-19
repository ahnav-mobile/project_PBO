package Pemesanan_makanan;

import javax.swing.*;
import java.awt.*;

public class UserFrame extends JFrame {

    private JPanel contentPanel;
    private JLabel lblTitle;
    private int idUser;

    public UserFrame(int idUser) {
        this.idUser = idUser;

        setTitle("User Dashboard - Pemesanan Makanan");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(52, 73, 94));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel lblUser = new JLabel("USER MENU");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblUser.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        sidebar.add(lblUser);

        JButton btnMenu = createMenuButton("Menu Makanan");
        JButton btnPesanan = createMenuButton("Pesanan Saya");
        JButton btnBayar = createMenuButton("Pembayaran");
        JButton btnKeluar = createMenuButton("Keluar");

        sidebar.add(btnMenu);
        sidebar.add(btnPesanan);
        sidebar.add(btnBayar);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnKeluar);

        add(sidebar, BorderLayout.WEST);

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 60));
        header.setBackground(new Color(236, 240, 241));

        lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        header.add(lblTitle, BorderLayout.WEST);

        add(header, BorderLayout.NORTH);

        // ===== CONTENT =====
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        showMenuMakanan();

        // ===== ACTION =====
        btnMenu.addActionListener(e -> showMenuMakanan());
        btnPesanan.addActionListener(e -> showPesanan());
        btnBayar.addActionListener(e -> showPembayaran());
        btnKeluar.addActionListener(e -> logout());

        setVisible(true);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(52, 73, 94));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(52, 73, 94));
            }
        });

        return btn;
    }

    // ===== VIEW =====
    private void showMenuMakanan() {
        lblTitle.setText("Menu Makanan");
        contentPanel.removeAll();
        contentPanel.add(new UserMenuPanel(idUser), BorderLayout.CENTER);
        refresh();
    }

    private void showPesanan() {
        lblTitle.setText("Pesanan Saya");
        contentPanel.removeAll();
        contentPanel.add(new UserPesananPanel(idUser), BorderLayout.CENTER);
        refresh();
    }

    private void showPembayaran() {
        lblTitle.setText("Pembayaran");
        contentPanel.removeAll();
        contentPanel.add(new PembayaranPanel(idUser), BorderLayout.CENTER);
        refresh();
    }

    private void logout() {
        new Login();
        dispose();
    }

    private void refresh() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
