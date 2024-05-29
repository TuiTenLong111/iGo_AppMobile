package Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.igo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import Adapter.ItemTrangChuAdapter;
import Adapter.ViewPagerAdapterFragmentTrangChu;
import Canhan.DangXuat;
import Model.IdUser;
import Model.khachsan;
import fragment.Fragment_KhamPha;
import fragment.Fragment_TimKiem;
import fragment.Fragment_canhan;


public class TrangChu_Activity extends AppCompatActivity {

    String DB_PATH_SUFFIX = "/databases/";
    String DATABASE_NAME="iGo_DB.db";
    private ViewPager2 mViewPager2;
    private ViewPagerAdapterFragmentTrangChu mViewPagerAdapterFragmentTrangChu;

    public int getIDUser() {
        return IDUser;
    }

    public void setIDUser(int IDUser) {
        this.IDUser = IDUser;
    }

    int IDUser =-1;

    Toolbar mToolbar;

    IdUser idUser;

    ImageView miImageView;

    @Override
    protected void onRestart() {
        super.onRestart();
        mViewPager2 = findViewById(R.id.viewPageItem);
        mViewPagerAdapterFragmentTrangChu = new ViewPagerAdapterFragmentTrangChu(this);
        mViewPager2.setAdapter(mViewPagerAdapterFragmentTrangChu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        //setContentView(R.layout.layout_trangchutesst);
        getSupportActionBar().hide();
        processCopy();



        //lấy dữ liệu iduser kiểu int

        Intent intent = getIntent();

        setIDUser(intent.getIntExtra("IDUser",-1)); //T.Tuan
        idUser = new IdUser();
        idUser.setIdUser(intent.getIntExtra("IDUser",-1)); //lưu iduser vào biến toàn cục

//        Toast.makeText(this, "ID ở trang chủ " + idUser.getIdUser(), Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPref = this.getSharedPreferences("MyPreferences", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("IDUser", IDUser+"");
        editor.commit();

        mViewPager2 = findViewById(R.id.viewPageItem);
        mViewPagerAdapterFragmentTrangChu = new ViewPagerAdapterFragmentTrangChu(this);
        mViewPager2.setAdapter(mViewPagerAdapterFragmentTrangChu);
        BottomNavigationView navigationView;
        navigationView = findViewById(R.id.bottom_nav);
        mToolbar = findViewById(R.id.toolbartrangchu);

        miImageView = findViewById(R.id.Logout);
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_khampha:
                    mViewPager2.setCurrentItem(0); // Chuyển đến trang thứ nhất
                    mToolbar.setTitle("Khám phá");
                    return true;
                case R.id.action_timkiem:
                    mViewPager2.setCurrentItem(1); // Chuyển đến trang thứ hai
                    mToolbar.setTitle("Tìm kiếm");
                    return true;
                case R.id.action_luu:
                    mViewPager2.setCurrentItem(2); // Chuyển đến trang thứ ba
                    mToolbar.setTitle("Lưu trữ");
                    return true;
                case R.id.action_taikhoan:
                    mViewPager2.setCurrentItem(3); // Chuyển đến trang thứ ba
                    mToolbar.setTitle("Cá nhân");
                    return true;
                default:
                    mViewPager2.setCurrentItem(0); // Chuyển đến trang thứ nhất
                    mToolbar.setTitle("Khám phá");
                    return true;
            }
        });

        //nút đăng xuất
        miImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TrangChu_Activity.this);
                builder.setCancelable(true);
                builder.setTitle("Logout");
                builder.setMessage("Do you relly want to logout ?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(TrangChu_Activity.this, DangNhap_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
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
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}