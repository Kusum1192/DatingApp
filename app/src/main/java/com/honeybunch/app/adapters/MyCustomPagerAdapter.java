package com.honeybunch.app.adapters;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.viewpager.widget.PagerAdapter;

import com.honeybunch.app.R;
import com.honeybunch.app.activities.ViewPagerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyCustomPagerAdapter extends PagerAdapter {

    private List<String> modelsImagesList;
    private LayoutInflater inflater;
    private Context context;

    public MyCustomPagerAdapter(Context context, List<String> modelsImagesList) {
        this.context = context;
        this.modelsImagesList = modelsImagesList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return modelsImagesList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.view_image_viewpager, view, false);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.image);

        Picasso.get().load(modelsImagesList.get(position))
                .into(myImage);

        view.addView(myImageLayout, 0);
        //listening to image click
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewPagerActivity.class);
                intent.putStringArrayListExtra("imagesList", (ArrayList<String>) modelsImagesList);
                context.startActivity(intent);

            }
        });


        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}