package com.honeybunch.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppOpenModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("count")
    @Expose
    private Count count;
    @SerializedName("msgCount")
    @Expose
    private Integer msgCount;
    @SerializedName("forceUpdate")
    @Expose
    private Boolean forceUpdate;
    @SerializedName("packAge")
    @Expose
    private String packAge;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userAge")
    @Expose
    private String userAge;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

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

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public Integer getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(Integer msgCount) {
        this.msgCount = msgCount;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getPackAge() {
        return packAge;
    }

    public void setPackAge(String packAge) {
        this.packAge = packAge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static class Count {

        @SerializedName("view_count")
        @Expose
        private Integer viewCount;
        @SerializedName("like_count")
        @Expose
        private Integer likeCount;
        @SerializedName("match_count")
        @Expose
        private Integer matchCount;
        @SerializedName("total")
        @Expose
        private Integer total;

        public Integer getViewCount() {
            return viewCount;
        }

        public void setViewCount(Integer viewCount) {
            this.viewCount = viewCount;
        }

        public Integer getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(Integer likeCount) {
            this.likeCount = likeCount;
        }

        public Integer getMatchCount() {
            return matchCount;
        }

        public void setMatchCount(Integer matchCount) {
            this.matchCount = matchCount;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

    }

}