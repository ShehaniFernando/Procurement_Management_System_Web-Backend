package com.nsss.procurementmanagementsystembackend.request;

public class InvoiceRequest {
    String orderId;
    String user;

    public InvoiceRequest(String orderId, String user) {
        this.orderId = orderId;
        this.user = user;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
