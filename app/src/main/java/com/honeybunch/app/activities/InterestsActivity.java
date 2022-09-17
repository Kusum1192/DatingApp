package com.honeybunch.app.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.honeybunch.app.R;
import com.honeybunch.app.models.MultiPLeData;

import java.util.ArrayList;
import java.util.List;


public class InterestsActivity extends AppCompatActivity {

    FlexboxAdapter adapter;
    ArrayList<MultiPLeData> myList = new ArrayList<>();

    TextView btn_select;
    List<String> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        btn_select = findViewById(R.id.btn_select);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000'>Select Interest</font>"));
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);


        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.unselect_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        Intent intent = getIntent();
        if(intent!=null){
          //  myList = (ArrayList<MultiPLeData>) getIntent().getSerializableExtra("myList");
            myList = getIntent().getExtras().getParcelableArrayList("mylist");

        }
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("interest", String.valueOf(selected));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        RecyclerView recyler_chip = findViewById(R.id.recyler_chip);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(InterestsActivity.this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        recyler_chip.setLayoutManager(layoutManager);
        adapter = new FlexboxAdapter(InterestsActivity.this, myList);
        recyler_chip.setAdapter(adapter);

    }

    public class FlexboxAdapter extends RecyclerView.Adapter<FlexboxAdapter.ViewHolder> {

        Context context;
        List<MultiPLeData> arrayList = new ArrayList<>();

        public FlexboxAdapter(Context context, List<MultiPLeData> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        @Override
        public FlexboxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.custom_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FlexboxAdapter.ViewHolder holder, int position) {
            holder.title.setText(arrayList.get(position).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    arrayList.get(pos).setSelected(!arrayList.get(pos).isSelected());
                    if (arrayList.get(pos).isSelected()) {
                        holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_bg));

                    } else {
                        holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_wtout_bg));

                    }
                    getSelected();

                }
            });

        }
        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            LinearLayout ll_custom_bg;

            public ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tvTitle);
                ll_custom_bg = itemView.findViewById(R.id.ll_custom_bg);

            }
        }

        public List<String> getSelected() {
             selected = new ArrayList<>();
            for (MultiPLeData model : arrayList) {
                if (model.isSelected()) {
                    selected.add(model.getName());
                }
            }
            return selected;
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}