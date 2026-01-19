package Pemesanan_makanan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PesananPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cbStatus;

    public PesananPanel() {
        setLayout(new BorderLayout());

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[] { "ID", "Tanggal", "Total", "Status" }, 0);
        table = new JTable(model);
        loadData();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BOTTOM PANEL =====
        JPanel bottom = new JPanel();

        cbStatus = new JComboBox<>(new String[] {
                "PENDING", "PROSES", "SELESAI", "BATAL"
        });

        JButton btnUpdate = new JButton("Update Status");
        JButton btnHapus = new JButton("Hapus Pesanan");

        bottom.add(new JLabel("Status:"));
        bottom.add(cbStatus);
        bottom.add(btnUpdate);
        bottom.add(btnHapus);

        add(bottom, BorderLayout.SOUTH);

        // ===== ACTION =====
        btnUpdate.addActionListener(e -> updateStatus());
        btnHapus.addActionListener(e -> hapusPesanan());
    }

    private void loadData() {
        model.setRowCount(0);
        for (Pesanan p : PesananDAO.getAll()) {
            model.addRow(new Object[] {
                    p.getId(),
                    p.getTanggal(),
                    p.getTotal(),
                    p.getStatus()
            });
        }
    }

    private void updateStatus() {
        int row = table.getSelectedRow();
        if (row < 0)
            return;

        try {
            int id = (int) model.getValueAt(row, 0);
            String status = cbStatus.getSelectedItem().toString();
            PesananDAO.updateStatus(id, status);
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal update status");
        }
    }

    private void hapusPesanan() {
        int row = table.getSelectedRow();
        if (row < 0)
            return;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin hapus pesanan?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = (int) model.getValueAt(row, 0);
                PesananDAO.delete(id);
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal hapus pesanan");
            }
        }
    }
}
