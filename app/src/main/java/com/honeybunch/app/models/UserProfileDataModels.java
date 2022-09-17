package com.honeybunch.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfileDataModels {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("user_age")
    @Expose
    private String userAge;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("bioText")
    @Expose
    private String bioText;
    @SerializedName("purpose")
    @Expose
    private List<String> purpose = null;
    @SerializedName("interest")
    @Expose
    private List<String> interest = null;
    @SerializedName("appearance")
    @Expose
    private String appearance;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;
    @SerializedName("interestList")
    @Expose
    private List<String> interestList = null;
    @SerializedName("purposeList")
    @Expose
    private List<String> purposeList = null;
    @SerializedName("professionList")
    @Expose
    private List<String> professionList = null;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getBioText() {
        return bioText;
    }

    public void setBioText(String bioText) {
        this.bioText = bioText;
    }

    public List<String> getPurpose() {
        return purpose;
    }

    public void setPurpose(List<String> purpose) {
        this.purpose = purpose;
    }

    public List<String> getInterest() {
        return interest;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<String> getInterestList() {
        return interestList;
    }

    public void setInterestList(List<String> interestList) {
        this.interestList = interestList;
    }

    public List<String> getPurposeList() {
        return purposeList;
    }

    public void setPurposeList(List<String> purposeList) {
        this.purposeList = purposeList;
    }

    public List<String> getProfessionList() {
        return professionList;
    }

    public void setProfessionList(List<String> professionList) {
        this.professionList = professionList;
    }

}