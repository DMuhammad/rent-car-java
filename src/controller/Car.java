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
public class Car {

    private int id;
    private String plat;
    private String merk;
    private double harga;

    private Connection conn;
    private PreparedStatement pst;
    private ResultSet rst;

    public Car() {

    }

    public Car(int id) {
        this.id = id;
    }

    public Car(int id, String plat, String merk, double harga) {
        this.id = id;
        this.plat = plat;
        this.merk = merk;
        this.harga = harga;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the merk
     */
    public String getMerk() {
        return merk;
    }

    /**
     * @param merk the merk to set
     */
    public void setMerk(String merk) {
        this.merk = merk;
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

    public boolean insert() {
        boolean result = false;

        try {
            conn = koneksi.koneksi();
            pst = conn.prepareStatement("SELECT * FROM cars WHERE id = '"
                    + this.getId() + "'");
            rst = pst.executeQuery();

            if (!rst.next()) {
                pst = conn.prepareStatement("INSERT INTO cars (plat, merk, harga) VALUES ('"
                        + this.getPlat() + "','"
                        + this.getMerk() + "','"
                        + this.getHarga() + "');");
                pst.execute();

                result = true;
            } else {
                pst = conn.prepareStatement("UPDATE cars SET "
                        + "plat = '" + this.getPlat() + "', "
                        + "merk = '" + this.getMerk() + "', "
                        + "harga = " + this.getHarga() + " "
                        + "WHERE id = " + this.getId());
                pst.execute();

                result = true;
            }
        } catch (SQLException e) {
            result = false;
        }

        return result;
    }

    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();

        try {
            conn = koneksi.koneksi();
            pst = conn.prepareStatement("SELECT * FROM cars");
            rst = pst.executeQuery();

            while (rst.next()) {
                int id = rst.getInt("id");
                String plat = rst.getString("plat");
                String merk = rst.getString("merk");
                double harga = rst.getDouble("harga");
                Car car = new Car(id, plat, merk, harga);
                carList.add(car);
            }
        } catch (SQLException e) {
        }

        return carList;
    }
    
    public List<Car> getCarByMerk() {
        List<Car> carList = new ArrayList<>();

        try {
            conn = koneksi.koneksi();
            pst = conn.prepareStatement("SELECT * FROM cars WHERE merk LIKE ?");
            pst.setString(1, "%" + this.getMerk() + "%");
            rst = pst.executeQuery();

            while (rst.next()) {
                int id = rst.getInt("id");
                String plat = rst.getString("plat");
                String merk = rst.getString("merk");
                double harga = rst.getDouble("harga");
                Car car = new Car(id, plat, merk, harga);
                carList.add(car);
            }
        } catch (SQLException e) {
        }

        return carList;
    }
    
    public void deleteCar() {
        try {
            conn = koneksi.koneksi();
            pst = conn.prepareStatement("DELETE FROM cars WHERE id = '" + this.getId() + "'");
            pst.execute();
        } catch (SQLException e) {
        }
    }
}
