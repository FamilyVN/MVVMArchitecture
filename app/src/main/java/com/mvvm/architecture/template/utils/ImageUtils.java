package com.mvvm.architecture.template.utils;

import android.content.Context;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.RequiresApi;

public class ImageUtils {
    private static final String GOOGLE_DRIVE_AUTHORITY = "com.google.android.apps.docs.storage";
    private static final String FORMAT_FILE_NAME = "yyyy-MM-dd_HH:mm:ss.SSS";
    private static final String TEMP_IMAGE_FILE_NAME = "temp_avatar.jpg";
    private static final String FOLDER_IMAGE = "Image";
    private static final String IMAGE_FILE_EXTENSION = ".jpg";

    @BindingAdapter("app:srcCompat")
    public static void bindSrcCompat(ImageView imageView, Drawable drawable) {
        Glide.with(imageView.getContext())
            .load(drawable)
            .into(imageView);
    }

    @BindingAdapter("app:icon")
    public static void setIcon(ImageView imageView, int drawable) {
        if (drawable == 0) return;
        imageView.setImageResource(drawable);
    }

    @BindingAdapter("app:icon")
    public static void setIcon(ImageView imageView, Drawable drawable) {
        if (drawable == null) return;
        imageView.setImageDrawable(drawable);
    }
    //

    public static String getCameraImagePath() {
        File cameraImage = createCacheImageFile();
        return compressImage(cameraImage);
    }

    public static String getPathTakeImage(String name) {
        File cameraImage = createFileTakeImage(name);
        return compressImage(cameraImage);
    }

    private static String compressImage(File imageTemp) {
        int orientation = getImageOrientation(imageTemp.getPath());
        if (!imageOrientationNormal(orientation)) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageTemp.getAbsolutePath());
            bitmap = rotateBitmap(bitmap, orientation);
            saveBitmapToFile(bitmap, imageTemp);
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        return imageTemp.getAbsolutePath();
    }

    private static boolean imageOrientationNormal(int orientation) {
        return orientation == ExifInterface.ORIENTATION_NORMAL
            || orientation == ExifInterface.ORIENTATION_UNDEFINED;
    }

    private static int getImageOrientation(String imagePath) {
        int orientation = 0;
        try {
            ExifInterface exif = new ExifInterface(imagePath);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orientation;
    }

    public static File createFileTakeImage(String name) {
        final File root = new File(Environment.getExternalStorageDirectory(), FOLDER_IMAGE);
        if (!root.exists()) {
            root.mkdirs();
        }
        return new File(root, name);
    }

    public static File createCacheImageFile(boolean override) {
        File file = createCacheImageFile();
        if (override && file.exists()) {
            file.delete();
        }
        return file;
    }

    public static File createCacheImageFile() {
        final File root = new File(Environment.getExternalStorageDirectory(), FOLDER_IMAGE);
        if (!root.exists()) {
            root.mkdirs();
        }
        return new File(root, TEMP_IMAGE_FILE_NAME);
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            return Bitmap
                .createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveBitmapToFile(Bitmap bitmap, File file) {
        if (bitmap == null) return;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //Compress bitmap into file
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            bitmap.recycle();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getImagePath(Context context, @NonNull Uri uri) {
        File imageTemp = createTempImage(context);
        String imagePath = getDataColumn(context, uri);
        if (imagePath != null) {
            if (copyToTempFile(imageTemp, new File(imagePath))) {
                return compressImage(imageTemp);
            }
        }
        try {
            InputStream inputStream;
            if (uri.getAuthority().contains(GOOGLE_DRIVE_AUTHORITY)) {
                //Image get from google drive
                inputStream = context.getContentResolver().openInputStream(uri);
            } else {
                inputStream = getSourceStream(context, uri);
            }
            if (copyToFile(imageTemp, inputStream)) {
                return compressImage(imageTemp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static File createTempImage(Context context) {
        final File root = new File(context.getExternalCacheDir(), FOLDER_IMAGE);
        if (!root.exists()) {
            root.mkdirs();
        }
        String fileName =
            new SimpleDateFormat(FORMAT_FILE_NAME, Locale.ROOT).format(new Date());
        return new File(root, fileName + IMAGE_FILE_EXTENSION);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String getDataColumn(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        String imagePath = null;
        Cursor cursor;
        int columnIndex;
        cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
            imagePath = cursor.getString(columnIndex);
            cursor.close();
        }
        if (imagePath != null) {
            return imagePath;
        }
        try {
            String wholeID = DocumentsContract.getDocumentId(uri);
            String id = wholeID.split(":")[1];
            String sel = MediaStore.Images.Media._ID + "=?";
            cursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, sel, new String[]{id}, null);
            if (cursor != null && cursor.moveToFirst()) {
                columnIndex = cursor.getColumnIndex(projection[0]);
                imagePath = cursor.getString(columnIndex);
                cursor.close();
            }
            if (imagePath != null) {
                return imagePath;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private static boolean copyToTempFile(File tempFile, File imageFile) {
        try {
            FileInputStream is = new FileInputStream(imageFile);
            FileOutputStream os = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static FileInputStream getSourceStream(Context context, Uri uri) {
        FileInputStream inputStream = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(uri, "r");
                if (parcelFileDescriptor != null) {
                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                    inputStream = new FileInputStream(fileDescriptor);
                }
            } else {
                inputStream = (FileInputStream) context.getContentResolver().openInputStream(uri);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    private static boolean copyToFile(File tempFile, InputStream inputStream) {
        if (inputStream == null || tempFile == null) return false;
        try {
            OutputStream out = new FileOutputStream(tempFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            inputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
