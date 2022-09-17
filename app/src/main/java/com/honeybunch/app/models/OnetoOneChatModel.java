package com.honeybunch.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnetoOneChatModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private long status;
    @SerializedName("messageArray")
    @Expose
    private List<MessageArray> messageArray = null;
    @SerializedName("recipientImage")
    @Expose
    private String recipientImage;
    @SerializedName("recipientAge")
    @Expose
    private String recipientAge;
    @SerializedName("recipientName")
    @Expose
    private String recipientName;
    @SerializedName("conversationId")
    @Expose
    private int conversationId;
    @SerializedName("recipientId")
    @Expose
    private int recipientId;
    @SerializedName("blocked")
    @Expose
    private boolean blocked;
    @SerializedName("selfBlocked")
    @Expose
    private boolean selfBlocked;

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

    public List<MessageArray> getMessageArray() {
        return messageArray;
    }

    public void setMessageArray(List<MessageArray> messageArray) {
        this.messageArray = messageArray;
    }

    public String getRecipientImage() {
        return recipientImage;
    }

    public void setRecipientImage(String recipientImage) {
        this.recipientImage = recipientImage;
    }

    public String getRecipientAge() {
        return recipientAge;
    }

    public void setRecipientAge(String recipientAge) {
        this.recipientAge = recipientAge;
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

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isSelfBlocked() {
        return selfBlocked;
    }

    public void setSelfBlocked(boolean selfBlocked) {
        this.selfBlocked = selfBlocked;
    }
    public class MessageArray {

        @SerializedName("sender_id")
        @Expose
        private long senderId;
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("bySelf")
        @Expose
        private boolean bySelf;
        @SerializedName("msgTime")
        @Expose
        private String msgTime;

        public long getSenderId() {
            return senderId;
        }

        public void setSenderId(long senderId) {
            this.senderId = senderId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isBySelf() {
            return bySelf;
        }

        public void setBySelf(boolean bySelf) {
            this.bySelf = bySelf;
        }

        public String getMsgTime() {
            return msgTime;
        }
        public void setMsgTime(String msgTime) {
            this.msgTime = msgTime;
        }
    }
}