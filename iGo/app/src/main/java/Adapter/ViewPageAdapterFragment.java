package Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fragment.*;

public class ViewPageAdapterFragment extends FragmentStateAdapter {

    public ViewPageAdapterFragment(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Fragment_Coffee();

            case 1:
                return new Fragment_DiaDiem();

            case 2:
                return new Fragment_KhachSan();

            case 3:
                return new Fragment_NhaHang();

            case 4:
                return new Fragment_GiaiTri();

            default:
                return new Fragment_Coffee();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
