package Canhan;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.igo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Model.IdUser;

public class activity_personal_info extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    String DATABASE_NAME="iGo_DB.db";

    int IdUser;
    Model.IdUser mIdUser;

    TextView editTextId,fullname,edt_PhoneNumber,editTextDateOfBirth,editTextEmail;
    Button btn_chinhsua,btn_thoat;
    ImageView img_hinh;

    Bitmap img  = null ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        processCopy();

        //ÁNH Xạ

        btn_thoat = findViewById(R.id.btn_thoat);
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity và thoát ứng dụng
            }
        });

        IdUser = mIdUser.getIdUser();

        editTextId = findViewById(R.id.editTextId);
        fullname = findViewById(R.id.fullname);
        edt_PhoneNumber = findViewById(R.id.edt_PhoneNumber);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        editTextEmail = findViewById(R.id.editTextEmail);
        img_hinh = findViewById(R.id.img_hinh);
        showUserInfor(IdUser);
    }

    private void showUserInfor(int id){
        database = this.openOrCreateDatabase("iGo_DB.db",MODE_PRIVATE, null);
        String sql = "SELECT * FROM InforUser WHERE IDAccount =  " + id ;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        Toast.makeText(this, "ffffffffffffffffffff", Toast.LENGTH_SHORT).show();
        editTextId.setText("" +id);
        String Fullname = cursor.getString(2)+cursor.getString(3)+cursor.getString(4);
        fullname.setText(Fullname);
        String Date = cursor.getString(1);
        editTextDateOfBirth.setText(Date);
        String Number = cursor.getString(5);
        Toast.makeText(this, "aaaaaaaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
        edt_PhoneNumber.setText(Number);
        String Gmail = cursor.getString(6);
        editTextEmail.setText(Gmail);
        Toast.makeText(this, "aaaaaaaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
        img = converPath(this, "ImageUser/" + cursor.getString(8));
        img_hinh.setImageBitmap(img);
        cursor.close();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back button click event
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try{CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }
    @NonNull
    private String getDatabasePath() {
        //Toast.makeText(this, getDatabasePath().toString(), Toast.LENGTH_LONG);
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }

    public void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);

            String outFileName = getDatabasePath();

            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
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