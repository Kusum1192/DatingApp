package com.honeybunch.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewProfileModels {

    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("appearance")
    @Expose
    private List<String> appearance = null;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("interest")
    @Expose
    private List<String> interest = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pictures")
    @Expose
    private List<String> pictures = null;
    @SerializedName("purpose")
    @Expose
    private List<String> purpose = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getAppearance() {
        return appearance;
    }

    public void setAppearance(List<String> appearance) {
        this.appearance = appearance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getInterest() {
        return interest;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<String> getPurpose() {
        return purpose;
    }

    public void setPurpose(List<String> purpose) {
        this.purpose = purpose;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}