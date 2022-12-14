package com.puyue.www.qiaoge;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TabFragmentAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> fragments;
    private List<String> strings;

    public TabFragmentAdapter(List<Fragment> fragments, List<String> strings, FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.strings = strings;
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }

    /**
     * 重写该方法，取消调用父类该方法 * 可以避免在viewpager切换
     * fragment不可见时执行到onDestroyView，可见时又从onCreateView重新加载视图
     * * 因为父类的destroyItem方法中会调用detach方法，将fragment与view分离，
     * （detach()->onPause()->onStop()->onDestroyView()）
     * 然后在instantiateItem方法中又调用attach方法，此方法里判断如果fragment与view分离了，
     ** 那就重新执行onCreateView，再次将view与fragment绑定（attach()->onCreateView()->onActivityCreated()->onStart()->onResume()） *
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // super.destroyItem(container, position, object); }

    }

}

