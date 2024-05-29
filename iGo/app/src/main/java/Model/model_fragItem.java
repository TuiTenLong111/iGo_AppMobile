package Model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class model_fragItem {

    private String iditem, tenks;
    private double diemks;
    private Bitmap imgks;

    public model_fragItem(String iditem, String tenks, double diemks, Bitmap imgks) {
        this.iditem = iditem;
        this.tenks = tenks;
        this.diemks = diemks;
        this.imgks = imgks;
    }

    public String getIditem() {
        return iditem;
    }

    public void setIditem(String iditem) {
        this.iditem = iditem;
    }

    public String getTenks() {
        return tenks;
    }

    public void setTenks(String tenks) {
        this.tenks = tenks;
    }

    public double getDiemks() {
        return diemks;
    }

    public void setDiemks(double diemks) {
        this.diemks = diemks;
    }

    public Bitmap getImgks() {
        return imgks;
    }

    public void setImgks(Bitmap imgks) {
        this.imgks = imgks;
    }

    public static Bitmap converPath (Context context, String pathimg){
        AssetManager assetManager = context.getAssets();
        try{
            InputStream is = assetManager.open(pathimg);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        }catch (IOException e){
            e.printStackTrace();
        }
        return  null;
    }
}
