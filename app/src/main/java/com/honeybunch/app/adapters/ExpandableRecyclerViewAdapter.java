package com.honeybunch.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.honeybunch.app.R;

import java.util.ArrayList;


public class ExpandableRecyclerViewAdapter extends RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder> {

    ArrayList<String> categoryDatumArrayList;
    ArrayList<String> listSubDataList;
    Context context;
    ArrayList<Integer> counter = new ArrayList<Integer>();

    public ExpandableRecyclerViewAdapter(Context activity, ArrayList<String> categoryDatumArrayList, ArrayList<String> listSubDataList) {
        this.categoryDatumArrayList = categoryDatumArrayList;
        this.context = activity;
        this.listSubDataList = listSubDataList;
        for (int i = 0; i < categoryDatumArrayList.size(); i++) {
            counter.add(0);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_movie_category, tv_movie_subcategories;
        RecyclerView cardRecyclerView;
        LinearLayout cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_movie_category = itemView.findViewById(R.id.tv_movie_category);
            tv_movie_subcategories = itemView.findViewById(R.id.tv_movie_subcategories);
            cardRecyclerView = itemView.findViewById(R.id.innerRecyclerView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_collapseview, parent, false);
        ExpandableRecyclerViewAdapter.ViewHolder vh = new ExpandableRecyclerViewAdapter.ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String datumAList=categoryDatumArrayList.get(position);
        Integer Counter=counter.get(position);
        holder.tv_movie_category.setText(datumAList);
        holder.tv_movie_subcategories.setText(datumAList);
        InnerRecyclerViewAdapter itemInnerRecyclerView = new InnerRecyclerViewAdapter(listSubDataList, context);
        holder.cardRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Counter % 2 == 0) {
                    TranslateAnimation animate = new TranslateAnimation(0,0, holder.cardRecyclerView.getHeight(),0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    holder.cardRecyclerView.startAnimation(animate);
                    holder.cardRecyclerView.setVisibility(View.VISIBLE);

                } else {
                    TranslateAnimation animate = new TranslateAnimation(0,0,0, holder.cardRecyclerView.getHeight());
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    holder.cardRecyclerView.startAnimation(animate);
                    holder.cardRecyclerView.setVisibility(View.GONE);
                }
                counter.set(position, Counter + 1);
            }
        });
        holder.cardRecyclerView.setAdapter(itemInnerRecyclerView);

    }

    @Override
    public int getItemCount() {
        return categoryDatumArrayList.size();
    }


}