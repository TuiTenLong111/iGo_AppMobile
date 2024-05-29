package Model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class khachsan  {
    private String idItem, title;
    private Bitmap img;
    private double diem;

    public khachsan(String idItem, String title, Bitmap img, double diem) {
        this.idItem = idItem;
        this.title = title;
        this.img = img;
        this.diem = diem;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }

    public static Bitmap converPath (Context context, String pathimg){
        AssetManager assetManager = context.getAssets();
        try{
            InputStream is = assetManager.open(pathimg);
            Bitmap bitmap = (Bitmap) BitmapFactory.decodeStream(is);
            return bitmap;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
