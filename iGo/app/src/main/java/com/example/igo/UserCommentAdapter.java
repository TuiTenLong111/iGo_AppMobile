package com.example.igo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class UserCommentAdapter extends ArrayAdapter {
    Context context;
    int resource; //là layout của từng item (chính là file layout thiết kế cho mỗi item)
    ArrayList<UserComment> arrayList;

    public UserCommentAdapter(@NonNull Context context, int resource, ArrayList<UserComment>arrayList) {
        super(context, resource, arrayList);
        this.context=context;
        this.resource= R.layout.item_user;
        this.arrayList=arrayList;
    }

    public UserCommentAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override //lấy view cho item ở vị trí position
    public View getView(int position, View convertView, ViewGroup parent)//position: vi trí của item cần gắn id các control; convertView: View của mỗi item;       \
    //parent: view của lítView
    {
        UserComment cmt = arrayList.get(position);
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(resource,null);
        }
        //set img
        ImageView img=(ImageView) convertView.findViewById(R.id.imgV);
        img.setImageBitmap(cmt.getImage());
        //set name
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvName.setText(cmt.getName());
        //set rating
        RatingBar rb_ratingbar = (RatingBar) convertView.findViewById(R.id.rb_ratingbar);
        rb_ratingbar.setRating(cmt.getRating());
        //title cmt
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        tvTitle.setText(cmt.getTitle());
        //noi dung cmt
        TextView tvComment = (TextView) convertView.findViewById(R.id.tv_noidung_cmt);
        tvComment.setText(cmt.getComment());

        return convertView;
    }

}
