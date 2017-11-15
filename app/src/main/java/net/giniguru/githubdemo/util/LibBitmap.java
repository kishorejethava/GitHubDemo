package net.giniguru.githubdemo.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class LibBitmap {
    private static String TAG="LibBitmap";
    public LibBitmap() {
        // TODO Auto-generated constructor stub
    }
    /**
     * Store Image in SD Card at specific location at Application Name folder
     * @param imageName image name
     * @param bitmap image bitmap
     */
    public static String saveBitmapInSDCard(Bitmap bitmap, String filePathDir, String imageName) {
        File fileDir = new File(filePathDir);
        if (!fileDir.exists())
            fileDir.mkdirs();

        String imagePath = fileDir.toString() + File.separator + imageName + ".jpg";
        Log.i(TAG,"image path:"+imagePath);
        try {
            FileOutputStream fos = new FileOutputStream(imagePath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            // compressing & creating PNG from bitmap
            bitmap.compress(CompressFormat.JPEG, 30, bos);
            bos.flush();
            bos.close();

            return imagePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagePath;
    }
    /**
     * get bitmap image from SD Card
     *
     * @return bitmap
     */
    public static Bitmap getBitmapImageFromSDCard(File file) {
        if (file != null) {
            if (file.exists()) {
                try {
                    return BitmapFactory.decodeStream(new FileInputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
        }
        return null;
    }
    public static void deleteImageFile(File dir) {

        if(dir.exists()) {
            if (dir.isDirectory()){
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++){
                    boolean isDeleted = new File(dir, children[i]).delete();
                    Log.i(TAG, "file :" + " isDeleted?" + isDeleted);
                }
            }
        }
    }

    /**
     * calculate in sample size to loading large bitmap efficiently
     * @param options options
     * @param reqWidth reqWidth
     * @param reqHeight reqHeight
     * @return
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Bitmap bitmap,
                                                         BufferedOutputStream bos, int reqWidth, int reqHeight) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteArray,0,byteArray.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return  BitmapFactory.decodeByteArray(byteArray,0,byteArray.length, options);
    }
}
