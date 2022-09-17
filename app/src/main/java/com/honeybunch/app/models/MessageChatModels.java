package com.honeybunch.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageChatModels {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("conversationArray")
    @Expose
    private List<ConversationArray> conversationArray = null;
    @SerializedName("unreadCount")
    @Expose
    private Integer unreadCount;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ConversationArray> getConversationArray() {
        return conversationArray;
    }

    public void setConversationArray(List<ConversationArray> conversationArray) {
        this.conversationArray = conversationArray;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public static class ConversationArray {

        @SerializedName("conversationId")
        @Expose
        private Integer conversationId;
        @SerializedName("lastRead")
        @Expose
        private Boolean lastRead;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;
        @SerializedName("chatUserId")
        @Expose
        private Integer chatUserId;
        @SerializedName("chatUserName")
        @Expose
        private String chatUserName;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("messageDate")
        @Expose
        private String messageDate;
        @SerializedName("recipientAge")
        @Expose
        private String recipientAge;
        @SerializedName("status")
        @Expose
        private String status;

        public Integer getConversationId() {
            return conversationId;
        }

        public void setConversationId(Integer conversationId) {
            this.conversationId = conversationId;
        }

        public Boolean getLastRead() {
            return lastRead;
        }

        public void setLastRead(Boolean lastRead) {
            this.lastRead = lastRead;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Integer getChatUserId() {
            return chatUserId;
        }

        public void setChatUserId(Integer chatUserId) {
            this.chatUserId = chatUserId;
        }

        public String getChatUserName() {
            return chatUserName;
        }

        public void setChatUserName(String chatUserName) {
            this.chatUserName = chatUserName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessageDate() {
            return messageDate;
        }

        public void setMessageDate(String messageDate) {
            this.messageDate = messageDate;
        }

        public String getRecipientAge() {
            return recipientAge;
        }

        public void setRecipientAge(String recipientAge) {
            this.recipientAge = recipientAge;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}