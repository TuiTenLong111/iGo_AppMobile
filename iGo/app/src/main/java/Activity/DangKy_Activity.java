package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igo.R;

public class DangKy_Activity extends AppCompatActivity {
    EditText txtUserName,txtPassword,txtRePassword;
    Button cmdRegister;
    SQLiteDatabase database;
    TextView cmdGoToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        getSupportActionBar().hide();
        txtUserName = (EditText) findViewById(R.id.edt_username);
        txtPassword = (EditText) findViewById(R.id.edt_password);
        txtRePassword = (EditText) findViewById(R.id.edt_nhaplaipassword);
        cmdRegister = (Button) findViewById(R.id.btn_Register);
        cmdGoToLogin = (TextView)findViewById(R.id.cmdGoToLogin) ;
        txtUserName.requestFocus();
        database = openOrCreateDatabase("iGo_DB.db",MODE_PRIVATE,null);

        cmdRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("UserName", txtUserName.getText().toString().trim());
                contentValues.put("PassWord", txtPassword.getText().toString().trim());
                if (txtPassword.getText().equals("") || txtRePassword.getText().equals("") || txtUserName.getText().equals("")) {
                    Toast.makeText(DangKy_Activity.this, "This value is required !", Toast.LENGTH_SHORT).show();
                } else if (!txtPassword.getText().toString().trim().equals(txtRePassword.getText().toString().trim())) {
                    Toast.makeText(DangKy_Activity.this, "This value should be the same !", Toast.LENGTH_SHORT).show();
                } else {
                    if (database.insert("AccountUser", null, contentValues) == -1) {
                        Toast.makeText(DangKy_Activity.this, txtUserName.getText().toString().trim() + "is already a IGO account !", Toast.LENGTH_SHORT).show();
                    } else {
                        Cursor cursor = database.query("AccountUser", null, "UserName = ? and PassWord = ?", new String[]{txtUserName.getText().toString().trim(), txtPassword.getText().toString().trim()}, null, null, null);
                        cursor.moveToNext();
                        Intent intent = new Intent(DangKy_Activity.this, TrangChu_Activity.class);
                        intent.putExtra("IDUser", cursor.getInt(0));
                        Toast.makeText(DangKy_Activity.this, "Register Successful !", Toast.LENGTH_SHORT).show();
                        cursor.close();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        });

        cmdGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKy_Activity.this,DangNhap_Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}