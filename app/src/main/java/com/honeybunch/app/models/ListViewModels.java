package com.honeybunch.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListViewModels {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("viewerList")
    @Expose
    private List<ViewerList> viewerList = null;
    @SerializedName("count")
    @Expose
    private Count count;

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

    public List<ViewerList> getViewerList() {
        return viewerList;
    }

    public void setViewerList(List<ViewerList> viewerList) {
        this.viewerList = viewerList;
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public static class ViewerList {

        @SerializedName("matchId")
        @Expose
        private Integer matchId;
        @SerializedName("requesterId")
        @Expose
        private Integer requesterId;
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

        public Integer getMatchId() {
            return matchId;
        }

        public void setMatchId(Integer matchId) {
            this.matchId = matchId;
        }

        public Integer getRequesterId() {
            return requesterId;
        }

        public void setRequesterId(Integer requesterId) {
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
        private Integer viewCount;
        @SerializedName("like_count")
        @Expose
        private Integer likeCount;
        @SerializedName("match_count")
        @Expose
        private Integer matchCount;

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

    }

}