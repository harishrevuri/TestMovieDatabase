package com.harish.test.project.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SpokenLanguage implements Serializable {
    @SerializedName("iso_639_1")
    String iso;
    @SerializedName("name")
    String name;

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
