package com.nsss.procurementmanagementsystembackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "invoices")
public class Invoice {
    @Id
    private String id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date generatedTimestamp;
    @DBRef
    private Order order;
    private String generatedUser;

    public Invoice(Order order, Date generatedTimestamp, String generatedUser) {
        this.order = order;
        this.generatedTimestamp = generatedTimestamp;
        this.generatedUser = generatedUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getGeneratedTimestamp() {
        return generatedTimestamp;
    }

    public void setGeneratedTimestamp(Date generatedTimestamp) {
        this.generatedTimestamp = generatedTimestamp;
    }

    public String getGeneratedUser() {
        return generatedUser;
    }

    public void setGeneratedUser(String generatedUser) {
        this.generatedUser = generatedUser;
    }
}
