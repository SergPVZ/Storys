package ru.academy;

public class Warehouse {

    private int id;
    private int productId;
    private int quantity;

    public Warehouse(int id, int productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
