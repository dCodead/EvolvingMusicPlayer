package com.dcodead.evolvingmusicplayer_recoded;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;

public class Main extends AppCompatActivity {
    public static void main(String[] args) {

    }

    DialogFragment permissionDialog;
    ListView permissionListView;
    getTracks getTracks = new getTracks();

    protected void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        setContentView(R.layout.main_activity);
//        setContentView(R.layout.permission_ui);
//
//        //Permission check and dialog area
//        // if (no permission){
//        permissionDialog = new DialogFragment(R.layout.permission_ui);
////        permissionDialog.show();
//        String[] permissionsArray = new String[]{"Read External Storage", "Write External Storage"};
//        String[] permissionDescArray = new String[]{"Used to search for audio tracks.", "Used to store playlists"};
//
//        permissionListView = findViewById(R.id.permission_list);
//
//            ArrayAdapter adapt = new ArrayAdapter(getApplicationContext(), R.layout.permission_list_item
//                    ,R.id.permission_name_item, permissionsArray);
//        permissionListView.setAdapter(adapt);
//
//    }
//}
//        ArrayAdapter<String> permissionAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.permission_ui, permissionListView, permissionsArray);
//        permissionDialog = new DialogFragment(R.layout.permission_ui);
//        permissionDialog.show();
        // }
        // permission check and dialog ends here

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

            //(this.getTracks).getAllTracks(uri, getContentResolver());
//
            MediaController mediaController = new MediaController(getApplicationContext());
            LinearLayout miniPlayLayout = (LinearLayout) findViewById(R.id.miniPlayLayout);
            ListView tracksView = (ListView) findViewById(R.id.tracks);
            FloatingActionButton playButton = (FloatingActionButton) findViewById(R.id.playButton);
            FloatingActionButton nextButton = (FloatingActionButton) findViewById(R.id.nextButton);
            FloatingActionButton prevButton = (FloatingActionButton) findViewById(R.id.prevButton);
            ImageView albumArt = (ImageView) findViewById(R.id.albumArt);
            TextView currentTrackName = (TextView) findViewById(R.id.currentTrack);
//
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

            playSheet.setClickable(true);
            BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(playSheet);

            ImageView arrow = (ImageView) findViewById(R.id.upArrow);
            sheetBehavior.setDraggable(true);
            arrow.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (sheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            });
            LinearLayout playControls = (LinearLayout) findViewById(R.id.playControlsLayout);
//            TextView textView = currentTrackName;
//            BottomSheetBehavior sheetBehavior2 = sheetBehavior;
//            TextView currentTrackName2 = currentTrackName;
            //needs cleaning doesn't wwork
            BottomSheetBehavior playsheet = new BottomSheetBehavior() {
                public void onStateChanged(View bottomSheet, int newState) {
                    if (sheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
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
            };
//            sheetBehavior.addBottomSheetCallback(playsheet);
//
            //problem nextline.
//            getTracks.getAllTracks(uri, getContentResolver());
            ArrayAdapter allTrackNamesAdapter = new ArrayAdapter(getApplicationContext(),
            R.layout.tracks_list_item, R.id.track_name_item, getTracks.trackNames);
            tracksView.setAdapter(allTrackNamesAdapter);

//            ArrayList<String> trackNames = getTracks.trackNames;
            ArrayAdapter arrayAdapter = allTrackNamesAdapter;
            AdapterView.OnItemClickListener onChooseListenedr = new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                   // pos = position;
                    try {
                        mediaController.playMulti(getTracks.trackUris, position);
                        currentTrackName.setText(getTracks.trackNames.get(mediaController.getPosition()));
                        if (!getTracks.trackImagePaths.get(position).equals("-1")) {
                            albumArt.setImageBitmap(BitmapFactory.decodeFile(getTracks.trackImagePaths.get(position)));
                        } else {
                            albumArt.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_btn));
                        }
                        //becones pausevb
                        playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_btn));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
//            tracksView.setOnItemClickListener(onChooseListenedr);
//            mediaController2.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                public void onCompletion(MediaPlayer player) {
//                    playButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.play_rectangle));
//                }
//            });
        }



    }
}

class customPermissionAdapter extends ArrayAdapter{

    public customPermissionAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
