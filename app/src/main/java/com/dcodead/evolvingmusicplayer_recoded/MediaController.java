package com.dcodead.evolvingmusicplayer_recoded;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import java.io.IOException;
import java.util.ArrayList;

public class MediaController {
    private Context context;
    private ArrayList<Uri> currentPlaylist;
    private int currentPosition = -1;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    MediaController(Context context2) {
        this.context = context2;
        this.currentPlaylist = new ArrayList<>();
    }

    public void playSingle(Uri uri) throws IOException {
        this.mediaPlayer.reset();
        this.mediaPlayer.setDataSource(this.context, uri);
        this.mediaPlayer.prepare();
        this.mediaPlayer.start();
    }

    public void playMulti(ArrayList<Uri> currentPlaylist2, int position) throws IOException {
        this.currentPlaylist = currentPlaylist2;
        this.mediaPlayer.reset();
        this.mediaPlayer.setDataSource(this.context, currentPlaylist2.get(position));
        this.currentPosition = position;
        this.mediaPlayer.prepare();
        this.mediaPlayer.start();
    }

    public String pause_resume() {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
            return "paused";
        }
        try {
            this.mediaPlayer.start();
            return "resumed";
        } catch (Exception e) {
            return "failed";
        }
    }

    public void next() {
        MediaPlayer mediaPlayer2;
        if (this.currentPlaylist != null && (mediaPlayer2 = this.mediaPlayer) != null) {
            try {
                mediaPlayer2.pause();
                playSingle(this.currentPlaylist.get(getNextPosition()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void previous() {
        MediaPlayer mediaPlayer2;
        if (this.currentPlaylist != null && (mediaPlayer2 = this.mediaPlayer) != null) {
            try {
                mediaPlayer2.pause();
                playSingle(this.currentPlaylist.get(getPreviousPosition()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    public int getPosition() {
        return this.currentPosition;
    }

    private int getNextPosition() {
        if (this.currentPosition + 1 < this.currentPlaylist.size()) {
            this.currentPosition++;
        } else {
            this.currentPosition = 0;
        }
        return this.currentPosition;
    }

    private int getPreviousPosition() {
        int i = this.currentPosition;
        if (i - 1 >= 0) {
            this.currentPosition = i - 1;
        } else {
            this.currentPosition = this.currentPlaylist.size() - 1;
        }
        return this.currentPosition;
    }
}
