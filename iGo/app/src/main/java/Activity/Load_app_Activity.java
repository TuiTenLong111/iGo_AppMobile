package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import com.example.igo.R;

public class Load_app_Activity extends AppCompatActivity {

    private static final int DELAY_TIME = 1000; // 3 giây
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_app);
        //setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


                // Tạo một đối tượng Handler
        Handler handler = new Handler();

        // Sử dụng Handler để chuyển đổi sang hoạt động mới sau 3 giây
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tạo một Intent để chuyển sang hoạt động mới
                Intent intent = new Intent(Load_app_Activity.this, DangNhap_Activity.class);
                startActivity(intent);

                // Đóng hoạt động hiện tại (nếu bạn muốn)
                finish();
            }
        }, DELAY_TIME);
    }
}