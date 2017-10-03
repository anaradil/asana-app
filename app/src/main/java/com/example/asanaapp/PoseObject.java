package com.example.asanaapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usser on 13.11.2016.
 */
public class PoseObject implements Parcelable {
    private String detail;
    private String imageURL;
    private int diff;
    private String seq;
    private Poses backendlessPose;
    private boolean known;
    private String objectId;
    private Bitmap b;

    public PoseObject(String detail, String imageURL, int diff, Poses backendlessPose) {
        this.setDetail(detail);
        this.setImageURL(imageURL);
        this.setDiff(diff);
        this.backendlessPose = backendlessPose;
        objectId = backendlessPose.getObjectId();
        known = false;
    }

    protected PoseObject(Parcel in) {
        detail = in.readString();
        imageURL = in.readString();
        diff = in.readInt();
        seq = in.readString();
        objectId = in.readString();
    }

    public static final Creator<PoseObject> CREATOR = new Creator<PoseObject>() {
        @Override
        public PoseObject createFromParcel(Parcel in) {
            return new PoseObject(in);
        }

        @Override
        public PoseObject[] newArray(int size) {
            return new PoseObject[size];
        }
    };

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown() {
        this.known = true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(detail);
        parcel.writeString(imageURL);
        parcel.writeInt(diff);
        parcel.writeString(seq);
        parcel.writeString(objectId);
    }

    @Override
    public boolean equals(Object obj) {
//two objcts are equal if their unique ObjectIDs are equal
        try {

            PoseObject temp = (PoseObject) obj;
            if (temp.getObjectId().equals(objectId)) {
                return true;
            }
            return false;
        }catch(Exception e) {
            if (getObjectId().equals(((Poses) obj).getObjectId())) {
                return true;
            }
            return false;
        }
    }

    public Poses getBackendlessPose() {
        return backendlessPose;
    }

    public String getObjectId() {
        return objectId;
    }

}
