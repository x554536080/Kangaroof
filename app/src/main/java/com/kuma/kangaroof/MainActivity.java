package com.kuma.kangaroof;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.kuma.base.KumaBaseActivity;


public class MainActivity extends KumaBaseActivity {

    private boolean drawerOpened = true;

    String currentPhotoPath;
    boolean saveToLocal;

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        init();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void init() {
        char[] a = new char[5];
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_TAKE_PHOTO_NO_FILE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            bgBottom.setImageBitmap(imageBitmap);
//        } else if (requestCode == REQUEST_TAKE_PHOTO_WITH_FILE && resultCode == RESULT_OK) {
//            try {
//                FileInputStream fis = new FileInputStream(currentPhotoPath);
//                Bitmap bitmap = BitmapFactory.decodeStream(fis);
//                bgBottom.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
    }
}