package CopyData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyDataFromAssets {
    private static Context context;

    static String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    static String DATABASE_NAME="iGo_DB.db";

    public CopyDataFromAssets(String DATABASE_NAME) {
        this.DATABASE_NAME = DATABASE_NAME;
    }

    public CopyDataFromAssets(Context context) {
        this.context = context;
    }

    public static void processCopy() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try{CopyDataBaseFromAsset();
                Toast.makeText(context, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @NonNull
    public static String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }


    public static void CopyDataBaseFromAsset() {

        try {
            InputStream myInput;
            myInput = context.getAssets().open(DATABASE_NAME);

            String outFileName = getDatabasePath();

            File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();

            OutputStream myOutput = new FileOutputStream(outFileName);

            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
