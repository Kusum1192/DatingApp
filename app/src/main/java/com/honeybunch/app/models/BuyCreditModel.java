package com.honeybunch.app.models;

public class BuyCreditModel {
    String countCredits;
    String msgPerChatCredits;
    String totalCredits;
    String chatCredit;

    public BuyCreditModel(String countCredits, String msgPerChatCredits, String totalCredits, String chatCredit) {
        this.countCredits = countCredits;
        this.msgPerChatCredits = msgPerChatCredits;
        this.totalCredits = totalCredits;
        this.chatCredit = chatCredit;
    }

    public String getChatCredit() {
        return chatCredit;
    }

    public void setChatCredit(String chatCredit) {
        this.chatCredit = chatCredit;
    }

    public String getCountCredits() {
        return countCredits;
    }

    public void setCountCredits(String countCredits) {
        this.countCredits = countCredits;
    }

    public String getMsgPerChatCredits() {
        return msgPerChatCredits;
    }

    public void setMsgPerChatCredits(String msgPerChatCredits) {
        this.msgPerChatCredits = msgPerChatCredits;
    }

    public String getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(String totalCredits) {
        this.totalCredits = totalCredits;
    }
}
