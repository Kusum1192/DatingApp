package com.honeybunch.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListLikeModels {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private long status;
    @SerializedName("likeList")
    @Expose
    private List<Like> likeList = null;
    @SerializedName("count")
    @Expose
    private Count count;

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

    public List<Like> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<Like> likeList) {
        this.likeList = likeList;
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }


    public class Like {

        @SerializedName("matchId")
        @Expose
        private long matchId;
        @SerializedName("requesterId")
        @Expose
        private int requesterId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("requesterName")
        @Expose
        private String requesterName;
        @SerializedName("requesterAge")
        @Expose
        private String requesterAge;
        @SerializedName("status")
        @Expose
        private String status;

        public long getMatchId() {
            return matchId;
        }

        public void setMatchId(long matchId) {
            this.matchId = matchId;
        }

        public int getRequesterId() {
            return requesterId;
        }

        public void setRequesterId(int requesterId) {
            this.requesterId = requesterId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRequesterName() {
            return requesterName;
        }

        public void setRequesterName(String requesterName) {
            this.requesterName = requesterName;
        }

        public String getRequesterAge() {
            return requesterAge;
        }

        public void setRequesterAge(String requesterAge) {
            this.requesterAge = requesterAge;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
    public class Count {

        @SerializedName("view_count")
        @Expose
        private long viewCount;
        @SerializedName("like_count")
        @Expose
        private int likeCount;
        @SerializedName("match_count")
        @Expose
        private long matchCount;
        @SerializedName("total")
        @Expose
        private long total;

        public long getViewCount() {
            return viewCount;
        }

        public void setViewCount(long viewCount) {
            this.viewCount = viewCount;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public long getMatchCount() {
            return matchCount;
        }

        public void setMatchCount(long matchCount) {
            this.matchCount = matchCount;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

    }

}