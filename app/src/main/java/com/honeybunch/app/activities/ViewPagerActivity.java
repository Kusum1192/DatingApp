package com.honeybunch.app.activities;

import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.honeybunch.app.R;
import com.honeybunch.app.adapters.MyCustomViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends Activity {

    MyCustomViewPagerAdapter myCustomPagerAdapter;
    List<String> modelsImagesList = new ArrayList<>();
    ImageView iv_close;
    TextView tv_image_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        iv_close = findViewById(R.id.iv_close);
        tv_image_count = findViewById(R.id.tv_image_count);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent!=null){
            modelsImagesList = getIntent().getStringArrayListExtra("imagesList");
        }

        ViewPager mImageViewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mImageViewPager, true);
        myCustomPagerAdapter = new MyCustomViewPagerAdapter(ViewPagerActivity.this, modelsImagesList);
        mImageViewPager.setAdapter(myCustomPagerAdapter);
        tv_image_count.setText(mImageViewPager.getCurrentItem()+1+"/"+modelsImagesList.size());
        mImageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                tv_image_count.setText(mImageViewPager.getCurrentItem()+1+"/"+modelsImagesList.size());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
}