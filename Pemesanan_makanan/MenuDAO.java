package Pemesanan_makanan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    public static List<MenuMakanan> getAllMenu() {
        List<MenuMakanan> list = new ArrayList<>();
        try {
            Connection c = KoneksiDB.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM menu_makanan");

            while (rs.next()) {
                list.add(new MenuMakanan(
                        rs.getInt("id_menu"),
                        rs.getString("nama_menu"),
                        rs.getInt("harga")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void insert(String nama, int harga) throws Exception {
        Connection c = KoneksiDB.getConnection();
        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO menu_makanan (nama_menu, harga) VALUES (?,?)");
        ps.setString(1, nama);
        ps.setInt(2, harga);
        ps.executeUpdate();
    }

    public static void update(int id, String nama, int harga) throws Exception {
        Connection c = KoneksiDB.getConnection();
        PreparedStatement ps = c.prepareStatement(
                "UPDATE menu_makanan SET nama_menu=?, harga=? WHERE id_menu=?");
        ps.setString(1, nama);
        ps.setInt(2, harga);
        ps.setInt(3, id);
        ps.executeUpdate();
    }

    public static void delete(int id) throws Exception {
        Connection c = KoneksiDB.getConnection();
        PreparedStatement ps = c.prepareStatement(
                "DELETE FROM menu_makanan WHERE id_menu=?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
