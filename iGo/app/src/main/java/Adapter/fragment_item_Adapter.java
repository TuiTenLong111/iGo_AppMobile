package Adapter;

import Model.model_fragItem;
import fragment.Fragment_KhachSan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.List;


public class fragment_item_Adapter extends RecyclerView.Adapter<fragment_item_Adapter.KSViewHolder> {
    private List<model_fragItem> mListcf;
    private Context mContext;

    public fragment_item_Adapter(Context context)
    {
        this.mContext = context;
    }
    public void setData(List<model_fragItem> list){
        this.mListcf = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_lisview_item,parent, false);
        return new KSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull fragment_item_Adapter.KSViewHolder holder, int position) {
        model_fragItem cf = mListcf.get(position);

        String idItem = cf.getIditem(); // dòng này lấy id item
        String ten = cf.getTenks();

        holder.textViewName.setText(cf.getTenks());
        holder.textViewDiem.setText(String.valueOf(cf.getDiemks()));
        holder.imgKS.setImageBitmap(cf.getImgks());
        holder.layout_recycleview_item.setOnClickListener(new View.OnClickListener() {
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
        if (mListcf != null){
            return mListcf.size();
        }
        return 0;
    }

    public class KSViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgKS;

        private TextView textViewName, textViewDiem;
        private CardView layout_recycleview_item;

        public KSViewHolder(@NonNull View itemView) {
            super(itemView);
            imgKS = itemView.findViewById(R.id.img_listks);
            textViewDiem = itemView.findViewById(R.id.tv_diemks);
            textViewName = itemView.findViewById(R.id.tv_tenlistks);
            layout_recycleview_item = itemView.findViewById(R.id.layout_recycleview_item);
        }
    }
}
