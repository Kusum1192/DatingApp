package com.honeybunch.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.honeybunch.app.BuildConfig;
import com.honeybunch.app.R;
import com.honeybunch.app.interfaces.Api;
import com.honeybunch.app.models.MultiPLeData;
import com.honeybunch.app.models.UpdateAboutModel;
import com.honeybunch.app.netowrkcall.RetrofitClientInstance;
import com.honeybunch.app.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileUpdateSecondActivity extends Activity implements View.OnClickListener {


    ArrayList<MultiPLeData> multiPLeDataInterset;
    ArrayList<MultiPLeData> multiPLeDataPurpose;
    RecyclerView recylerview_interest;
    RecyclerView recylerview_date_purpose;
    List<String> selectedInterest;
    List<String> selectedDatePurpose;
    List<String> educationlist;
    List<String> professionlist;
    List<String> hairlist;
    List<String> heightlist;
    List<String> weightlist;
    List<String> eyelist;
    TextView tv_submit_profile;
    SpinKitView loadBar;

    String educationData;
    String professionData;
    String appearanceHairData;
    String appearanceWeightData;
    String appearanceHeightData;
    String appearanceEyeData;

    Spinner spinner_edication;
    Spinner spinner_profession;
    Spinner spinner_haircolor;
    Spinner spinner_weight;
    Spinner spinner_height;
    Spinner spinner_eyecolor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update_second);

        Constants.setSharedPreferenceInt(UserProfileUpdateSecondActivity.this, "flag", 2);

        recylerview_interest = findViewById(R.id.recylerview_interest);
        recylerview_date_purpose = findViewById(R.id.recylerview_date_purpose);
        tv_submit_profile = findViewById(R.id.tv_submit_profile);
        loadBar = findViewById(R.id.spin_kit);
        tv_submit_profile.setOnClickListener(this);

        spinner_edication = (Spinner) findViewById(R.id.spin_education);
        spinner_profession = (Spinner) findViewById(R.id.spinner_profession);
        spinner_haircolor = (Spinner) findViewById(R.id.spinner_haircolor);
        spinner_weight = (Spinner) findViewById(R.id.spinner_weight);
        spinner_height = (Spinner) findViewById(R.id.spinner_height);
        spinner_eyecolor = (Spinner) findViewById(R.id.spinner_eyecolor);
//        spinner_edication.setOnItemSelectedListener(this);
//        spinner_profession.setOnItemSelectedListener(this);
//        spinner_haircolor.setOnItemSelectedListener(this);
//        spinner_weight.setOnItemSelectedListener(this);
//        spinner_eyecolor.setOnItemSelectedListener(this);
//        spinner_height.setOnItemSelectedListener(this);

        multiPLeDataInterset = new ArrayList<>();


        educationlist = new ArrayList<>();
        professionlist = new ArrayList<>();
        hairlist = new ArrayList<>();
        heightlist = new ArrayList<>();
        eyelist = new ArrayList<>();
        weightlist = new ArrayList<>();


        multiPLeDataInterset.add(new MultiPLeData("Animals"));
        multiPLeDataInterset.add(new MultiPLeData("Art"));
        multiPLeDataInterset.add(new MultiPLeData("IT"));
        multiPLeDataInterset.add(new MultiPLeData("Finance and Investments"));
        multiPLeDataInterset.add(new MultiPLeData("Cars"));
        multiPLeDataInterset.add(new MultiPLeData("Science"));
        multiPLeDataInterset.add(new MultiPLeData("Charitable activities"));
        multiPLeDataInterset.add(new MultiPLeData("Gadgets"));
        multiPLeDataInterset.add(new MultiPLeData("Dancing"));
        multiPLeDataInterset.add(new MultiPLeData("Hikings"));
        multiPLeDataInterset.add(new MultiPLeData("Travel"));
        multiPLeDataInterset.add(new MultiPLeData("Cinema"));
        multiPLeDataInterset.add(new MultiPLeData("Extreme"));
        multiPLeDataInterset.add(new MultiPLeData("Literature"));
        multiPLeDataInterset.add(new MultiPLeData("Music"));
        multiPLeDataInterset.add(new MultiPLeData("Painting"));
        multiPLeDataInterset.add(new MultiPLeData("Shopping"));
        multiPLeDataInterset.add(new MultiPLeData("Photography"));
        multiPLeDataInterset.add(new MultiPLeData("Sports"));
        multiPLeDataInterset.add(new MultiPLeData("Couch surfing"));
        multiPLeDataInterset.add(new MultiPLeData("Theater"));
        multiPLeDataInterset.add(new MultiPLeData("Writing"));
        multiPLeDataInterset.add(new MultiPLeData("Games"));
        multiPLeDataInterset.add(new MultiPLeData("Movies"));
        multiPLeDataInterset.add(new MultiPLeData("Eating out"));
        multiPLeDataInterset.add(new MultiPLeData("Bars/Pubs"));


        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(UserProfileUpdateSecondActivity.this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        recylerview_interest.setLayoutManager(layoutManager);
        FlexboxInterestAdapter adapter = new FlexboxInterestAdapter(UserProfileUpdateSecondActivity.this, multiPLeDataInterset);
        recylerview_interest.setAdapter(adapter);

        //for education
        educationlist.add("Select Education");
        educationlist.add("High School");
        educationlist.add("Trade School");
        educationlist.add("Diploma");
        educationlist.add("Bachelor's");
        educationlist.add("Master's");
        educationlist.add("Doctorate's");

        ArrayAdapter dataAdapter_education = new ArrayAdapter(this, android.R.layout.simple_spinner_item, educationlist);
        // Drop down layout style - list view with radio button
        dataAdapter_education.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_edication.setAdapter(dataAdapter_education);

        //for profession
        professionlist.add("Select Profession");
        professionlist.add("Software Professional");
        professionlist.add("Business Person");
        professionlist.add("Students");
        professionlist.add("Sales / Marketing");
        professionlist.add("Technical / Engineering / Science");
        professionlist.add("Hospitality / Service");
        professionlist.add("Executive / Management");
        professionlist.add("Account / Finance");
        professionlist.add("Acting / Artist / Musician");
        professionlist.add("Architect");
        professionlist.add("Journalism");
        professionlist.add("Fashion");
        professionlist.add("Law");
        professionlist.add("Teaching");
        professionlist.add("Agriculture");
        professionlist.add("Civil Services(IAS, IPS, IRS, IFS)");
        professionlist.add("Defence");
        professionlist.add("Social Services");
        professionlist.add("Medical");
        professionlist.add("Writer");
        professionlist.add("Others");

        ArrayAdapter dataAdapter_professional = new ArrayAdapter(this, android.R.layout.simple_spinner_item, professionlist);
        // Drop down layout style - list view with radio button
        dataAdapter_professional.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_profession.setAdapter(dataAdapter_professional);

        //for hair
        hairlist.add("Select Hair Color");
        hairlist.add("Blond");
        hairlist.add("Brown");
        hairlist.add("Red");
        hairlist.add("Light Brown");
        hairlist.add("Black");
        hairlist.add("Gray");
        hairlist.add("White");

        ArrayAdapter dataAdapter_drinks = new ArrayAdapter(this, android.R.layout.simple_spinner_item, hairlist);
        // Drop down layout style - list view with radio button
        dataAdapter_drinks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_haircolor.setAdapter(dataAdapter_drinks);
        // for weight
        weightlist.add("Select Weight");
        weightlist.add("41 kg - 50 kg");
        weightlist.add("51 kg - 60 kg");
        weightlist.add("61 kg - 70 kg");
        weightlist.add("71 kg - 80 kg");
        weightlist.add("81 kg - 90 kg");
        weightlist.add("91 kg - 100 kg");
        weightlist.add("above 100 kg");

        ArrayAdapter dataAdapter_wieght = new ArrayAdapter(this, android.R.layout.simple_spinner_item, weightlist);
        // Drop down layout style - list view with radio button
        dataAdapter_wieght.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_weight.setAdapter(dataAdapter_wieght);

        // for height
        heightlist.add("Select Height");
        heightlist.add("141 cm - 150 cm");
        heightlist.add("151 cm - 160 cm");
        heightlist.add("161 cm - 170 cm");
        heightlist.add("above 170");

        ArrayAdapter dataAdapter_smoke = new ArrayAdapter(this, android.R.layout.simple_spinner_item, heightlist);
        // Drop down layout style - list view with radio button
        dataAdapter_smoke.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_height.setAdapter(dataAdapter_smoke);

        //for eating
        eyelist.add("Select Eye Color");
        eyelist.add("Brown");
        eyelist.add("Black");
        eyelist.add("Other");


        ArrayAdapter dataAdapter_eating = new ArrayAdapter(this, android.R.layout.simple_spinner_item, eyelist);
        // Drop down layout style - list view with radio button
        dataAdapter_eating.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_eyecolor.setAdapter(dataAdapter_eating);

        //date purpose
        multiPLeDataPurpose = new ArrayList<>();
        multiPLeDataPurpose.add(new MultiPLeData("Chat"));
        multiPLeDataPurpose.add(new MultiPLeData("New Friend"));
        multiPLeDataPurpose.add(new MultiPLeData("Long Term Dating"));
        multiPLeDataPurpose.add(new MultiPLeData("Short Term Dating"));

        FlexboxLayoutManager layoutManager1 = new FlexboxLayoutManager(UserProfileUpdateSecondActivity.this);
        layoutManager1.setFlexDirection(FlexDirection.ROW);
        layoutManager1.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager1.setAlignItems(AlignItems.FLEX_START);
        recylerview_date_purpose.setLayoutManager(layoutManager1);
        FlexboxDatePurposeAdapter adapter1 = new FlexboxDatePurposeAdapter(UserProfileUpdateSecondActivity.this, multiPLeDataPurpose);
        recylerview_date_purpose.setAdapter(adapter1);

        // for education
        spinner_edication.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("TAG", "onItemSelected: " + educationlist.get(position));
                educationData = educationlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        //for profession
        spinner_profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("TAG", "onItemSelected: " + professionlist.get(position));
                professionData = professionlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        //for haircolor
        spinner_haircolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("TAG", "onItemSelected: " + hairlist.get(position));
                appearanceHairData = hairlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        //for weight
        spinner_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("TAG", "onItemSelected: " + weightlist.get(position));
                appearanceWeightData = weightlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        // for heigth
        spinner_height.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("TAG", "onItemSelected: " + heightlist.get(position));
                appearanceHeightData = heightlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        //for eye color
        spinner_eyecolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("TAG", "onItemSelected: " + eyelist.get(position));
                appearanceEyeData = eyelist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit_profile:
                invalidateData();

                break;

            default:

        }
    }

    private void invalidateData() {
        Log.e("TAG", "invalidateData: " + multiPLeDataPurpose.size());
        if (educationData.isEmpty() || spinner_edication.getSelectedItem().equals("Select Education")) {
            TextView errorText = (TextView) spinner_edication.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Select Education*");//changes the selected item text to this
            Toast.makeText(this, "Select Education*", Toast.LENGTH_SHORT).show();
        } else if (professionData.isEmpty() || spinner_profession.getSelectedItem().equals("Select Profession")) {
            TextView errorText = (TextView) spinner_profession.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Select Profession*");//changes the selected item text to this
            Toast.makeText(this, "Select Profession*", Toast.LENGTH_SHORT).show();
        } else if (selectedDatePurpose == null || selectedDatePurpose.size() <= 0) {
            Toast.makeText(this, "select date purpose", Toast.LENGTH_SHORT).show();
        } else if (appearanceHairData.isEmpty() || spinner_haircolor.getSelectedItem().equals("Select Hair Color")) {
            TextView errorText = (TextView) spinner_haircolor.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Select Hair Color*");//changes the selected item text to this
            Toast.makeText(this, "Select Hair Color*", Toast.LENGTH_SHORT).show();
        } else if (appearanceWeightData.isEmpty() || spinner_weight.getSelectedItem().equals("Select Weight")) {
            TextView errorText = (TextView) spinner_weight.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Select Weight*");//changes the selected item text to this
            Toast.makeText(this, "Select Weight*", Toast.LENGTH_SHORT).show();
        } else if (appearanceHeightData.isEmpty() || spinner_height.getSelectedItem().equals("Select Height")) {
            TextView errorText = (TextView) spinner_height.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Select Height*");//changes the selected item text to this
            Toast.makeText(this, "Select Height*", Toast.LENGTH_SHORT).show();
        } else if (appearanceEyeData.isEmpty() || spinner_eyecolor.getSelectedItem().equals("Select Eye Color")) {
            TextView errorText = (TextView) spinner_eyecolor.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Select Eye Color*");//changes the selected item text to this
            Toast.makeText(this, "Select Eye Color*", Toast.LENGTH_SHORT).show();
        } else if (selectedInterest == null || selectedInterest.size() <= 0) {
            Toast.makeText(this, "select Interest", Toast.LENGTH_SHORT).show();
        } else {
            updateAboutProfile(educationData, professionData, appearanceHairData, appearanceWeightData, appearanceHeightData, appearanceEyeData, selectedDatePurpose, selectedInterest);
        }
    }
    private void updateAboutProfile(String educationData, String professionData, String appearanceHairData,
                                    String appearanceWeightData, String appearanceHeightData, String appearanceEyeData, List<String> selectedDatePurpose, List<String> interest) {
        String apreanceData = appearanceHairData + "," + appearanceWeightData + "," + appearanceHeightData + "," + appearanceEyeData;
        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<UpdateAboutModel> call = service.updateAboutProfile(Constants.getSharedPreferenceInt(this, "userId", 0),
                Constants.getSharedPreferenceString(this, "securityToken", ""),
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE,
                educationData, professionData, apreanceData, selectedDatePurpose, interest, "Post");

        loadBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<UpdateAboutModel>() {
            @Override
            public void onResponse(Call<UpdateAboutModel> call, final Response<UpdateAboutModel> response) {

                loadBar.setVisibility(View.GONE);
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            //Constants.setSharedPreferenceBoolean(UserProfileUpdateSecondActivity.this, "allowPermission", true);
                            Intent intent = new Intent(UserProfileUpdateSecondActivity.this, UploadProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    Toast.makeText(UserProfileUpdateSecondActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UpdateAboutModel> call, Throwable t) {
                // Log error here since request failed
                loadBar.setVisibility(View.GONE);
                Log.e("response", t.toString());
            }
        });
    }

    public class FlexboxInterestAdapter extends RecyclerView.Adapter<FlexboxInterestAdapter.ViewHolder> {

        Context context;
        List<MultiPLeData> arrayListInterest = new ArrayList<>();

        public FlexboxInterestAdapter(Context context, List<MultiPLeData> arrayList) {
            this.context = context;
            this.arrayListInterest = arrayList;
        }

        @Override
        public FlexboxInterestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_bg, parent, false);
            return new FlexboxInterestAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FlexboxInterestAdapter.ViewHolder holder, int position) {
            holder.title.setText(arrayListInterest.get(position).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    arrayListInterest.get(pos).setSelected(!arrayListInterest.get(pos).isSelected());
                    if (arrayListInterest.get(pos).isSelected()) {
                        holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_square_bg_without_border));
                        holder.title.setTextColor(getResources().getColor(R.color.white));
                        getInterestList();
                    } else {
                        holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_square_bg_with_border));
                        holder.title.setTextColor(getResources().getColor(R.color.black));
                        getInterestList();
                    }


                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayListInterest.size();
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

        public List<String> getInterestList() {
            selectedInterest = new ArrayList<>();
            for (MultiPLeData model : arrayListInterest) {
                if (model.isSelected()) {
                    selectedInterest.add(model.getName());
                }
            }

            return selectedInterest;
        }
    }

    public class FlexboxDatePurposeAdapter extends RecyclerView.Adapter<FlexboxDatePurposeAdapter.ViewHolder> {

        Context context;
        List<MultiPLeData> arrayListDatePuorpse = new ArrayList<>();

        public FlexboxDatePurposeAdapter(Context context, List<MultiPLeData> arrayList) {
            this.context = context;
            this.arrayListDatePuorpse = arrayList;
        }

        @Override
        public FlexboxDatePurposeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_bg, parent, false);
            return new FlexboxDatePurposeAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FlexboxDatePurposeAdapter.ViewHolder holder, int position) {
            holder.title.setText(arrayListDatePuorpse.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    arrayListDatePuorpse.get(pos).setSelected(!arrayListDatePuorpse.get(pos).isSelected());
                    if (arrayListDatePuorpse.get(pos).isSelected()) {
                        holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_square_bg_without_border));
                        holder.title.setTextColor(getResources().getColor(R.color.white));
                        getPurposeList();
                    } else {
                        holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_square_bg_with_border));
                        holder.title.setTextColor(getResources().getColor(R.color.black));
                        getPurposeList();
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return arrayListDatePuorpse.size();
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

        public List<String> getPurposeList() {
            selectedDatePurpose = new ArrayList<>();
            for (MultiPLeData model : arrayListDatePuorpse) {
                if (model.isSelected()) {
                    selectedDatePurpose.add(model.getName());
                }
            }
            return selectedDatePurpose;
        }
    }
}