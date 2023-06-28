package com.kicks.inventory.api.dao;

import com.kicks.inventory.api.Shoe;
import com.kicks.inventory.api.ShoeSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShoesDAO {

    @Autowired
    private Connection connection;

    public List<Shoe> loadShoes() {
        List<Shoe> shoeList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT brand, model, colorway, size, price, est_sale_price, quantity, style_code, sku FROM shoe_inventory WHERE quantity <> 0");
            while(resultSet.next()){
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String colorway = resultSet.getString("colorway");
                double size = resultSet.getDouble("size");
                double price = resultSet.getDouble("price");
                double estSalePrice = resultSet.getDouble("est_sale_price");
                int quantity = resultSet.getInt("quantity");
                String styleCode = resultSet.getString("style_code");
                String sku = resultSet.getString("sku");
                shoeList.add(new Shoe(estSalePrice, colorway, size, model, brand, price, quantity, styleCode, sku));
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return shoeList;
    }

    public void addShoe(Shoe shoe) throws SQLException {
        String sql = "INSERT INTO shoe_inventory (brand, model, colorway, size, price, est_sale_price, quantity, style_code, sku) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, shoe.getBrand());
            stmt.setString(2, shoe.getModel());
            stmt.setString(3, shoe.getColorway());
            stmt.setDouble(4, shoe.getSize());
            stmt.setDouble(5, shoe.getPrice());
            stmt.setDouble(6, shoe.getEstSalePrice());
            stmt.setInt(7, shoe.getQuantity());
            stmt.setString(8, shoe.getStyleCode());
            stmt.setString(9, shoe.getSku());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new shoe was inserted successfully!");
            }
            stmt.close();
        } catch (SQLException e){
            System.out.println("Could not insert to database.");
            throw e;
        }
    }
    public void addShoeSale(ShoeSale sale) {
        String sql = "INSERT INTO shoe_sale (sku, price, sale_date) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, sale.getSku());
            stmt.setDouble(2, sale.getPrice());
            stmt.setDate(3, Date.valueOf(sale.getSaleDate()));
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateShoe(Shoe shoe) {
        String sql = "UPDATE shoe_inventory SET brand=?, model=?, colorway=?, size=?, price=?, est_sale_price=?, quantity=?, style_code=? WHERE sku=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, shoe.getBrand());
            stmt.setString(2, shoe.getModel());
            stmt.setString(3, shoe.getColorway());
            stmt.setDouble(4, shoe.getSize());
            stmt.setDouble(5, shoe.getPrice());
            stmt.setDouble(6, shoe.getEstSalePrice());
            stmt.setInt(7, shoe.getQuantity());
            stmt.setString(8, shoe.getStyleCode());
            stmt.setString(9, shoe.getSku());
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Shoe getShoe(String shoeSku) {
        String sql = "SELECT brand, model, colorway, size, price, est_sale_price, quantity, style_code, sku FROM shoe_inventory WHERE sku=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, shoeSku);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String colorway = resultSet.getString("colorway");
                double size = resultSet.getDouble("size");
                double price = resultSet.getDouble("price");
                double estSalePrice = resultSet.getDouble("est_sale_price");
                int quantity = resultSet.getInt("quantity");
                String styleCode = resultSet.getString("style_code");
                String sku = resultSet.getString("sku");

                Shoe shoe = new Shoe(estSalePrice, colorway, size, model, brand, price, quantity, styleCode, sku);

                return shoe;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
