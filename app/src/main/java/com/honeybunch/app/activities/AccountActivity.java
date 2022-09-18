package com.honeybunch.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.honeybunch.app.R;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout ll_block_list;
    LinearLayout ll_refused_list;
    LinearLayout ll_skip_list;
    Toolbar toolbar;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar = findViewById(R.id.accountToolbar);
        backBtn=findViewById(R.id.backbtn_iv);
        backBtn.setOnClickListener(v -> onBackPressed());


     /*   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000'>Account info</font>"));
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.unselect_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);*/


        ll_block_list = findViewById(R.id.ll_block_list);
      //  ll_refused_list = findViewById(R.id.ll_refused_list);
        ll_skip_list = findViewById(R.id.ll_skip_list);
        ll_block_list.setOnClickListener(this);
       // ll_refused_list.setOnClickListener(this);
        ll_skip_list.setOnClickListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_block_list:
                Intent intent = new Intent(AccountActivity.this, BlockUserListActivity.class);
                startActivity(intent);
                break;

           /* case R.id.ll_refused_list:
                Intent intentgetCredit = new Intent(AccountActivity.this, RefusedListActivity.class);
                startActivity(intentgetCredit);
                break;*/

            case R.id.ll_skip_list:
                Intent intentgetSkip = new Intent(AccountActivity.this, SkipListActivity.class);
                startActivity(intentgetSkip);
                break;
            default:
        }
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}