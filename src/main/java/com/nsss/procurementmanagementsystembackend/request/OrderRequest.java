package com.nsss.procurementmanagementsystembackend.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OrderRequest {
//    private Map<String, Integer> orderItems;
    private String item;
    private double quantity;
    private String supplier;
    private String comment;
    private String site;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date deliveryDate;

    public OrderRequest(String item, double quantity, String supplier, String comment, String site, Date deliveryDate) {
        this.item = item;
        this.quantity = quantity;
        this.supplier = supplier;
        this.comment = comment;
        this.site = site;
        this.deliveryDate = deliveryDate;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
