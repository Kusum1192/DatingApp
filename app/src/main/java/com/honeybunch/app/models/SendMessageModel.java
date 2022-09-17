package com.honeybunch.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendMessageModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("messageArray")
@Expose
private List<MessageArray> messageArray = null;
@SerializedName("recipientImage")
@Expose
private String recipientImage;
@SerializedName("recipientName")
@Expose
private String recipientName;
@SerializedName("conversationId")
@Expose
private Integer conversationId;


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

public String getRecipientName() {
return recipientName;
}

public void setRecipientName(String recipientName) {
this.recipientName = recipientName;
}

public Integer getConversationId() {
return conversationId;
}

public void setConversationId(Integer conversationId) {
this.conversationId = conversationId;
}

    public static  class MessageArray {

        @SerializedName("sender_id")
        @Expose
        private Integer senderId;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("bySelf")
        @Expose
        private Boolean bySelf;
        @SerializedName("msgTime")
        @Expose
        private String msgTime;

        public Integer getSenderId() {
            return senderId;
        }

        public void setSenderId(Integer senderId) {
            this.senderId = senderId;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Boolean getBySelf() {
            return bySelf;
        }

        public void setBySelf(Boolean bySelf) {
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