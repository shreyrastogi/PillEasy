package com.mc2022.template.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class FileUtilities {

    public static void saveMedicineImage(Context context, Bitmap imageBitmap, String imageFileName) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(imageFileName, Context.MODE_PRIVATE);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            Log.d("saveArticleImage", "Problem in Saving Image into File System");
            e.printStackTrace();
        } finally {
            fos.close();
        }
    }

    public static Bitmap loadMedicineImage(Context context, String imageFilePath) {
        Bitmap imageBitmap = null;
        FileInputStream fis;
        try {
            fis = context.openFileInput(imageFilePath);
            imageBitmap = BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (Exception e) {
            Log.d("loadArticleImage:", "Unable to Load Image from File System");
            e.printStackTrace();
        }
        return imageBitmap;
    }

    public static ArrayList<String> allFiles(Context context) {
        ArrayList<String> filenames = new ArrayList<String>();
        try {
            String[] fileList = context.fileList();

            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].endsWith(".json")) {
                    filenames.add(fileList[i]);
                }
            }
            Log.e("allFiles", String.valueOf(filenames));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("allFiles", "Unable to List all Files");
        }
        return filenames;
    }
}
