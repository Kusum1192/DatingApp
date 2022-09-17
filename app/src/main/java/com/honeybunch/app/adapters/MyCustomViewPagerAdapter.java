package com.honeybunch.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.honeybunch.app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCustomViewPagerAdapter extends PagerAdapter {

    private List<String> images;
    private LayoutInflater inflater;
    private Context context;

    public MyCustomViewPagerAdapter(Context context, List<String> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.view_image_viewpager_full, view, false);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.image);
       // myImage.setImageUriAsync(Uri.parse(images.get(position)));

        Picasso.get().load(images.get(position))
                .into(myImage);

        view.addView(myImageLayout, 0);
        //listening to image click


        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}