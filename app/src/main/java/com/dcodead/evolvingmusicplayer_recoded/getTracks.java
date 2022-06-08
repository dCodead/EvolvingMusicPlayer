package com.dcodead.evolvingmusicplayer_recoded;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import java.util.ArrayList;

public class getTracks extends AsyncTask<Void, Void, Boolean>{
    public ArrayList<String> fileDirs = new ArrayList<>();
    public ArrayList<String> trackImagePaths = new ArrayList<>();
    public ArrayList<String> trackNames = new ArrayList<>();
    public ArrayList<Uri> trackUris = new ArrayList<>();
    public ArrayList<Track> tracks = new ArrayList<>();

    @SuppressLint("Range")
    public void getAllTracks(Uri uri, ContentResolver cr)

    {
        new ArrayList();
        String[] projection = {"title", "_data", "_id", MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.COMPOSER};
        Cursor cursor = cr.query(uri, projection, (String) null, null, (String) null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("title"));
            this.trackNames.add(name);
            Uri singleUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
            this.trackUris.add(singleUri);
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            Uri art = Uri.parse("content://media/external/audio/albumart");

            Uri albumUri = ContentUris.withAppendedId(art, albumId);


            tracks.add(new Track(name, id, albumUri));
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return null;
    }
}
