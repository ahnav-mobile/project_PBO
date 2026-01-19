package Pemesanan_makanan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class UserPesananPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private int idUser;

    public UserPesananPanel(int idUser) {
        this.idUser = idUser;
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Pesanan Saya");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        model = new DefaultTableModel(
                new Object[] { "ID Detail", "Menu", "Harga", "Qty", "Subtotal", "Status" }, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                String status = getValueAt(row, 5).toString();
                return col == 3 && status.equalsIgnoreCase("PENDING");
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);

        loadPesanan();

        JButton btnUpdate = new JButton("Update Qty");
        JButton btnHapus = new JButton("Hapus Pesanan");

        btnUpdate.addActionListener(e -> updateQty());
        btnHapus.addActionListener(e -> hapusPesanan());

        JPanel bottom = new JPanel();
        bottom.add(btnUpdate);
        bottom.add(btnHapus);

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private void loadPesanan() {
        model.setRowCount(0);

        String sql = """
                    SELECT d.id_detail, m.nama_menu, m.harga, d.qty, d.subtotal, p.status
                    FROM pesanan p
                    JOIN detail_pesanan d ON p.id_pesanan = d.id_pesanan
                    JOIN menu_makanan m ON d.id_menu = m.id_menu
                    WHERE p.id_user = ?
                """;

        try (Connection c = KoneksiDB.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getInt("id_detail"),
                        rs.getString("nama_menu"),
                        rs.getInt("harga"),
                        rs.getInt("qty"),
                        rs.getInt("subtotal"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateQty() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan!");
            return;
        }

        String status = model.getValueAt(row, 5).toString();
        if (!status.equalsIgnoreCase("PENDING")) {
            JOptionPane.showMessageDialog(this, "Pesanan sudah diproses!");
            return;
        }

        int idDetail = Integer.parseInt(model.getValueAt(row, 0).toString());
        int harga = Integer.parseInt(model.getValueAt(row, 2).toString());
        int qty = Integer.parseInt(model.getValueAt(row, 3).toString());
        int subtotal = qty * harga;

        String sql = "UPDATE detail_pesanan SET qty=?, subtotal=? WHERE id_detail=?";

        try (Connection c = KoneksiDB.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, subtotal);
            ps.setInt(3, idDetail);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Qty berhasil diupdate");
            loadPesanan();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hapusPesanan() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan!");
            return;
        }

        String status = model.getValueAt(row, 5).toString();
        if (!status.equalsIgnoreCase("PENDING")) {
            JOptionPane.showMessageDialog(this, "Pesanan sudah diproses!");
            return;
        }

        int idDetail = Integer.parseInt(model.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin hapus pesanan?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        String sql = "DELETE FROM detail_pesanan WHERE id_detail=?";

        try (Connection c = KoneksiDB.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idDetail);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Pesanan dihapus");
            loadPesanan();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
