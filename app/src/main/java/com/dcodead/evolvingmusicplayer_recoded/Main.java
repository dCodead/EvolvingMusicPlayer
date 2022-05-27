package com.dcodead.evolvingmusicplayer_recoded;



import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;

public class Main extends AppCompatActivity {
    public static void main(String[] args){

    }



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
        }



        //Bottomsheet "playsheet"
        //BottomSheetBehavior playsheet = new BottomSheetBehavior(R.id./*put frame layout here child of coordinator*/);

    }
}
