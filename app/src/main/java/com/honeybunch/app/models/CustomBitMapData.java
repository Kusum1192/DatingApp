package com.honeybunch.app.models;

import android.graphics.Bitmap;

public class CustomBitMapData {
    Bitmap bitmap;
    String name;

    public CustomBitMapData(Bitmap bitmap, String name) {
        this.bitmap = bitmap;
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
