package com.honeybunch.app.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteChatModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private long status;
    @SerializedName("messageArray")
    @Expose
    private List<String> messageArray = null;
    @SerializedName("recipientImage")
    @Expose
    private String recipientImage;
    @SerializedName("recipientName")
    @Expose
    private String recipientName;
    @SerializedName("conversationId")
    @Expose
    private int conversationId;
    @SerializedName("recipientId")
    @Expose
    private int recipientId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public List<String> getMessageArray() {
        return messageArray;
    }

    public void setMessageArray(List<String> messageArray) {
        this.messageArray = messageArray;
    }

    public String getRecipientImage() {
        return recipientImage;
    }

    public void setRecipientImage(String recipientImage) {
        this.recipientImage = recipientImage;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public long getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

}