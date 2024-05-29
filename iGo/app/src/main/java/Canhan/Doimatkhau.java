package Canhan;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.igo.R;

public class Doimatkhau extends AppCompatActivity {
    //private DatabaseHelper databaseHelper;
    private EditText mEditTextCurrentPassword;
    private EditText mEditTextNewPassword;
    private EditText mEditTextConfirmNewPassword;
    private Button mButtonChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);

       mEditTextCurrentPassword = findViewById(R.id.edit_text_current_password);
        mEditTextNewPassword = findViewById(R.id.edit_text_new_password);
        mEditTextConfirmNewPassword = findViewById(R.id.edit_text_confirm_new_password);
        mButtonChangePassword = findViewById(R.id.btn_pass);
        // thanh thoát ra
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị của các trường thông tin
                String currentPassword = mEditTextCurrentPassword.getText().toString();
                String newPassword = mEditTextNewPassword.getText().toString();
                String confirmNewPassword = mEditTextConfirmNewPassword.getText().toString();
                // Kiểm tra tính hợp lệ của các trường thông tin
                if (TextUtils.isEmpty(currentPassword)) {
                    mEditTextCurrentPassword.setError("Vui lòng nhập mật khẩu hiện tại");
                    return;
                }

                if (TextUtils.isEmpty(newPassword)) {
                    mEditTextNewPassword.setError("Vui lòng nhập mật khẩu mới");
                    return;
                }

                if (TextUtils.isEmpty(confirmNewPassword)) {
                    mEditTextConfirmNewPassword.setError("Vui lòng xác nhận mật khẩu mới");
                    return;
                }

                if (!newPassword.equals(confirmNewPassword)) {
                    mEditTextConfirmNewPassword.setError("Mật khẩu mới và xác nhận mật khẩu mới không trùng khớp");
                    return;
                }

                // Thực hiện đổi mật khẩu
                // ...
                Toast.makeText(Doimatkhau.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
        // thanh thoát ra
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back button click event
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
