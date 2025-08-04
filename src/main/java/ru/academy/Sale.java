package ru.academy;

import java.util.List;

public class Sale {

    private int id;
    private int storeId;
    private int productId;
    private int quantity;
    private String date;

    public Sale(int id, int storeId, int productId, int quantity, String date) {

        this.id = id;
        this.storeId = storeId;
        this.productId = productId;
        this.quantity = quantity;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }
}
