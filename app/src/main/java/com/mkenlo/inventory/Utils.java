package com.mkenlo.inventory;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static Bitmap decodeItemImage(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    public static String encodeItemImage(Bitmap bitmap) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, output);
        byte[] b = output.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}
