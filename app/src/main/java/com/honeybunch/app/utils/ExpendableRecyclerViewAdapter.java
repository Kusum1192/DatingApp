package com.honeybunch.app.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.honeybunch.app.R;
import com.honeybunch.app.models.Person;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ExpendableRecyclerViewAdapter extends RecyclerView.Adapter<ExpendableRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Person> personList;

    public ExpendableRecyclerViewAdapter(Context context, List<Person> list) {
        this.context = context;
        this.personList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_expand, viewGroup, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        holder.name.setText(personList.get(i).getName());

        Picasso.get().load(personList.get(i).getImage()).into(holder.image);

        holder.viewMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = toggleLayout(!personList.get(i).isExpanded(), v, holder.layoutExpand);
                personList.get(i).setExpanded(show);
            }
        });

    }

    private boolean toggleLayout(boolean isExpanded, View v, LinearLayout layoutExpand) {
        Animations.toggleArrow(v, isExpanded);
        if (isExpanded) {
            Animations.expand(layoutExpand);
        } else {
            Animations.collapse(layoutExpand);
        }
        return isExpanded;

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  {

        TextView name;
        ImageView image;
        ImageButton viewMoreBtn;
        LinearLayout layoutExpand;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            viewMoreBtn = itemView.findViewById(R.id.viewMoreBtn);
            layoutExpand = itemView.findViewById(R.id.layoutExpand);


        }

    }



}