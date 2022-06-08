package com.dcodead.evolvingmusicplayer_recoded;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class PermissionAdapter extends ArrayAdapter<Object> {
    private Context context;
    private Permission[] permissions;
    private int resource; // Resource of list item layout
    private int mainTextResourceId;
    private int descriptionTextResourceId;
    private int imageViewResourceId;
    public PermissionAdapter(@NonNull Context context, int resource, int imageViewResourceId, int mainTextViewResourceId,
                             int descriptionTextViewResourceId, @NonNull Permission[] permissions) {
        super(context, resource, permissions);
        this.context = context;
        this.permissions = permissions;
        this.resource = resource;
        mainTextResourceId = mainTextViewResourceId;
        descriptionTextResourceId = descriptionTextViewResourceId;
        this.imageViewResourceId = imageViewResourceId;

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.permission_list_item, parent, false);
            listItem = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Permission permission = permissions[position];

        // ImageView
        if(imageViewResourceId!=0) {
            ImageView itemImage = (ImageView) listItem.findViewById(imageViewResourceId);
            itemImage.setImageDrawable(permission.getImage()); // Mostly will be wrong
        }
        // Main TextView
        if(mainTextResourceId!=0) {
            TextView itemName = (TextView) listItem.findViewById(mainTextResourceId);
            itemName.setText(permission.getName());
        }
        // Description TextView
        if(descriptionTextResourceId!=0) {
            TextView itemDescription = (TextView) listItem.findViewById(descriptionTextResourceId);
            itemDescription.setText(permission.getDescription());
        }

        return listItem;
    }
}