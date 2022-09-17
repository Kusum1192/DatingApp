package com.honeybunch.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.honeybunch.app.R;
import com.honeybunch.app.adapters.SimpleAdapter;
import com.honeybunch.app.models.BuyCreditModel;

import java.util.ArrayList;

public class GetCreditActivity extends AppCompatActivity {

    RecyclerView recylerview_buy_credits;
    SimpleAdapter simpleAdapter;
    ArrayList<BuyCreditModel> buyCreditModelArrayList;
    TextView tv_why_credit;
    TextView tv_credit_msga;
    TextView tv_continue_pay;
    View sheetView;
    RoundedBottomSheetDialog mBottomSheetDialog;

    ImageView backBtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_credit);
        toolbar = findViewById(R.id.creditToolbar);
        backBtn=findViewById(R.id.backbtn_iv);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_why_credit = findViewById(R.id.tv_why_credit);
        tv_credit_msga = findViewById(R.id.tv_credit_msga);
        tv_continue_pay = findViewById(R.id.tv_continue_pay);
        mBottomSheetDialog = new RoundedBottomSheetDialog(this);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setDisplayShowHomeEnabled(true);

    //    getSupportActionBar().setTitle(Html.fromHtml("<font color='#000'>Buy Credits</font>"));
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.unselect_color), PorterDuff.Mode.SRC_ATOP);
       // getSupportActionBar().setHomeAsUpIndicator(upArrow);

        buyCreditModelArrayList = new ArrayList<>();
        buyCreditModelArrayList.add(new BuyCreditModel("100 credits", "10 chats", "6", "599"));
        buyCreditModelArrayList.add(new BuyCreditModel("500 credits", "50 chats", "4.4", "2199"));
        buyCreditModelArrayList.add(new BuyCreditModel("1000 credits", "100 chats", "3.5", "599"));

        recylerview_buy_credits = findViewById(R.id.recylerview_buy_credits);
        simpleAdapter = new SimpleAdapter(buyCreditModelArrayList,this);
        recylerview_buy_credits.setLayoutManager(new LinearLayoutManager(this));
        recylerview_buy_credits.setAdapter(simpleAdapter);

        tv_why_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //deleteChatCustomView();
                Toast.makeText(GetCreditActivity.this, "This Features is coming soon.", Toast.LENGTH_SHORT).show();
            }
        });

        tv_continue_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GetCreditActivity.this, "This feature is coming soon", Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(GetCreditActivity.this,CreditCardActivity.class);
                startActivity(intent);*/
            }
        });
    }

    public void deleteChatCustomView() {
        sheetView = getLayoutInflater().inflate(R.layout.bottom_why_credit_modal, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.main_menu_text, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}