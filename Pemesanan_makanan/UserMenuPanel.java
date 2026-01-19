package Pemesanan_makanan;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class UserMenuPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private int idUser;

    public UserMenuPanel(int idUser) {
        this.idUser = idUser;
        setLayout(new BorderLayout(10, 10));

        model = new DefaultTableModel(
                new Object[] { "Pilih", "ID", "Nama Menu", "Harga", "Qty" }, 0) {

            @Override
            public Class<?> getColumnClass(int col) {
                if (col == 0)
                    return Boolean.class;
                if (col == 4)
                    return Integer.class;
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 0 || col == 4;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);

        // Spinner Qty
        table.getColumnModel().getColumn(4)
                .setCellEditor(new SpinnerEditor());

        loadMenu();

        JButton btnPesan = new JButton("Pesan");
        btnPesan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPesan.addActionListener(e -> prosesPesanan());

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPesan, BorderLayout.SOUTH);
    }

    private void loadMenu() {
        model.setRowCount(0);
        try (Connection c = KoneksiDB.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM menu_makanan")) {

            while (rs.next()) {
                model.addRow(new Object[] {
                        false,
                        rs.getInt("id_menu"),
                        rs.getString("nama_menu"),
                        rs.getInt("harga"),
                        1
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prosesPesanan() {
        Connection conn = null;

        try {
            conn = KoneksiDB.getConnection();
            conn.setAutoCommit(false);

            int total = 0;
            boolean adaPesanan = false;

            // HITUNG TOTAL
            for (int i = 0; i < table.getRowCount(); i++) {
                boolean pilih = (boolean) table.getValueAt(i, 0);
                if (pilih) {
                    int qty = (int) table.getValueAt(i, 4);
                    int harga = Integer.parseInt(table.getValueAt(i, 3).toString());
                    total += qty * harga;
                    adaPesanan = true;
                }
            }

            if (!adaPesanan) {
                JOptionPane.showMessageDialog(this, "Pilih menu terlebih dahulu!");
                return;
            }

            // INSERT PESANAN
            String sqlPesanan = "INSERT INTO pesanan (id_user, total, status) VALUES (?, ?, 'PENDING')";

            PreparedStatement psPesanan = conn.prepareStatement(sqlPesanan, Statement.RETURN_GENERATED_KEYS);
            psPesanan.setInt(1, idUser);
            psPesanan.setInt(2, total);
            psPesanan.executeUpdate();

            ResultSet rs = psPesanan.getGeneratedKeys();
            if (!rs.next()) {
                conn.rollback();
                return;
            }

            int idPesanan = rs.getInt(1);

            // INSERT DETAIL PESANAN
            String sqlDetail = "INSERT INTO detail_pesanan (id_pesanan, id_menu, qty, subtotal) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement psDetail = conn.prepareStatement(sqlDetail);

            for (int i = 0; i < table.getRowCount(); i++) {
                boolean pilih = (boolean) table.getValueAt(i, 0);
                if (pilih) {
                    int idMenu = Integer.parseInt(table.getValueAt(i, 1).toString());
                    int qty = (int) table.getValueAt(i, 4);
                    int harga = Integer.parseInt(table.getValueAt(i, 3).toString());
                    int subtotal = qty * harga;

                    psDetail.setInt(1, idPesanan);
                    psDetail.setInt(2, idMenu);
                    psDetail.setInt(3, qty);
                    psDetail.setInt(4, subtotal);
                    psDetail.addBatch();
                }
            }

            psDetail.executeBatch();
            conn.commit();

            JOptionPane.showMessageDialog(this,
                    "Pesanan berhasil!\nTotal: Rp " + total);

            resetForm();

        } catch (Exception e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ignored) {
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memproses pesanan!");
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException ignored) {
            }
        }
    }

    private void resetForm() {
        for (int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(false, i, 0);
            table.setValueAt(1, i, 4);
        }
    }
}
