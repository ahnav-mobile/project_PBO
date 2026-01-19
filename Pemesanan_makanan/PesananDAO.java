package Pemesanan_makanan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PesananDAO {

    public static List<Pesanan> getAll() {
        List<Pesanan> list = new ArrayList<>();
        try {
            Connection c = KoneksiDB.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM pesanan");

            while (rs.next()) {
                list.add(new Pesanan(
                        rs.getInt("id_pesanan"),
                        rs.getString("tanggal"),
                        rs.getInt("total"),
                        rs.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void updateStatus(int id, String status) throws Exception {
        Connection c = KoneksiDB.getConnection();
        PreparedStatement ps = c.prepareStatement(
                "UPDATE pesanan SET status=? WHERE id_pesanan=?");
        ps.setString(1, status);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    public static void delete(int id) throws Exception {
        Connection c = KoneksiDB.getConnection();
        PreparedStatement ps = c.prepareStatement(
                "DELETE FROM pesanan WHERE id_pesanan=?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
