package Pemesanan_makanan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PenjualanPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JLabel lblTotal;

    public PenjualanPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ===== TITLE =====
        JLabel title = new JLabel("Data Penjualan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[] { "ID Pesanan", "Tanggal", "Total (Rp)" }, 0) {
            public boolean isCellEditable(int r, int c) {
                return false; // READ ONLY
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        loadData();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== TOTAL =====
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblTotal = new JLabel();
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateTotal();

        bottom.add(lblTotal);
        add(bottom, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        for (Object[] row : PenjualanDAO.getAllPenjualan()) {
            model.addRow(row);
        }
    }

    private void updateTotal() {
        int total = PenjualanDAO.getTotalUang();
        lblTotal.setText("Total Uang Diterima : Rp " + total);
    }
}
