package com.kuma.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraUtils {

    public static final int REQUEST_TAKE_PHOTO_NO_FILE = 1000;
    public static final int REQUEST_TAKE_PHOTO_WITH_FILE = 1001;

    public static String takePhoto(boolean saveToFile, Activity activity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {

            if (saveToFile) {
                String filePath = "";
                File photoFile = null;
                try {
                    photoFile = createImageFile(activity);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    filePath  = photoFile.getAbsolutePath();
                    Uri photoURI = FileProvider.getUriForFile(activity,
                            "com.kuma.kangaroof.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_WITH_FILE);
                }
                return filePath;
            } else {
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_NO_FILE);
                return "";
            }

        }
        return "";
    }


    private static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
}
