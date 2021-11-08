package com.kuma.kangaroof;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.kuma.base.KumaBaseActivity;
import com.kuma.base.util.CameraUtils;
import com.kuma.kangaroof.business.weather.ui.weather.WeatherActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.kuma.base.util.CameraUtils.REQUEST_TAKE_PHOTO_NO_FILE;
import static com.kuma.base.util.CameraUtils.REQUEST_TAKE_PHOTO_WITH_FILE;

public class MainActivity extends KumaBaseActivity {

    private boolean drawerOpened = true;

    String currentPhotoPath;
    boolean saveToLocal;

    AppBarLayout appBarLayout;
    Toolbar toolbar;

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    ImageView bgTop;
    ImageView bgBottom;

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
        appBarLayout = findViewById(R.id.main_appBar);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        button1 = findViewById(R.id.main_button_1);
        button2 = findViewById(R.id.main_button_2);
        button3 = findViewById(R.id.main_button_3);
        button4 = findViewById(R.id.main_button_4);
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
        button4.setOnClickListener(onClickListener);
        button3.setOnLongClickListener(onLongClickListener);

        bgBottom = findViewById(R.id.main_bottom_bg);

    }

    private View.OnClickListener onClickListener = v -> {
        if (v == button1) {
            startActivity(new Intent(this, WeatherActivity.class));
        }
        if (v == button2) {
            startActivity(new Intent(this, LeafActivity.class));
        }
        if (v == button3) {
            currentPhotoPath = CameraUtils.takePhoto(saveToLocal, this);
        }
        if (v == button4) {
            startActivity(new Intent(this, PlayerActivity.class));
        }
    };

    private final View.OnLongClickListener onLongClickListener = v -> {
        if (v == button3) {
            saveToLocal = !saveToLocal;
            Toast.makeText(MainActivity.this, saveToLocal + "", Toast.LENGTH_SHORT).show();
        }
        return true;
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerOpened) {
                appBarLayout.setExpanded(false);
                drawerOpened = false;
            } else {
                appBarLayout.setExpanded(true);
                drawerOpened = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO_NO_FILE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bgBottom.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_TAKE_PHOTO_WITH_FILE && resultCode == RESULT_OK) {
            try {
                FileInputStream fis = new FileInputStream(currentPhotoPath);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                bgBottom.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}