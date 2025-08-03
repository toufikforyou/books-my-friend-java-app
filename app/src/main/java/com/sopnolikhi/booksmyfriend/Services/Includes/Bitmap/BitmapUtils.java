package com.sopnolikhi.booksmyfriend.Services.Includes.Bitmap;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {
    private static final long SIZE_LIMIT = 5000000;// 191000000;

    public static Bitmap loadBitmapFromUri(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        return BitmapFactory.decodeStream(inputStream);
    }


    public static boolean isBitmapSizeExceedsLimit(Bitmap bitmap) {
        long imageSize = (long) bitmap.getWidth() * bitmap.getHeight() * 4; // Assuming RGBA format
        return imageSize > SIZE_LIMIT;
    }

    public static String getRealPath(Context context, Uri fileUri) {
        String realPath = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            realPath = getRealPathFromMediaStore(context, fileUri);
        }

        if (realPath.isEmpty()) {
            String mimeType = context.getContentResolver().getType(fileUri);
            if (mimeType != null) {
                String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                if (extension != null) {
                    realPath = getPathForFileType(context, fileUri, mimeType);
                }
            }
        }

        return realPath;
    }

    private static String getRealPathFromMediaStore(Context context, Uri fileUri) {
        String path = "";
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(fileUri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }


    private static String getPathForFileType(Context context, Uri fileUri, String mimeType) {
        String path = "";
        if (mimeType.startsWith("image")) {
            // Process image files
            path = getImagePath(context, fileUri);
        } else if (mimeType.startsWith("audio")) {
            // Process audio files
            path = getAudioPath(context, fileUri);
        } else if (mimeType.startsWith("video")) {
            // Process video files
            path = getVideoPath(context, fileUri);
        } else if (mimeType.equals("application/pdf")) {
            // Process PDF files
            path = getPDFPath(context, fileUri);
        } else if (mimeType.equals("text/html")) {
            // Process HTML files
            path = getHTMLPath(context, fileUri);
        } else {
            // Handle other file types
            path = getOtherFilePath(context, fileUri);
        }
        return path;
    }

    private static String getImagePath(Context context, Uri fileUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(fileUri, projection, null, null, null);
        String path = "";
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.moveToFirst()) {
                path = cursor.getString(column_index);
            }
            cursor.close();
        }
        return path;
    }

    private static String getAudioPath(Context context, Uri fileUri) {
        String[] projection = {MediaStore.Audio.Media.DATA};
        Cursor cursor = context.getContentResolver().query(fileUri, projection, null, null, null);
        String path = "";
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            if (cursor.moveToFirst()) {
                path = cursor.getString(column_index);
            }
            cursor.close();
        }
        return path;
    }

    private static String getVideoPath(Context context, Uri fileUri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = context.getContentResolver().query(fileUri, projection, null, null, null);
        String path = "";
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            if (cursor.moveToFirst()) {
                path = cursor.getString(column_index);
            }
            cursor.close();
        }
        return path;
    }

    private static String getPDFPath(Context context, Uri fileUri) {
        String[] projection = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = context.getContentResolver().query(fileUri, projection, null, null, null);
        String path = "";
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            if (cursor.moveToFirst()) {
                path = cursor.getString(column_index);
            }
            cursor.close();
        }
        return path;
    }

    private static String getHTMLPath(Context context, Uri fileUri) {
        // For HTML files, the path can directly be obtained from the file URI
        return fileUri.getPath();
    }

    private static String getOtherFilePath(Context context, Uri fileUri) {
        // For other file types, retrieve the path using the file URI
        return fileUri.getPath();
    }

    public static Bitmap resizeBitmap(Bitmap bitmap) {
        int newWidth = bitmap.getWidth() / 2;
        int newHeight = bitmap.getHeight() / 2;
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
    }

    public static Bitmap[] splitBitmap(Bitmap bitmap) {
        int splitWidth = bitmap.getWidth() / 2;
        int splitHeight = bitmap.getHeight() / 2;
        Bitmap[] subBitmaps = new Bitmap[4];

        for (int y = 0; y < bitmap.getHeight(); y += splitHeight) {
            for (int x = 0; x < bitmap.getWidth(); x += splitWidth) {
                int subImageWidth = Math.min(splitWidth, bitmap.getWidth() - x);
                int subImageHeight = Math.min(splitHeight, bitmap.getHeight() - y);
                Bitmap subBitmap = Bitmap.createBitmap(bitmap, x, y, subImageWidth, subImageHeight);
                subBitmaps[(y / splitHeight) * 2 + (x / splitWidth)] = subBitmap;
            }
        }

        return subBitmaps;
    }
}