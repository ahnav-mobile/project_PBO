package Pemesanan_makanan;

import java.sql.Connection;
import java.sql.DriverManager;

public class KoneksiDB {

    private static final String URL = "jdbc:mysql://localhost:3306/pemesanan_makanan?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; // isi kalau MySQL pakai password

    public static Connection getConnection() {
        try {
            // Load driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Driver MySQL OK");

            // Buat koneksi
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Koneksi database berhasil");

            return conn;

        } catch (Exception e) {
            System.out.println("KONEKSI DATABASE GAGAL");
            e.printStackTrace();
            return null;
        }
    }
}
