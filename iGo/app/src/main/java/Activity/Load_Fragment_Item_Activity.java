package Activity;

import Adapter.ItemTrangChuAdapter;
import Model.khachsan;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.igo.R;
import Adapter.ViewPageAdapterFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class Load_Fragment_Item_Activity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private ViewPageAdapterFragment mViewPageAdapterFragment;


    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    String DATABASE_NAME="iGo_DB.db";

    ViewFlipper viewFlipper;
    private List<khachsan> list;
    private RecyclerView rcvKS;
    private ItemTrangChuAdapter ksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_item);
        getSupportActionBar().setTitle("Danh sách");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4E6CFE")));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //gọi hàm copy database vào assets processCopy();
        processCopy();

        mTabLayout = findViewById(R.id.tablayout_item);
        mViewPager = findViewById(R.id.viewPageItem);

        mViewPageAdapterFragment = new ViewPageAdapterFragment(this);
        mViewPager.setAdapter(mViewPageAdapterFragment);

        // Lấy dữ liệu truyền từ Activity_TrangChu
        int selectedFragment = getIntent().getIntExtra("selectedFragment", 4);
        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Coffee");
                    break;
                case 1:
                    tab.setText("Địa điểm");
                    break;
                case 2:
                    tab.setText("Khách sạn");
                    break;
                case 3:
                    tab.setText("Nhà hàng");
                    break;
                case 4:
                    tab.setText("Giải trí");
                    break;
            }
        }).attach();
        // gọi
        mViewPager.setCurrentItem(selectedFragment - 1);
    }

    //nút quay lại trên thanh actionbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed(); // Xử lý sự kiện khi nút quay lại được nhấn
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //két nối data
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