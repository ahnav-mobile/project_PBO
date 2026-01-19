package Pemesanan_makanan;

public class Pesanan {
    private int id;
    private String tanggal;
    private int total;
    private String status;

    public Pesanan(int id, String tanggal, int total, String status) {
        this.id = id;
        this.tanggal = tanggal;
        this.total = total;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}
