package com.dcodead.evolvingmusicplayer_recoded;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.ArrayList;

public class getTracks {
    public ArrayList<String> fileDirs = new ArrayList<>();
    public ArrayList<String> trackImagePaths = new ArrayList<>();
    public ArrayList<String> trackNames = new ArrayList<>();
    public ArrayList<Uri> trackUris = new ArrayList<>();

    @SuppressLint("Range")
    public void getAllTracks(Uri uri, ContentResolver cr) {
        new ArrayList();
        String[] strArr = {"title", "_data", "_id", "title"};
        Cursor cursor = cr.query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            this.trackNames.add(cursor.getString(cursor.getColumnIndex("title")));
            this.trackUris.add(ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id));
            try {
                this.trackImagePaths.add(cursor.getString(cursor.getColumnIndex("album_art")));
            } catch (Exception e) {
                this.trackImagePaths.add("-1");
            }
        }
    }
}
