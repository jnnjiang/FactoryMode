package com.ff.factoryimage.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;

public class ImageUtil {
    private static final String TAG = "ImageTestImageTest";

    public static Bitmap getBitmapFromPath(Context context, String path) {
        return decodeAndCompressBitmap(context,path);
    }
    public static Bitmap getBitmapFromPath(Context context, String path,boolean compress) {
        if(compress) {
            return decodeAndCompressBitmap(context, path);
        }else {
            return decodeBitmap(context,path);
        }
    }

    private static Bitmap decodeBitmap(Context context,String path){
        return BitmapFactory.decodeFile(path);
    }

    //I added this to have a good approximation of the screen size:
    private static Bitmap decodeAndCompressBitmap(Context context,String pathName) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return decodeSampledBitmap(pathName, width, height);
    }

    private static int calculateInSampleSize(
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
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private static Bitmap decodeSampledBitmap(String pathName,
                                       int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }
}