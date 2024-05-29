package Adapter;

import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import fragment.Fragment_Coffee;
import fragment.Fragment_DiaDiem;
import fragment.Fragment_GiaiTri;
import fragment.Fragment_KhachSan;
import fragment.Fragment_KhamPha;
import fragment.Fragment_Luu;
import fragment.Fragment_NhaHang;
import fragment.Fragment_TimKiem;
import fragment.Fragment_canhan;

public class ViewPagerAdapterFragmentTrangChu extends FragmentStateAdapter {


//    public ViewPagerAdapterFragmentTrangChu(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
//        super(fragmentManager, lifecycle);
//        this.fragments = fragments;
//    }

    public ViewPagerAdapterFragmentTrangChu(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Fragment_KhamPha();

            case 1:
                return new Fragment_TimKiem();

            case 2:
                return new Fragment_Luu();

            case 3:
                return new Fragment_canhan();
            default:
                return new Fragment_KhamPha();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
