package com.honeybunch.app.models;

public class MessageChatModel {
    String senderName;
    String senderAge;
    String senderMessage;
    String senderImage;
    String senderMessageCount;

    public MessageChatModel(String senderName, String senderAge, String senderMessage, String senderImage, String senderMessageCount) {
        this.senderName = senderName;
        this.senderAge = senderAge;
        this.senderMessage = senderMessage;
        this.senderImage = senderImage;
        this.senderMessageCount = senderMessageCount;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(String senderMessage) {
        this.senderMessage = senderMessage;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getSenderMessageCount() {
        return senderMessageCount;
    }

    public void setSenderMessageCount(String senderMessageCount) {
        this.senderMessageCount = senderMessageCount;
    }

    public String getSenderAge() {
        return senderAge;
    }

    public void setSenderAge(String senderAge) {
        this.senderAge = senderAge;
    }
}
