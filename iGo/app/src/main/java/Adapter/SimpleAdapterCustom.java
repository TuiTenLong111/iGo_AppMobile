package Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class SimpleAdapterCustom extends SimpleAdapter {
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     *                 in the from parameter.
     */
    public SimpleAdapterCustom(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public void setViewImage(ImageView v, String value) {
        super.setViewImage(v, value);
        Bitmap bm = converPath(v.getContext(), value);
        v.setImageBitmap(bm);
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
