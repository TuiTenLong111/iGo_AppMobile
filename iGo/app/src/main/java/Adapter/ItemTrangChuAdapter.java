package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.igo.MainActivity;
import com.example.igo.R;
import Model.khachsan;

import java.util.List;

public class ItemTrangChuAdapter extends RecyclerView.Adapter<ItemTrangChuAdapter.ITemiewHolder> {

    private List<khachsan> mListKS;
    private Context mContext;

    public ItemTrangChuAdapter(Context context)
    {
        this.mContext = context;
    }

    public void setData(List<khachsan> list){
        this.mListKS = list;
        notifyDataSetChanged(); // Thông báo cập nhật dữ liệu
    }
    @NonNull
    @Override
    public ITemiewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_trangchu,parent, false);
        return new ITemiewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ITemiewHolder holder, int position) {
        khachsan KS = mListKS.get(position);

        String idItem = KS.getIdItem(); // dòng này lấy id item

        holder.textViewName.setText(KS.getTitle());
        holder.imgKS.setImageBitmap(KS.getImg());
        holder.tvdiemks.setText(String.valueOf(KS.getDiem()));
        holder.layout_recycleview_item_trangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goToDetailItem(idItem);
                }catch (Exception ex)
                {
                    Toast.makeText(mContext, "lỗi!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void goToDetailItem(String iditem)
    {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("idItem", iditem);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mListKS != null){
            return mListKS.size();
        }
        return 0;
    }

    public class ITemiewHolder extends RecyclerView.ViewHolder{
        private ImageView imgKS;
        private TextView textViewName;
        private TextView tvdiemks;

        private CardView layout_recycleview_item_trangchu;

        public ITemiewHolder(@NonNull View itemView) {
            super(itemView);
            imgKS = itemView.findViewById(R.id.img_thuytienavatar);
            textViewName = itemView.findViewById(R.id.textview_thuytien);
            tvdiemks = itemView.findViewById(R.id.textview_diem);
            layout_recycleview_item_trangchu = itemView.findViewById(R.id.layout_recycleview_item_trangchu);
        }
    }
}
