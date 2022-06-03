package com.dcodead.evolvingmusicplayer_recoded;

import android.graphics.drawable.Drawable;
import android.media.Image;

import androidx.annotation.NonNull;

public class Permission {
    private String name;
    private String description;
    private Drawable image;

    public Permission(@NonNull String name, String description, Drawable image){
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    //Possibly wrong
    public Drawable getImage() {
        return image;
    }



}
