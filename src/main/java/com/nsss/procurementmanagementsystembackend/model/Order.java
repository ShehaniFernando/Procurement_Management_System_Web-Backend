package com.nsss.procurementmanagementsystembackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "orders")
public class Order {
    @Id
    private String id;
//    private List<OrderItem> items;
    private OrderItem item;
    @DBRef
    private Supplier supplier;
    private String status;
    private double amount;
    private String comment;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date purchaseTimestamp;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date deliveryTimestamp;
    private Site site;

    public Order(OrderItem item, Supplier supplier, String status, double amount, String comment, Date purchaseTimestamp, Date deliveryTimestamp, Site site) {
        this.item = item;
        this.supplier = supplier;
        this.status = status;
        this.amount = amount;
        this.comment = comment;
        this.purchaseTimestamp = purchaseTimestamp;
        this.deliveryTimestamp = deliveryTimestamp;
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderItem getItem() {
        return item;
    }

    public void setItem(OrderItem item) {
        this.item = item;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPurchaseTimestamp() {
        return purchaseTimestamp;
    }

    public void setPurchaseTimestamp(Date purchaseTimestamp) {
        this.purchaseTimestamp = purchaseTimestamp;
    }

    public Date getDeliveryTimestamp() {
        return deliveryTimestamp;
    }

    public void setDeliveryTimestamp(Date deliveryTimestamp) {
        this.deliveryTimestamp = deliveryTimestamp;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
