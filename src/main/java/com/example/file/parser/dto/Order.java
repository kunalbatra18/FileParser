package com.example.file.parser.dto;

public class Order {
    private String id;
    private String orderId;
    private String amount;
    private String currency;
    private String comment ;
    private String result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", comment='" + comment + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
