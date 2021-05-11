package com.example.localuser.retrofittest.Utils;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import com.example.localuser.retrofittest.MyApplication;
import java.io.File;

public class FileStorageUtil {
    /**
     * 获取File目录
     * @return
     */
    public static File getDiskCacheDir() {
        File cacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cacheDir = MyApplication.getInstance().getExternalCacheDir();
        }

        if (cacheDir == null){
            cacheDir = MyApplication.getInstance().getCacheDir();
        }

        return cacheDir;
    }

    public static String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection老获取真实的图片路径
        Cursor cursor = MyApplication.getInstance().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
