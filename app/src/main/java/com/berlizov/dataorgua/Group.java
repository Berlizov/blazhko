package com.berlizov.dataorgua;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 350z6_000 on 05.11.2015.
 */
public class Group implements Serializable {
    private String name;
    private String imageDisplayUrl;
    private String description;
    private List<Info> infos = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Info info) {
        infos.add(info);
    }

    public String getImageDisplayUrl() {
        return imageDisplayUrl;
    }

    public void setImageDisplayUrl(String imageDisplayUrl) {
        this.imageDisplayUrl = imageDisplayUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Info> getInfos() {
        return infos;
    }

}
