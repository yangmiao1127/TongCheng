package com.example.ttt.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 1 on 2015/10/7.
 */
public class VPAdapter extends PagerAdapter {

    private List<View> viewList;
    private Context context;

    public VPAdapter(List<View> viewList, Context context) {
        this.viewList = viewList;
        this.context = context;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {//加载view的方法，类似于getView（）方法
        ((ViewPager)container).addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//移除view
        ((ViewPager)container).removeView(viewList.get(position));
    }



    @Override
    public int getCount() {//获得view的数目
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }
}
