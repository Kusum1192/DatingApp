package com.honeybunch.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honeybunch.app.R;

import com.honeybunch.app.models.DataItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;


public class SearchCityActivity extends AppCompatActivity {

    ArrayList<DataItem> citiesDataModels = new ArrayList<>();

    RecyclerView recyclerView;
    CustomCityAdapter adapter;

    EditText et_search_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setDisplayShowHomeEnabled(true);

    //    getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Search City</font>"));
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);

      /*  final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
        upArrow.setColorFilter(getResources().getColor(R.color.unselect_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);*/


        recyclerView = findViewById(R.id.recylerview);
        et_search_city = findViewById(R.id.et_search_city);



        try {
            JSONObject object = new JSONObject(readJSON());
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String id = jsonObject.getString("id");
                String first_name = jsonObject.getString("name");
                String state = jsonObject.getString("state");

                citiesDataModels.add(new DataItem(id,first_name,state));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new CustomCityAdapter(this, citiesDataModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchCityActivity.this));
        recyclerView.setAdapter(adapter);

        et_search_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<DataItem> filteredList = new ArrayList<>();
        for (DataItem item : citiesDataModels) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    public String readJSON() {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = getAssets().open("cities.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }
        return json;
    }

    public class CustomCityAdapter extends RecyclerView.Adapter<CustomCityAdapter.ViewHolder> {

        Context context;
        ArrayList<DataItem> arrayList;

        public CustomCityAdapter(Context context, ArrayList<DataItem> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public CustomCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_city_name, parent, false);
            return new CustomCityAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomCityAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.tv_cityname.setText(arrayList.get(position).getName()+", "+arrayList.get(position).getState());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("city",arrayList.get(position).getName());
                    intent.putExtra("state",arrayList.get(position).getState());
                     setResult(RESULT_OK,intent);
                     finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }


        public  class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_cityname;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_cityname = itemView.findViewById(R.id.tv_cityname);

            }
        }

        public void filterList(ArrayList<DataItem> filteredList) {
            arrayList = filteredList;
            notifyDataSetChanged();
        }

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}