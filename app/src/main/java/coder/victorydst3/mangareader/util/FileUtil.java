package coder.victorydst3.mangareader.util;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHlb on 1/21/16.
 */
public final class FileUtil {
    private static final String APP_NAME = "MangaReader";
    private static final String TAG = FileUtil.class.getSimpleName();

    private FileUtil() {
        // No instance
    }

    /**
     * @return root directory of photo
     */
    public static File createPhotoFile(String subFolderName) {

        File dir = new File(Environment.getExternalStorageDirectory(), APP_NAME + "/" + subFolderName);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new RuntimeException("Can't create folder " + dir.toString());
            }
        }

        return new File(dir, String.format("Image%d.jpg", new Date().getTime()));
    }

    /**
     * Get path from URI get from gallery and from folder which create by app
     * http://stackoverflow.com/questions/3401579/get-filename-and-path-from-uri-from-mediastore
     */
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(columnIndex);
            cursor.close();
            return result;
        }
        return contentUri.getPath();
    }

    /**
     * This method is used to delete a file.
     */
    public static void deleteFile(Context context, String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                Log.d(TAG, "deleteFile - success");
            } else {
                Log.d(TAG, "deleteFile - failure");
            }
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        }
    }
}
