package com.honeybunch.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfileDataInterestModels {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("interest")
@Expose
private List<String> interest = null;
@SerializedName("interestList")
@Expose
private List<String> interestList = null;

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

public List<String> getInterest() {
return interest;
}

public void setInterest(List<String> interest) {
this.interest = interest;
}

public List<String> getInterestList() {
return interestList;
}

public void setInterestList(List<String> interestList) {
this.interestList = interestList;
}

}