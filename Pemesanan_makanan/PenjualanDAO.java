package Pemesanan_makanan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PenjualanDAO {

    public static List<Object[]> getAllPenjualan() {
        List<Object[]> list = new ArrayList<>();
        try {
            Connection c = KoneksiDB.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(
                    "SELECT id_pesanan, tanggal, total " +
                            "FROM pesanan WHERE status='SELESAI' ORDER BY tanggal DESC");

            while (rs.next()) {
                list.add(new Object[] {
                        rs.getInt("id_pesanan"),
                        rs.getString("tanggal"),
                        rs.getInt("total")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int getTotalUang() {
        int total = 0;
        try {
            Connection c = KoneksiDB.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(
                    "SELECT SUM(total) AS total_uang FROM pesanan WHERE status='SELESAI'");

            if (rs.next()) {
                total = rs.getInt("total_uang");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}
