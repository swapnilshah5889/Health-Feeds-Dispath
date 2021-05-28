package com.fluvina.hummnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.fluvina.hummnew.R;

import java.util.ArrayList;

public class CarouseHealthInShortAdapter extends PagerAdapter {

    Context context;
    //int[] listItems;
    ArrayList<String> listItems;

    public CarouseHealthInShortAdapter(Context context, ArrayList<String> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.inshort_carousel_item_layout, null);
        try {

            String string = listItems.get(position);

            ImageView ivImage = view.findViewById(R.id.ivImage);

            Glide.with(context).load(string)
                    .into(ivImage);

            container.addView(view);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}

