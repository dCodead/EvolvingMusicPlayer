package com.dcodead.evolvingmusicplayer_recoded;



import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class Main extends AppCompatActivity {
    public static void main(String[] args){

    }


    getTracks getTracks = new getTracks();
    protected void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        setContentView(R.layout.main_activity);
        {
            Uri uri;
            if (Build.VERSION.SDK_INT >= 29) { // sdk>29 needs special permissions
                uri = MediaStore.Audio.Media.getContentUri("external");
            } else {
                uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            // Permission gathering
            new File("Android/media");
            Uri filesUri = DocumentsContract.buildDocumentUri("com.android.externalstorage.documents", "primary:Android/media/");
            Uri buildTreeDocumentUri = DocumentsContract.buildTreeDocumentUri("com.android.externalstorage.documents", "primary:Android/media/");
            if (filesUri.getAuthority() != null && filesUri.getAuthority() == "com.android.externalstorage.documents") {
                new File(Environment.getExternalStorageDirectory(), filesUri.getPath().split(":", 2)[1]);
            }
            Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
            startActivity(intent);
            // permissions gathering ends here

            //(this.getTracks).getAllTracks(uri, getContentResolver());

            final MediaController mediaController = new MediaController(getApplicationContext());
            LinearLayout miniPlayLayout = (LinearLayout) findViewById(R.id.miniPlayLayout);
            ListView tracksView = (ListView) findViewById(R.id.tracks);
            final FloatingActionButton playButton = (FloatingActionButton) findViewById(R.id.playButton);
            FloatingActionButton nextButton = (FloatingActionButton) findViewById(R.id.nextButton);
            FloatingActionButton prevButton = (FloatingActionButton) findViewById(R.id.prevButton);
            ImageView albumArt = (ImageView) findViewById(R.id.albumArt);
            final TextView currentTrackName = (TextView) findViewById(R.id.currentTrack);


            playButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String playPauseResult = mediaController.pause_resume();
                    if (playPauseResult.equals("paused")) {
                        playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.play_rectangle));
                    } else if (playPauseResult.equals("resumed")) {
                        playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_btn));
                    }
                }
            });


            nextButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mediaController.next();
                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_btn));
                    if (mediaController.getPosition() > -1) {
                        try {
                            int position = mediaController.getPosition();
                            if (position != -1) {
                                currentTrackName.setText(getTracks.trackNames.get(position));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            prevButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mediaController.previous();
                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_btn));
                    if (mediaController.getPosition() > -1) {
                        try {
                            int position = mediaController.getPosition();
                            if (position != -1) {
                                currentTrackName.setText(getTracks.trackNames.get(position));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            FrameLayout playSheet = (FrameLayout) findViewById(R.id.playSheet);
            final BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(playSheet);
            playSheet.setClickable(true);
            ImageView arrow = (ImageView) findViewById(R.id.upArrow);
            sheetBehavior.setDraggable(true);
            arrow.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (sheetBehavior.getState() == 4) {
                        sheetBehavior.setState(3);
                    } else {
                        sheetBehavior.setState(4);
                    }
                }
            });
            final BottomSheetBehavior bottomSheetBehavior = sheetBehavior;
            MediaController mediaController2 = mediaController;
            final LinearLayout linearLayout = miniPlayLayout;
            ImageView arrow2 = arrow;
            final LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.playControlsLayout);
            final TextView textView = currentTrackName;
            LinearLayout linearLayout3 = miniPlayLayout;
            BottomSheetBehavior sheetBehavior2 = sheetBehavior;
            final FloatingActionButton floatingActionButton = playButton;
            FrameLayout frameLayout = playSheet;
            final FloatingActionButton floatingActionButton2 = prevButton;
            TextView currentTrackName2 = currentTrackName;
            final FloatingActionButton floatingActionButton3 = nextButton;
            FloatingActionButton floatingActionButton4 = prevButton;
            final ImageView imageView = arrow2;
            BottomSheetBehavior.BottomSheetCallback playsheet = new BottomSheetBehavior.BottomSheetCallback() {
                public void onStateChanged(View bottomSheet, int newState) {
                    if (bottomSheetBehavior.getState() == 4) {
                        linearLayout.setOrientation(0);
                        linearLayout2.setGravity(GravityCompat.START);
                        linearLayout.setGravity(17);
                        textView.setGravity(GravityCompat.START);
                        return;
                    }
                    linearLayout.setOrientation(1);
                    linearLayout.setGravity(80);
                    textView.setGravity(17);
                    linearLayout2.setGravity(17);
                }

                public void onSlide(View bottomSheet, float slideOffset) {
                    floatingActionButton.setCustomSize((int) ((110.0f * slideOffset) + 100.0f));
                    floatingActionButton2.setCustomSize((int) ((slideOffset * 90.0f) + 100.0f));
                    floatingActionButton3.setCustomSize((int) ((90.0f * slideOffset) + 100.0f));
                    imageView.setRotation(180.0f * slideOffset);
                }
            };
            sheetBehavior2.addBottomSheetCallback(playsheet);


            getTracks.getAllTracks(uri, getContentResolver());
            ArrayAdapter allTrackNamesAdapter = new ArrayAdapter(this.getApplicationContext(), R.id.tracks, getTracks.trackNames);
            tracksView.setAdapter(allTrackNamesAdapter);
            final MediaController mediaController3 = mediaController2;
            Uri uri2 = uri;
            ArrayList<String> trackNames = getTracks.trackNames;
            final TextView textView2 = currentTrackName2;
            ArrayAdapter arrayAdapter = allTrackNamesAdapter;
            final ImageView imageView2 = albumArt;
            Intent intent2 = intent;
            final FloatingActionButton floatingActionButton5 = playButton;
            AdapterView.OnItemClickListener onChooseListenedr = new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                   // pos = position;
                    try {
                        mediaController3.playMulti(getTracks.trackUris, position);
                        textView2.setText(getTracks.trackNames.get(mediaController3.getPosition()));
                        if (!getTracks.trackImagePaths.get(position).equals("-1")) {
                            imageView2.setImageBitmap(BitmapFactory.decodeFile(getTracks.trackImagePaths.get(position)));
                        } else {
                            imageView2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_btn));
                        }
                        floatingActionButton5.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_btn));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            tracksView.setOnItemClickListener(onChooseListenedr);
            mediaController2.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer player) {
                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.play_rectangle));
                }
            });
        }



    }
}
