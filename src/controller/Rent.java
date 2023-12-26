/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import db.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dzikri
 */
public class Rent {

    private String kode;
    private String tanggal;
    private String plat;
    private double harga;
    private int durasi;
    private double total;

    private Connection conn;
    private PreparedStatement pst;
    private ResultSet rst;

    public Rent() {
        
    }
    
    public Rent(String kode) {
        this.kode = kode;
    }
    
    public Rent(String kode, String tanggal, String plat, double harga, int durasi, double total) {
        this.kode = kode;
        this.tanggal = tanggal;
        this.plat = plat;
        this.harga = harga;
        this.durasi = durasi;
        this.total = total;
    }

    /**
     * @return the kode
     */
    public String getKode() {
        return kode;
    }

    /**
     * @param kode the kode to set
     */
    public void setKode(String kode) {
        this.kode = kode;
    }

    /**
     * @return the tanggal
     */
    public String getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal the tanggal to set
     */
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    /**
     * @return the plat
     */
    public String getPlat() {
        return plat;
    }

    /**
     * @param plat the plat to set
     */
    public void setPlat(String plat) {
        this.plat = plat;
    }

    /**
     * @return the harga
     */
    public double getHarga() {
        return harga;
    }

    /**
     * @param harga the harga to set
     */
    public void setHarga(double harga) {
        this.harga = harga;
    }

    /**
     * @return the durasi
     */
    public int getDurasi() {
        return durasi;
    }

    /**
     * @param durasi the durasi to set
     */
    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    public boolean insert() {
        boolean result = false;

        try {
            conn = koneksi.koneksi();
            pst = conn.prepareStatement("SELECT kode FROM rentals WHERE kode = '"
                    + this.getKode() + "'");
            rst = pst.executeQuery();

            if (!rst.next()) {
                pst = conn.prepareStatement("INSERT INTO rentals (kode, tanggal, plat, harga, durasi, total) VALUES ('"
                        + this.getKode() + "','"
                        + this.getTanggal() + "','"
                        + this.getPlat() + "','"
                        + this.getHarga() + "','"
                        + this.getDurasi() + "','"
                        + this.getTotal() + "');");
                pst.execute();

                result = true;
            } else {
                result = false;
            }
        } catch (SQLException e) {
            result = false;
        }

        return result;
    }

    public List<Rent> getAllRents() {
        List<Rent> rentList = new ArrayList<>();

        try {
            conn = koneksi.koneksi();
            pst = conn.prepareStatement("SELECT * FROM rentals");
            rst = pst.executeQuery();

            while (rst.next()) {
                String kode = rst.getString("kode");
                String tanggal = rst.getString("tanggal");
                String plat = rst.getString("plat");
                double harga = rst.getDouble("harga");
                int durasi = rst.getInt("durasi");
                double total = rst.getDouble("total");
                Rent rent = new Rent(kode, tanggal, plat, harga, durasi, total);
                rentList.add(rent);
            }
        } catch (SQLException e) {
        }

        return rentList;
    }

    public boolean updateRent() {
        boolean result = false;
        try {
            conn = koneksi.koneksi();
            pst = conn.prepareStatement("UPDATE rentals SET "
                    + "tanggal = '" + this.getTanggal() + "', "
                    + "plat = '" + this.getPlat() + "', "
                    + "harga = '" + this.getHarga() + "', "
                    + "durasi = '" + this.getDurasi() + "', "
                    + "total = '" + this.getTotal() + "', "
                    + "WHERE kode = '" + this.getKode() + "'");
            pst.execute();
            result = true;
        } catch (SQLException e) {
            result = false;
        }
        return result;
    }

    public void deleteRent() {
        try {
            conn = koneksi.koneksi();
            pst = conn.prepareStatement("DELETE FROM rentals WHERE kode = '" + this.getKode() + "'");
            pst.execute();
        } catch (SQLException e) {
        }
    }
}
