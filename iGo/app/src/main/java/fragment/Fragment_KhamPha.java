package fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.igo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import Activity.Load_Fragment_Item_Activity;
import Activity.TrangChu_Activity;
import Adapter.ItemTrangChuAdapter;
import Model.khachsan;

public class Fragment_KhamPha extends Fragment {

    SQLiteDatabase database = null;
    private Context mContext;
    private TextView tvtatca;
    private RecyclerView rcvTH,rcvNH,rcvKS,rcvCF,rcvDD;
    private ItemTrangChuAdapter ksAdapter;

    private Button btnNH, btnKS, btnCF, btnDD;

    ViewFlipper viewFlipper;

    private FragmentManager fragmentManager;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Fragment_KhamPha() {
        this.mContext = mContext;
    }
    public static Fragment_KhamPha newInstance(String param1, String param2) {
        Fragment_KhamPha fragment = new Fragment_KhamPha();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //----------------------------ID ITEM NE
    public String idItem;
    private void getListKhachSan() {
        List<khachsan> list = new ArrayList<>();
        database = mContext.openOrCreateDatabase("iGo_DB.db", MODE_PRIVATE, null);
        //truy vấn

        String sql = "SELECT IdItem, ItemName, PathImg, Ratings FROM Item WHERE Item.TypeItem = 'KS'";
        Cursor c= database.rawQuery(sql, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            idItem = c.getString(0);
            String nameitem = c.getString(1);
            String imagePath = c.getString(2);
            double diem = c.getDouble(3);
            if (nameitem.length() > 17){
                int endIndex = Math.min(17, nameitem.length());
                nameitem = nameitem.substring(0, endIndex) + " ...";
            }
            Bitmap bm = khachsan.converPath(mContext, imagePath);
            khachsan ks = new khachsan(idItem, nameitem, bm, diem);
            list.add(ks);
            c.moveToNext();
        }

        c.close();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        rcvKS.setLayoutManager(linearLayoutManager);
        ksAdapter =  new ItemTrangChuAdapter(getContext());
        ksAdapter.setData(list); // Thiết lập dữ liệu cho Adapter
        rcvKS.setAdapter(ksAdapter);
    }
    private List<khachsan> getListNhaHang() {
        List<khachsan> list = new ArrayList<>();
        mContext = requireContext();
        database = mContext.openOrCreateDatabase("iGo_DB.db", MODE_PRIVATE, null);
        //truy vấn

        String sql = "SELECT IdItem, ItemName, PathImg, Ratings FROM Item WHERE Item.TypeItem = 'NH'";
        Cursor c= database.rawQuery(sql, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            idItem = c.getString(0);
            String nameitem = c.getString(1);
            String imagePath = c.getString(2);
            double diem = c.getDouble(3);
            if (nameitem.length() > 17){
                int endIndex = Math.min(17, nameitem.length());
                nameitem = nameitem.substring(0, endIndex) + " ...";
            }
            Bitmap bm = khachsan.converPath(mContext, imagePath);
            khachsan ks = new khachsan(idItem, nameitem, bm, diem);
            list.add(ks);
            c.moveToNext();
        }
        c.close();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        rcvNH.setLayoutManager(linearLayoutManager);
        ksAdapter =  new ItemTrangChuAdapter(getContext());
        ksAdapter.setData(list); // Thiết lập dữ liệu cho Adapter
        rcvNH.setAdapter(ksAdapter);
        return list;
    }
    private void getListCoffee() {
        List<khachsan> list = new ArrayList<>();
        database = mContext.openOrCreateDatabase("iGo_DB.db", MODE_PRIVATE, null);
        //truy vấn

        String sql = "SELECT IdItem, ItemName, PathImg, Ratings FROM Item WHERE Item.TypeItem = 'CF'";
        Cursor c= database.rawQuery(sql, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            idItem = c.getString(0);
            String nameitem = c.getString(1);
            String imagePath = c.getString(2);
            double diem = c.getDouble(3);
            if (nameitem.length() > 17){
                int endIndex = Math.min(17, nameitem.length());
                nameitem = nameitem.substring(0, endIndex) + " ...";
            }
            Bitmap bm = khachsan.converPath(mContext, imagePath);
            khachsan ks = new khachsan(idItem, nameitem, bm, diem);
            list.add(ks);
            c.moveToNext();
        }
        c.close();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        rcvCF.setLayoutManager(linearLayoutManager);
        ksAdapter =  new ItemTrangChuAdapter(getContext());
        ksAdapter.setData(list); // Thiết lập dữ liệu cho Adapter
        rcvCF.setAdapter(ksAdapter);
    }
    private void getListDiaDiem() {
        List<khachsan> list = new ArrayList<>();
        database = mContext.openOrCreateDatabase("iGo_DB.db", MODE_PRIVATE, null);
        //truy vấn

        String sql = "SELECT IdItem, ItemName, PathImg, Ratings FROM Item WHERE Item.TypeItem = 'KVC'";
        Cursor c= database.rawQuery(sql, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            idItem = c.getString(0);
            String nameitem = c.getString(1);
            String imagePath = c.getString(2);
            double diem = c.getDouble(3);
            if (nameitem.length() > 17){
                int endIndex = Math.min(17, nameitem.length());
                nameitem = nameitem.substring(0, endIndex) + " ...";
            }
            Bitmap bm = khachsan.converPath(mContext, imagePath);
            khachsan ks = new khachsan(idItem, nameitem, bm, diem);
            list.add(ks);
            c.moveToNext();
        }
        c.close();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        rcvDD.setLayoutManager(linearLayoutManager);
        ksAdapter =  new ItemTrangChuAdapter(getContext());
        ksAdapter.setData(list); // Thiết lập dữ liệu cho Adapter
        rcvDD.setAdapter(ksAdapter);
    }
    private void getListThinhHanh() {
        List<khachsan> list = new ArrayList<>();
        database = mContext.openOrCreateDatabase("iGo_DB.db", MODE_PRIVATE, null);
        //truy vấn

        String sql = "SELECT IdItem, ItemName, PathImg, Ratings FROM Item WHERE Item.TypeItem = 'NH'";
        Cursor c= database.rawQuery(sql, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            idItem = c.getString(0);
            String nameitem = c.getString(1);
            String imagePath = c.getString(2);
            double diem = c.getDouble(3);
            if (nameitem.length() > 17){
                int endIndex = Math.min(17, nameitem.length());
                nameitem = nameitem.substring(0, endIndex) + " ...";
            }
            Bitmap bm = khachsan.converPath(mContext, imagePath);
            khachsan ks = new khachsan(idItem, nameitem, bm, diem);
            list.add(ks);
            c.moveToNext();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        rcvTH.setLayoutManager(linearLayoutManager);
        ksAdapter =  new ItemTrangChuAdapter(getContext());
        ksAdapter.setData(list); // Thiết lập dữ liệu cho Adapter
        rcvTH.setAdapter(ksAdapter);
        c.close();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragmentkhampha, container, false);

        //chuyển ảnh
        viewFlipper = view.findViewById(R.id.viewflipper);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);

        //ánh xạ
        rcvKS = view.findViewById(R.id.rev_khachsan);
        rcvTH = view.findViewById(R.id.rev_thinhhanh);
        rcvNH = view.findViewById(R.id.rev_nhahang);
        rcvCF = view.findViewById(R.id.rev_cf);
        rcvDD = view.findViewById(R.id.rev_dd);

        btnNH = view.findViewById(R.id.btnNH);
        btnCF = view.findViewById(R.id.btnCF);
        btnDD = view.findViewById(R.id.btnDD);
        btnKS = view.findViewById(R.id.btnKS);


        getListNhaHang(); getListDiaDiem(); getListThinhHanh(); getListCoffee() ;getListKhachSan();

        tvtatca = view.findViewById(R.id.tvtatca);
        showtatcadanhsach();
        showtatcadanhsachKS();
        showtatcadanhsachCF();
        showtatcadanhsachDD();
        showtatcadanhsachNH();

        return  view;
    }
    private void showtatcadanhsach(){
        tvtatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện nhấp vào TextView ở đây
                Intent intent = new Intent(mContext, Load_Fragment_Item_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void showtatcadanhsachNH(){
        btnNH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Load_Fragment_Item_Activity.class);
                intent.putExtra("selectedFragment", 4); // Truyền dữ liệu để xác định fragment được chọn
                startActivity(intent);
            }
        });
    }

    private void showtatcadanhsachCF(){
        btnCF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Load_Fragment_Item_Activity.class);
                intent.putExtra("selectedFragment", 1); // Truyền dữ liệu để xác định fragment được chọn
                startActivity(intent);
            }
        });
    }

    private void showtatcadanhsachKS(){
        btnKS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Load_Fragment_Item_Activity.class);
                intent.putExtra("selectedFragment", 3); // Truyền dữ liệu để xác định fragment được chọn
                startActivity(intent);
            }
        });
    }
    private void showtatcadanhsachDD(){
        btnDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Load_Fragment_Item_Activity.class);
                intent.putExtra("selectedFragment", 2); // Truyền dữ liệu để xác định fragment được chọn
                startActivity(intent);
            }
        });
    }
}
