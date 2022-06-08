package com.dcodead.evolvingmusicplayer_recoded;



import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Main extends AppCompatActivity {
    public static void main(String[] args) {

    }

    DialogFragment permissionDialog;
    ListView permissionListView;
    getTracks getTracks = new getTracks();
    private Boolean checkWriteExternalStoragePermission(){
        String writePerrmission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String readPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        int code = getApplicationContext().checkCallingOrSelfPermission(readPermission);
        return (code==PackageManager.PERMISSION_GRANTED);
    }

    protected void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        setContentView(R.layout.main_activity);



        //Permission check and dialog area
        // if (no permission){

        //Temp
        Boolean permission = (checkWriteExternalStoragePermission())? true : false;
        if(!permission) {
            ////end Temp
            setContentView(R.layout.permission_ui); // to be deleted later
            permissionDialog = new DialogFragment(R.layout.permission_ui);
//        permissionDialog.show();




//
//            Permission readExtStorage = new Permission("Read External Storage",
//                    "Used to search for audio tracks.", null);
//
//            Permission writeExtStorage = new Permission("Write External Storage",
//                    "Used to store playlists.", null);
//            Permission[] permissions = new Permission[]{readExtStorage, writeExtStorage};
//
//            permissionListView = findViewById(R.id.permission_list);
//
//            //// Custom Permission adapter starts here
//            PermissionAdapter adapt = new PermissionAdapter(getApplicationContext(), R.layout.permission_list_item,
//                    0, R.id.permission_name_item, R.id.permission_description_item, permissions);
//            permissionListView.setAdapter(adapt);


//        permissionDialog = new DialogFragment(R.layout.permission_ui);
//        permissionDialog.show();
            // }
        }else {
            // permission check and dialog ends here
            setContentView(R.layout.main_activity);

            {
                Uri uri;
                if (Build.VERSION.SDK_INT >= 29) { // sdk>29 needs special permissions
                    uri = MediaStore.Audio.Media.getContentUri("external");
                } else {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                // Permission gathering
//            new File("Android/media");
//            Uri filesUri = DocumentsContract.buildDocumentUri("com.android.externalstorage.documents", "primary:Android/media/");
//            Uri buildTreeDocumentUri = DocumentsContract.buildTreeDocumentUri("com.android.externalstorage.documents", "primary:Android/media/");
//            if (filesUri.getAuthority() != null && filesUri.getAuthority() == "com.android.externalstorage.documents") {
//                new File(Environment.getExternalStorageDirectory(), filesUri.getPath().split(":", 2)[1]);
//            }
//            Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
//            startActivity(intent);
                // permissions gathering ends here

//                this.getTracks.getAllTracks(uri, getContentResolver());

                MediaController mediaController = new MediaController(getApplicationContext());
                LinearLayout miniPlayLayout = (LinearLayout) findViewById(R.id.miniPlayLayout);
                ListView tracksView = (ListView) findViewById(R.id.tracks);
                FloatingActionButton playButton = (FloatingActionButton) findViewById(R.id.playButton);
                FloatingActionButton nextButton = (FloatingActionButton) findViewById(R.id.nextButton);
                FloatingActionButton prevButton = (FloatingActionButton) findViewById(R.id.prevButton);
                ImageView albumArt = (ImageView) findViewById(R.id.albumArt);
                TextView currentTrackName = (TextView) findViewById(R.id.currentTrack);

//
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
//
//
                nextButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        mediaController.next();
                        playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_btn));
                        if (mediaController.getPosition() > -1) {
                            try {
                                int position = mediaController.getPosition();
                                if (position != -1) {
                                    currentTrackName.setText((getTracks.tracks.get(position)).getName());
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
                                    currentTrackName.setText((getTracks.tracks.get(position)).getName());
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });

                FrameLayout playSheet = (FrameLayout) findViewById(R.id.playSheet);

                playSheet.setClickable(true);
                BottomSheetBehavior playSheetBehaviour = BottomSheetBehavior.from(playSheet);
                playSheetBehaviour.setDraggable(true);

                ImageView arrow = (ImageView) findViewById(R.id.upArrow);

                arrow.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (playSheetBehaviour.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                            playSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                        } else {
                            playSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    }
                });

                LinearLayout playControls = (LinearLayout) findViewById(R.id.playControlsLayout);
                playSheetBehaviour.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    public void onStateChanged(View bottomSheet, int newState) {
                        if (playSheetBehaviour.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                            miniPlayLayout.setOrientation(LinearLayout.HORIZONTAL);
                            playControls.setGravity(GravityCompat.START);
                            playControls.setGravity(17);
                            currentTrackName.setGravity(GravityCompat.START);
                            return;
                        }
                        miniPlayLayout.setOrientation(LinearLayout.VERTICAL);
                        miniPlayLayout.setGravity(80);
                        currentTrackName.setGravity(17);
                        playControls.setGravity(17);
                    }

                    //
                    public void onSlide(View bottomSheet, float slideOffset) {
                        playButton.setCustomSize((int) ((110.0f * slideOffset) + 100.0f));
                        prevButton.setCustomSize((int) ((slideOffset * 90.0f) + 100.0f));
                        nextButton.setCustomSize((int) ((90.0f * slideOffset) + 100.0f));
                        arrow.setRotation(180.0f * slideOffset);
                    }
                });


                getTracks.getAllTracks(uri, getContentResolver());

                Track[] tracks = new Track[getTracks.tracks.size()];
                tracks = getTracks.tracks.toArray(tracks);
                TrackAdapter allTracksAdapter = new TrackAdapter(getApplicationContext(),
                        R.layout.tracks_list_item, R.id.album_art, R.id.track_name_item, tracks);
                tracksView.setAdapter(allTracksAdapter);

                AdapterView.OnItemClickListener onChooseListener = new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                        // pos = position;
                        try {
                            mediaController.playMulti(getTracks.trackUris, position);
                            currentTrackName.setText(getTracks.trackNames.get(mediaController.getPosition()));
                            if (getTracks.tracks.get(position).getAlbummArtUri()!= null) {

                                // NEEDS CLEANING
                                albumArt.setImageURI(null);

                                albumArt.setImageURI(getTracks.tracks.get(position).getAlbummArtUri());

                                File f = new File(getTracks.tracks.get(position).getAlbummArtUri().getPath()) ;
                                albumArt.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));

                                albumArt.setImageURI(getTracks.tracks.get(position).getAlbummArtUri());



                            } else {
                                albumArt.setImageURI(null);

                                albumArt.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_btn));
                            }
                        } catch (Exception e) {
//                            e.printStackTrace();
                            albumArt.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_bar));

//                            currentTrackName.setText(getTracks.tracks.get(position).getAlbummArtPath());
                        }
                    }
                };
                tracksView.setOnItemClickListener(onChooseListener);
                mediaController.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer player) {
                        playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.play_rectangle));
                    }
                });
            }

        }
        }

}

