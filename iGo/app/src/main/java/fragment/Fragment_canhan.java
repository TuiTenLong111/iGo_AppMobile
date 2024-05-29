package fragment;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.igo.R;

import Canhan.*;

public class Fragment_canhan extends Fragment {
    SQLiteDatabase database = null;
    private Context mContext;
    private GridLayout mainGrid;

    public Fragment_canhan() {
        this.mContext = mContext;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main2, container, false);

        mainGrid = (GridLayout) view.findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);
        return view;
    }

    private void setSingleEvent(GridLayout mainGrid) {
        for(int i = 0 ; i < mainGrid.getChildCount(); i ++){
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i ;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI == 0){
                        Intent intent = new Intent(getActivity(),activity_personal_info.class);
                        startActivity(intent);
                    }
                    else if(finalI == 1){
                        Intent intent = new Intent(getActivity(), Doimatkhau.class);
                        startActivity(intent);
                    }
                    else if(finalI == 2){
                        Intent intent = new Intent(getActivity(), Chinhsach.class);
                        startActivity(intent);
                    }
                    else if(finalI == 3){
                        Intent intent = new Intent(getActivity(), HDSD.class);
                        startActivity(intent);
                    }
                    else if(finalI == 4){
                        Intent intent = new Intent(getActivity(), DangXuat.class);
                        startActivity(intent);
                    }
                    else if(finalI == 5){
                        Intent intent = new Intent(getActivity(), VeChungToi.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}

