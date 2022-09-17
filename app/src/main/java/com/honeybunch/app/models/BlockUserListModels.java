package com.honeybunch.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlockUserListModels {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("blockList")
@Expose
private List<BlockList> blockList = null;


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

public List<BlockList> getBlockList() {
return blockList;
}

public void setBlockList(List<BlockList> blockList) {
this.blockList = blockList;
}

    public static class BlockList {

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
        @SerializedName("bio")
        @Expose
        private String bio;

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

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }
    }

}