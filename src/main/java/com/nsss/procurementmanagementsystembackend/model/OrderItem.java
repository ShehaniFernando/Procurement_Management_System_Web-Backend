package com.nsss.procurementmanagementsystembackend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class OrderItem {
    @DBRef
    private Material material;
    private double quantity;

    public OrderItem(Material material, double quantity) {
        this.material = material;
        this.quantity = quantity;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
