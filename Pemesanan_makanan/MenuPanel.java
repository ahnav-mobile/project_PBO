package Pemesanan_makanan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MenuPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtNama, txtHarga;

    public MenuPanel() {
        setLayout(new BorderLayout());

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        txtNama = new JTextField();
        txtHarga = new JTextField();

        form.add(new JLabel("Nama Menu"));
        form.add(txtNama);
        form.add(new JLabel("Harga"));
        form.add(txtHarga);

        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[] { "ID", "Nama Menu", "Harga" }, 0);
        table = new JTable(model);
        loadData();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON =====
        JPanel btnPanel = new JPanel();

        JButton btnTambah = new JButton("Tambah");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");

        btnPanel.add(btnTambah);
        btnPanel.add(btnUbah);
        btnPanel.add(btnHapus);

        add(btnPanel, BorderLayout.SOUTH);

        // ===== ACTION =====
        btnTambah.addActionListener(e -> tambah());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());

        table.getSelectionModel().addListSelectionListener(e -> isiForm());
    }

    private void loadData() {
        model.setRowCount(0);
        for (MenuMakanan m : MenuDAO.getAllMenu()) {
            model.addRow(new Object[] {
                    m.getId(), m.getNama(), m.getHarga()
            });
        }
    }

    private void isiForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtNama.setText(model.getValueAt(row, 1).toString());
            txtHarga.setText(model.getValueAt(row, 2).toString());
        }
    }

    private void tambah() {
        try {
            MenuDAO.insert(txtNama.getText(),
                    Integer.parseInt(txtHarga.getText()));
            loadData();
            clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menambah menu");
        }
    }

    private void ubah() {
        int row = table.getSelectedRow();
        if (row < 0)
            return;

        try {
            int id = (int) model.getValueAt(row, 0);
            MenuDAO.update(id, txtNama.getText(),
                    Integer.parseInt(txtHarga.getText()));
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah menu");
        }
    }

    private void hapus() {
        int row = table.getSelectedRow();
        if (row < 0)
            return;

        try {
            int id = (int) model.getValueAt(row, 0);
            MenuDAO.delete(id);
            loadData();
            clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus menu");
        }
    }

    private void clear() {
        txtNama.setText("");
        txtHarga.setText("");
    }
}
