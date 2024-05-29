package com.example.igo;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import Model.IdUser;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    //khai báo db
    String DB_PATH_SUFFIX = "/databases/"; // Thư mục chứa csdl trong bộ nhớ điện thoại
    SQLiteDatabase database=null; // Tên csdl
    String DATABASE_NAME="iGo_DB.db"; // Tên file chứa csdl


    ViewPager viewPager;
    CircleIndicator circleIndicator;
    PhotoAdapter photoAdapter;
    TextView tv_ThanhPho, tv_web, tv_sdt, tv_email, tv_path;


    TextView tv_NameItem, tv_Desc, tv_Diachi;
    RatingBar rb_ratingbar1, rb_ratingbar;


    ImageView img_view;
    Button btn_danhgia;

    ListView lvComment;
    //-------------------------Khai báo các list------------------------------
    ArrayList<UserComment> listUserComment =new ArrayList<UserComment>(); //ds các comment trog item
    String listImageWallpaper = "";

    //--------------------------------------------------------------
    UserCommentAdapter userCommentAdapter;


    String idItem;
    int IDUser;
    IdUser mIdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

        IDUser = mIdUser.getIdUser();
        //Toast.makeText(this, "ID nè " + IDUser, Toast.LENGTH_SHORT).show();

        idItem = getIntent().getStringExtra("idItem");
        if(idItem == null)
        {
            idItem = "21";
        }

        //hiển thị ảnh nền(viewPager)
        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);


        //textview click
        tv_web = findViewById(R.id.tv_web);
        tv_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Load Web", Toast.LENGTH_SHORT).show();
            }
        });

        //text
        //mấy th này truyền dữ liệu từ db
        tv_NameItem = findViewById(R.id.tv_NameItem);
        tv_Desc = findViewById(R.id.tv_Desc);
        tv_Diachi = findViewById(R.id.tv_DiaChi);
        rb_ratingbar1 = findViewById(R.id.rb_ratingbar1);
        rb_ratingbar = findViewById(R.id.rb_ratingbar);

        tv_ThanhPho = (TextView) findViewById(R.id.tv_ThanhPho);
        tv_ThanhPho.setPaintFlags(tv_ThanhPho.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tv_web = findViewById(R.id.tv_web);
        tv_web.setPaintFlags(tv_web.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tv_sdt = findViewById(R.id.tv_sdt);
        tv_sdt.setPaintFlags(tv_sdt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tv_email = findViewById(R.id.tv_email);
        tv_email.setPaintFlags(tv_email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tv_path = findViewById(R.id.tv_path);
        img_view = findViewById(R.id.img_view);

        lvComment=(ListView) findViewById(R.id.lv_comment);

        //button
        btn_danhgia = findViewById(R.id.btn_danhgia);

        btn_danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommentActivity.class);
                intent.putExtra("IDUser", IDUser);
                intent.putExtra("idItem", idItem);
                Toast.makeText(MainActivity.this, idItem, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        processCopy();
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        //load du lieu item
        imageItem(idItem);
        data1Item(idItem);
        layAddress(idItem);

        //comment cua user
        dataComment(idItem);

        userCommentAdapter = new UserCommentAdapter(MainActivity.this, R.layout.item_user, listUserComment);
        lvComment.setAdapter(userCommentAdapter);

        photoAdapter = new PhotoAdapter(this, getListPhoto());
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

    }
//-------main ở trên ^---
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //back to main
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(requestCode==1 && resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            String[] filepath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filepath, null, null, null);
            cursor.moveToFirst();
            int columneIndex = cursor.getColumnIndex(filepath[0]);
            String picturepath = cursor.getString(columneIndex);
            Toast.makeText(MainActivity.this, picturepath, Toast.LENGTH_SHORT).show();
            cursor.close();
            img_view.setImageBitmap(BitmapFactory.decodeFile(picturepath));
            String filename = picturepath.substring((picturepath.lastIndexOf("/")) + 1);
            tv_path.setText(picturepath);
        }
    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_top, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_save:
                //id item(của thằng hiện tại vs id user(lúc đăng nhập có)
                SharedPreferences sharedPref = this.getSharedPreferences("MyPreferences", this.MODE_PRIVATE);
                String IdAccout = sharedPref.getString("IDUser", "");

                insertTableSave(IdAccout, idItem);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void insertTableSave(String IdAccout, String IdItem)
    {
        ContentValues myvalue = new ContentValues();
        myvalue.put("IDAccount", IdAccout);
        myvalue.put("IDItem", IdItem);
        if(database.insert("Save", null, myvalue) == -1)
        {
            Toast.makeText(MainActivity.this, "Bạn đã lưu địa điểm này rồi!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Lưu địa điểm thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    public void data1Item(String id_item)
    {
        String sql = "Select * from Item where IDItem = '"+id_item+"'";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.getCount()==0)
        {
            Toast.makeText(MainActivity.this, "Rac", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                String iditem = cursor.getString(0);
                String nameitem = cursor.getString(1);
                String typename = cursor.getString(2);
                String addresscode = cursor.getString(3);
                String describeitem = cursor.getString(4);

                String ratings = cursor.getString(5);
                float rate = Float.parseFloat(ratings);


                //Item item = new Item()
                tv_NameItem.setText(nameitem);
                tv_Desc.setText(describeitem);
                rb_ratingbar1.setRating(rate);
                rb_ratingbar.setRating(rate);

            }
        }
        cursor.close();
    }
    public void imageItem(String id_item) {
        String sql = "Select Img from ImgItem where IDItem = '" + id_item + "'";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Lỗi, ko load dc ảnh", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                String listImage = cursor.getString(0);
                listImageWallpaper = listImage;
            }
        }
        cursor.close();
    }
    public void layAddress(String id_item)
    {
        String sql = "select City, Detail from Address where AddressCode = (select AddressCode from Item where IDItem = '"+id_item+"')";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Rac", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                tv_ThanhPho.setText(cursor.getString(0));
                tv_Diachi.setText(cursor.getString(1));
            }
        }
        cursor.close();
    }

    //data comment user
    ArrayList<String> listIDAccount = new ArrayList<>();
    public void dataComment(String id_item) {
        String sql = "select InforUser.IDAccount, IDItem, Title, Comment, Rating, SurName, MiddleName, name, Avatar from Comment, InforUser where Comment.IDAccount = InforUser.IDAccount and IDItem = '"+id_item+"'";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.getCount() == 0) {
            return;
        } else {
            while (cursor.moveToNext()) {
                String IDAccount = cursor.getString(0);
                String IDItem = cursor.getString(1);
                String Title = cursor.getString(2);
                String Comment = cursor.getString(3);
                Float Rating = cursor.getFloat(4);
                String Sur = cursor.getString(5);
                String Middle = cursor.getString(6);
                String Name = cursor.getString(7);
                String Avatar = cursor.getString(8);
                String fullname = Sur + Middle + Name;
                listUserComment.add(new UserComment(IDAccount, fullname, Title, Comment, Rating, UserComment.convertStringToBitmapFromAccess(this, "ImageUser/"+Avatar)));
            }
        }
        cursor.close();
    }

    //hàm liên quan database
    private void processCopy() {
        // Kiểm tra file csdl đã tồn tại hay chưa
        // Trả về đường dẫn đến file csdl
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())// Nếu file tồn tại rồi thì bỏ qua. không sao chép nữa
        {
            try{CopyDataBaseFromAsset();// nếu chưa tồn tại thì bắt đầu coppy
                //( Nó chỉ coppy ở lần sử dụng đầu tiên)
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }
    // Lấy đường dẫn chứa file csdl
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }
    // Hàm coppy csdl từ assets vào database
    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;
            //Lấy database đưa vào myInput
            myInput = getAssets().open(DATABASE_NAME);
            //Lấy dường dẫn lưu trữ để đưa myInput vào
            String outFileName = getDatabasePath();

            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            //mở CSDL rỗng InputStream
            //myOutput để tương tác với CSDL
            OutputStream myOutput = new FileOutputStream(outFileName);
            //Sao chép CSDL từ myInput vào myOutput
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            //ghi vào myOutput
            myOutput.write(buffer);
            //làm rỗng file myOutput
            myOutput.flush();
            //Đóng các file myOutput, myInput
            myOutput.close();
            myInput.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    //hàm lấy ảnh
    ArrayList<Photo> listAnhBia = new ArrayList<>();
    public ArrayList<Photo> getListPhoto()
    {
        String imageString = listImageWallpaper;

        String [] imageParts = imageString.split(", ");

        for(String image : imageParts)
        {
            listAnhBia.add(new Photo(Photo.convertStringToBitmapFromAccess(MainActivity.this, "ImageItem/" + image)));
        }
        return listAnhBia;
    }
}