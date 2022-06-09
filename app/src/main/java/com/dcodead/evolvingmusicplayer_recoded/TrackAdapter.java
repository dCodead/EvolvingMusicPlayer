package com.dcodead.evolvingmusicplayer_recoded;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;

class TrackAdapter extends ArrayAdapter<Object> {
    private Context context;
    private Track[] tracks;
    private int resource; // Resource of list item layout
    private int mainTextResourceId;
    private int descriptionTextResourceId;
    private int imageViewResourceId;
    private ImageView moreOptions;
    public TrackAdapter(@NonNull Context context, int resource, int imageViewResourceId, int mainTextViewResourceId,
                        @NonNull Track[] tracks) {
        super(context, resource, tracks);
        this.context = context;
        this.tracks = tracks;
        this.resource = resource;
        mainTextResourceId = mainTextViewResourceId;
//        descriptionTextResourceId = descriptionTextViewResourceId;
        this.imageViewResourceId = imageViewResourceId;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.tracks_list_item, parent, false);
            listItem = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Track track = tracks[position];

        // ImageView
        if(imageViewResourceId!=0) {
            ImageView itemImage = listItem.findViewById(imageViewResourceId);
            Uri uri = track.getAlbummArtUri();
            if(uri!=null){
                Bitmap bit = null;
                try{
                    bit = MediaStore.Images.Media.getBitmap(context.getContentResolver(),
                            uri);
                    bit = Bitmap.createScaledBitmap(bit,30, 30 ,true);
                }catch(FileNotFoundException e){
                    bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_bar);
                }catch(IOException e1){

                }
                itemImage.setImageBitmap(bit);

            }
        }
        // Main TextView
        if(mainTextResourceId!=0) {
            TextView itemName = (TextView) listItem.findViewById(mainTextResourceId);
            itemName.setText(track.getName());
            itemName.setTextColor(Color.GRAY);
        }


        //Needs work here for more options button
        moreOptions = listItem.findViewById(R.id.more_options);
        moreOptions.setClickable(true);
        // Description TextView
//        if(descriptionTextResourceId!=0) {
//            TextView itemDescription = (TextView) listItem.findViewById(descriptionTextResourceId);
//            itemDescription.setText(track.getDescription());
//        }

        return listItem;
    }
}
