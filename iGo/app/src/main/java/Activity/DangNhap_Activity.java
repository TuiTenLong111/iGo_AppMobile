package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DangNhap_Activity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    String DATABASE_NAME="iGo_DB.db";
    EditText txtUserName,txtPassword;
    Button cmdLogin;
    SQLiteDatabase database;
    TextView cmdRegister,cmdForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        processCopy();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        getSupportActionBar().hide();
        txtUserName = (EditText) findViewById(R.id.edt_username);
        txtPassword = (EditText) findViewById(R.id.edt_password);
        cmdLogin = (Button) findViewById(R.id.btn_login);
        cmdRegister = (TextView)findViewById(R.id.txtRegister);
        cmdForgotPassword = (TextView)findViewById(R.id.txtForgotPassword);
        txtUserName.requestFocus();
        database = openOrCreateDatabase("iGo_DB.db",MODE_PRIVATE,null);
        cmdLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cursor cursor = database.query("AccountUser",null,"UserName = ? and PassWord = ?",new String[]{txtUserName.getText().toString().trim(),txtPassword.getText().toString().trim()},null,null,null);
                    cursor.moveToNext();
                    if (cursor.isNull(0)) {}
                    else
                    {
                        Intent intent = new Intent(DangNhap_Activity.this,TrangChu_Activity.class);
                        intent.putExtra("IDUser",cursor.getInt(0));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                    cursor.close();
                }catch (Exception e)
                {
                    Toast.makeText(DangNhap_Activity.this, "Your account or password is incorrect !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cmdRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap_Activity.this, DangKy_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        cmdForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DangNhap_Activity.this);
                builder.setCancelable(true);
                builder.setTitle("Forgot Password");
                builder.setMessage("Relax and try to remember your password !");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onRestart() {
        txtUserName.requestFocus();
        txtUserName.setText("");
        txtPassword.setText("");
        super.onRestart();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
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
    @NonNull
    private String getDatabasePath() {
        //Toast.makeText(this, getDatabasePath().toString(), Toast.LENGTH_LONG);
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }
}