package fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.igo.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import Adapter.fragment_item_Adapter;
import Model.model_fragItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Coffee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Coffee extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private String mParam1;
    private String mParam2;


    private Context mContext;
    private fragment_item_Adapter frag_adapter;
    private RecyclerView rcvcf;
    private SQLiteDatabase database=null;

    public Fragment_Coffee() {
        this.mContext = mContext;
    }

    @NonNull
    public static Fragment_Coffee newInstance(String param1, String param2) {
        Fragment_Coffee fragment = new Fragment_Coffee();
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
    public String idItem;
    @NonNull
    private List<model_fragItem> getList() throws FileNotFoundException {
        List<model_fragItem> list = new ArrayList<>();
        mContext = requireContext();
        database = mContext.openOrCreateDatabase("iGo_DB.db", MODE_PRIVATE, null);
        //truy vấn
        Cursor c = database.query("Item", new String[]{"IdItem, ItemName", "Ratings", "PathImg", "TypeItem"}, null, null, null, null, null, null);
        String data = "";
        c.moveToFirst();
        String a = "";
        while (c.isAfterLast() == false) {
            idItem = c.getString(0);
            String nameitem = c.getString(1);
            double diemItem = c.getDouble(2);
            String imagePath = c.getString(3);
            String type = c.getString(4);
            a = imagePath;

            if (type.equals("CF") == true){
                Bitmap bm = model_fragItem.converPath(mContext, imagePath);
                model_fragItem fragItem = new model_fragItem(idItem, nameitem, diemItem,bm);
                list.add(fragItem);
            }
            c.moveToNext();
        }
        Toast.makeText(getActivity(), a, Toast.LENGTH_LONG);
        c.close();
        return list;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // ánh xạ view
        View view = inflater.inflate(R.layout.fragment__coffee, container, false);

        rcvcf = view.findViewById(R.id.revfragCoffe);

        frag_adapter = new fragment_item_Adapter(getContext());

        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcvcf.setLayoutManager(linearLayoutManager);

        try {
            frag_adapter.setData(getList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        rcvcf.setAdapter(frag_adapter);

        // Inflate the layout for this fragment
        return view;
    }
}