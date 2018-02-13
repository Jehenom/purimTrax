package com.trax.purim.model;

import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by asisayag on 27/01/2018.
 */

public class VoteOption {

    @SerializedName("id")
    private int id;

    @SerializedName("path")
    private String imageURL;

    @SerializedName("text")
    private String text;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
