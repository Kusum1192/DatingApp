package com.honeybunch.app.models;

public class SingleModeList {

    String modelImage;
    String modelName;

    public SingleModeList(String modelImage, String modelName) {
        this.modelImage = modelImage;
        this.modelName = modelName;
    }

    public String getModelImage() {
        return modelImage;
    }

    public void setModelImage(String modelImage) {
        this.modelImage = modelImage;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
