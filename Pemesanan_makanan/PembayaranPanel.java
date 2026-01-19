package Pemesanan_makanan;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PembayaranPanel extends JPanel {

    private int idUser;
    private int idPesanan;
    private int totalHarga;

    private JLabel lblTotalItem;
    private JLabel lblTotalHarga;
    private JTextField txtBayar;
    private JButton btnBayar;

    public PembayaranPanel(int idUser) {
        this.idUser = idUser;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Pembayaran");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 10));

        panelForm.add(new JLabel("Total Item"));
        lblTotalItem = new JLabel("-");
        panelForm.add(lblTotalItem);

        panelForm.add(new JLabel("Total Harga"));
        lblTotalHarga = new JLabel("-");
        panelForm.add(lblTotalHarga);

        panelForm.add(new JLabel("Nominal Bayar"));
        txtBayar = new JTextField();
        panelForm.add(txtBayar);

        add(panelForm, BorderLayout.CENTER);

        btnBayar = new JButton("Bayar");
        btnBayar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBayar.addActionListener(e -> prosesPembayaran());

        add(btnBayar, BorderLayout.SOUTH);

        loadDataPembayaran();
    }

    private void loadDataPembayaran() {
        String sql = "SELECT p.id_pesanan, SUM(d.qty) AS total_item, SUM(d.subtotal) AS total_harga " +
                "FROM pesanan p " +
                "JOIN detail_pesanan d ON p.id_pesanan = d.id_pesanan " +
                "WHERE p.id_user = ? AND p.status = 'PENDING' " +
                "GROUP BY p.id_pesanan";

        try (Connection c = KoneksiDB.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idPesanan = rs.getInt("id_pesanan");
                int totalItem = rs.getInt("total_item");
                totalHarga = rs.getInt("total_harga");

                lblTotalItem.setText(String.valueOf(totalItem));
                lblTotalHarga.setText("Rp " + totalHarga);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Tidak ada pesanan yang perlu dibayar");
                btnBayar.setEnabled(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data pembayaran");
        }
    }

    private void prosesPembayaran() {
        if (txtBayar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan nominal pembayaran!");
            return;
        }

        int bayar;
        try {
            bayar = Integer.parseInt(txtBayar.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nominal harus angka!");
            return;
        }

        if (bayar < totalHarga) {
            JOptionPane.showMessageDialog(this,
                    "Nominal kurang dari total pembayaran!");
            return;
        }

        if (bayar > totalHarga) {
            JOptionPane.showMessageDialog(this,
                    "Nominal harus PAS, tidak boleh lebih!");
            return;
        }

        String sql = "UPDATE pesanan SET status = 'SELESAI' WHERE id_pesanan = ?";

        try (Connection c = KoneksiDB.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idPesanan);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Pembayaran berhasil!\nTerima kasih ");

            btnBayar.setEnabled(false);
            txtBayar.setEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Pembayaran gagal!");
        }
    }
}
