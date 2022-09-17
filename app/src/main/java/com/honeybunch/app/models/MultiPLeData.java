package com.honeybunch.app.models;


import android.os.Parcel;
import android.os.Parcelable;

public class MultiPLeData implements Parcelable {
    String name;
    boolean isSelected = false;

    public MultiPLeData(String list) {
        this.name = list;
    }


    protected MultiPLeData(Parcel in) {
        name = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<MultiPLeData> CREATOR = new Creator<MultiPLeData>() {
        @Override
        public MultiPLeData createFromParcel(Parcel in) {
            return new MultiPLeData(in);
        }

        @Override
        public MultiPLeData[] newArray(int size) {
            return new MultiPLeData[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
