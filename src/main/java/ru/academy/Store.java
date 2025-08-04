package ru.academy;

import java.util.List;

public class Store {

    public int id;
    public String name;
    public String address;
    public boolean isOpen;

    public Store(int id, String name, String address, boolean isOpen) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.isOpen = isOpen;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}
