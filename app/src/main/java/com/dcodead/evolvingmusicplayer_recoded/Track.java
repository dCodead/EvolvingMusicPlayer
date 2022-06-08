
package com.dcodead.evolvingmusicplayer_recoded;

import android.net.Uri;

import androidx.annotation.Nullable;

public class Track {
    private String name;
    private long id;
    private Uri albummArtUri;

    public Track(String name, long id, @Nullable Uri albummArtUri){
        this.name = name;
        this.id = id;
        this.albummArtUri = albummArtUri;
    }

    public String getName(){
        return name;
    }

    public long getId(){
        return id;
    }

    public Uri getAlbummArtUri(){
        return albummArtUri;
    }

}
