package com.example.igo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class Photo {
    private Bitmap image;

    public Photo(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public static Bitmap convertStringToBitmapFromAccess(Context context, String filename)
    {
        AssetManager assetManager = context.getAssets();
        try{
            InputStream is = assetManager.open(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
