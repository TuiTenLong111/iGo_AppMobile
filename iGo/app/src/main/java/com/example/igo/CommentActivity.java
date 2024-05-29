package com.example.igo;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CommentActivity extends AppCompatActivity {

    SQLiteDatabase database=null;
    Button btnDanhGia;
    RatingBar ratingBar;
    TextView tv_rateCount, tv_showRating;
    EditText edt_tieudecmt, edt_noidungcmt;
    float rateValue;
    String temp;

    String DATABASE_NAME="iGo_DB.db";

    int IDUser ;
    String idItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null); //khai báo database

        IDUser = getIntent().getIntExtra("IDUser", -1);
        idItem = getIntent().getStringExtra("idItem");

        tv_rateCount = findViewById(R.id.tv_rateCount);
        tv_showRating = findViewById(R.id.tv_showRating);
        ratingBar = findViewById(R.id.rb_ratingbar);
        edt_tieudecmt = findViewById(R.id.edt_tieudecmt);
        edt_noidungcmt = findViewById(R.id.edt_noidungcmt);
        btnDanhGia = findViewById(R.id.btn_danhgia);

        //back to main
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateValue = ratingBar.getRating();
                if(rateValue <= 1 && rateValue>0)
                    tv_rateCount.setText("Rất tệ " + rateValue +"/5");
                else if(rateValue <=2 && rateValue>1)
                    tv_rateCount.setText("Tệ " + rateValue +"/5");
                else if(rateValue <= 3 && rateValue>2)
                    tv_rateCount.setText("Ổn " + rateValue +"/5");
                else if(rateValue <= 4 && rateValue>3)
                    tv_rateCount.setText("Tốt " + rateValue +"/5");
                else if(rateValue <= 5 && rateValue>4)
                    tv_rateCount.setText("Rất tốt " + rateValue +"/5");
            }
        });
        btnDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = tv_rateCount.getText().toString();
                //tv_showRating.setText("Đánh giá của bạn: \n" + temp + "\n" + edt_tieudecmt.getText() + "\n" + edt_noidungcmt.getText());

                //lấy dữ liệu để insert vào table Comment
                String title = edt_tieudecmt.getText().toString();
                String comment = edt_noidungcmt.getText().toString();
                float rating = rateValue;

                ContentValues contentValues=new ContentValues();
                contentValues.put("IDAccount", IDUser);
                contentValues.put("IDItem",idItem);
                contentValues.put("Title", title);
                contentValues.put("Comment", comment);
                contentValues.put("Rating", rating);

                String msg=" ";
                if(database.insert("Comment", null, contentValues)==-1){
                    msg="Thêm thất bại";


                }else{
                    msg="Thêm dữ liệu thành công!!!";
                }
                Toast.makeText(CommentActivity.this, msg, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CommentActivity.this, MainActivity.class);
                intent.putExtra("idItem", idItem); //trả ngược id item lại cho item detail
                startActivity(intent);
//                Toast.makeText(CommentActivity.this, String.valueOf(IDUser), Toast.LENGTH_SHORT).show();
            }
        });
    }
}